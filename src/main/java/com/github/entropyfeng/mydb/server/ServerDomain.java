package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.config.ServerStatus;
import com.github.entropyfeng.mydb.core.domain.*;
import com.github.entropyfeng.mydb.core.helper.Pair;
import com.github.entropyfeng.mydb.core.zset.OrderSet;
import com.github.entropyfeng.mydb.server.command.ClientCommand;
import com.github.entropyfeng.mydb.server.command.ClientRequest;
import com.github.entropyfeng.mydb.server.command.ICommand;
import com.github.entropyfeng.mydb.server.factory.*;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.*;


/**
 * @author entropyfeng
 */
public class ServerDomain {

    private static final Logger logger = LoggerFactory.getLogger(ServerDomain.class);

    /**
     * 服务器状态
     */
    private volatile ServerStatus serverStatus;

    public static AtomicBoolean interrupted = new AtomicBoolean(false);

    public ServerDomain() {

        this.adminObject = new AdminObject(this);

        this.valuesDomain = new ValuesDomain();
        this.listDomain = new ListDomain();
        this.setDomain = new SetDomain();
        this.hashDomain = new HashDomain();
        this.orderSetDomain = new OrderSetDomain();

        constructQueue();

        start();
    }


    public ServerDomain(ValuesDomain valuesDomain, ListDomain listDomain, SetDomain setDomain, HashDomain hashDomain, OrderSetDomain orderSetDomain) {

        this.adminObject = new AdminObject(this);

        this.valuesDomain = valuesDomain;
        this.listDomain = listDomain;
        this.setDomain = setDomain;
        this.hashDomain = hashDomain;
        this.orderSetDomain = orderSetDomain;

        constructQueue();
        start();

    }

    protected AdminObject adminObject;

    protected ValuesDomain valuesDomain;

    protected ListDomain listDomain;

    protected SetDomain setDomain;

    protected HashDomain hashDomain;

    protected OrderSetDomain orderSetDomain;

    protected ConcurrentLinkedDeque<ClientCommand> valuesQueue;

    protected ConcurrentLinkedDeque<ClientCommand> listQueue;

    protected ConcurrentLinkedDeque<ClientCommand> setQueue;

    protected ConcurrentLinkedDeque<ClientCommand> hashQueue;

    protected ConcurrentLinkedDeque<ClientCommand> orderSetQueue;

    protected ConcurrentLinkedDeque<ClientCommand> adminQueue;
    protected Thread valueThread;
    protected Thread listThread;
    protected Thread setThread;
    protected Thread hashThread;
    protected Thread orderSetThread;

    public void start() {

        valueThread = new ValuesThreadFactory().newThread(this::runValues);
        listThread = new ListThreadFactory().newThread(this::runList);
        setThread = new SetThreadFactory().newThread(this::runSet);
        hashThread = new HashThreadFactory().newThread(this::runHash);
        orderSetThread = new OrderSetThreadFactory().newThread(this::runOrderSet);

        valueThread.start();
        listThread.start();
        setThread.start();
        hashThread.start();
        orderSetThread.start();


    }

    private void runOrderSet() {
        logger.info("runOrderSet");
        while (true) {
            ClientCommand orderCommand = orderSetQueue.pollFirst();
            if (orderCommand != null) {
                execute(orderCommand, orderSetDomain);
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    private void runHash() {

        logger.info("runHash");
        while (true) {
            ClientCommand hashCommand = hashQueue.pollFirst();
            if (hashCommand != null) {
                execute(hashCommand, hashDomain);
            } else {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void runList() {
        logger.info("runList");
        while (true) {
            ClientCommand listCommand = listQueue.pollFirst();
            if (listCommand != null) {
                execute(listCommand, listDomain);
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void runValues() {
        logger.info("runValues");
        while (true) {
            ClientCommand valuesCommand = valuesQueue.pollFirst();
            if (valuesCommand != null) {

                execute(valuesCommand, valuesDomain);
            } else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void runSet() {
        logger.info("runSet");
        while (true) {
            ClientCommand setCommand = setQueue.pollFirst();
            if (setCommand != null) {
                execute(setCommand, setDomain);
            } else {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void execute(ICommand command, Object target) {
        Object res;

        try {
            if (command.getValues().size() == 0) {
                res = command.getMethod().invoke(target);
            } else {

                res = command.getMethod().invoke(target, command.getValues().toArray(new Object[0]));
            }
        } catch (IllegalAccessException e) {
            exceptionWrite(command.getChannel(), command.getRequestId(), ExceptionType.IllegalAccessException, "禁止访问" + e.getMessage());
            return;
        } catch (InvocationTargetException e) {
            //调用函数的内部有未捕获的异常
            exceptionWrite(command.getChannel(), command.getRequestId(), ExceptionType.InvocationTargetException, e.getMessage());
            return;
        }

        Pair<ResHead, Collection<ResBody>> pair = ((Pair<ResHead, Collection<ResBody>>) res);

        writeChannel(pair, command.getChannel(), command.getRequestId());
    }

    private void exceptionWrite(Channel channel, Long requestId, ExceptionType exceptionType, String msg) {
        ResHead.Builder headBuilder = ResHead.newBuilder();
        headBuilder.setSuccess(false);
        headBuilder.setResSize(0);
        headBuilder.setInnerException(msg);
        headBuilder.setInnerExceptionType(exceptionType);

        ResponseData.Builder resDataBuilder = ResponseData.newBuilder().setRequestId(requestId).setHeader(headBuilder.build());

        channel.write(resDataBuilder.build());
        resDataBuilder.clear().setEndAble(true).setRequestId(requestId);
        channel.write(resDataBuilder.build());
    }

    private void writeChannel(Pair<ResHead, Collection<ResBody>> pair, Channel channel, Long requestId) {

        //-----------header-------------------------
        ResponseData.Builder responseBuilder = ResponseData.newBuilder();
        responseBuilder.setHeader(pair.getKey());
        responseBuilder.setBeginAble(true);
        responseBuilder.setEndAble(false);
        responseBuilder.setRequestId(requestId);
        channel.write(responseBuilder.build());


        //-----------body---------------------------
        responseBuilder.clear();
        responseBuilder.setRequestId(requestId);
        responseBuilder.setEndAble(false);
        pair.getValue().forEach(resBody -> channel.write(responseBuilder.setBody(resBody).build()));


        //---------------end------------------
        responseBuilder.clear();
        responseBuilder.setRequestId(requestId);
        responseBuilder.setEndAble(true);
        channel.write(responseBuilder.build());
    }


    public void accept(ClientRequest clientRequest, Channel channel) {

        switch (clientRequest.getModel()) {
            case VALUE:
                constructCommand(clientRequest, channel, ValuesDomain.class, valuesQueue);
                return;
            case ADMIN:
                System.out.println(clientRequest.getOperationName());
                return;
            case LIST:
                constructCommand(clientRequest, channel, ListDomain.class, listQueue);
                return;
            case SET:
                constructCommand(clientRequest, channel, SetDomain.class, setQueue);
                return;
            case HASH:
                constructCommand(clientRequest, channel, HashDomain.class, hashQueue);
                return;
            case ZSET:
                constructCommand(clientRequest, channel, OrderSet.class, orderSetQueue);
                return;
            default:
                throw new UnsupportedOperationException();
        }
    }

    private void constructCommand(ClientRequest clientRequest, Channel channel, Class<?> target, ConcurrentLinkedDeque<ClientCommand> queue) {
        Method method = null;
        final String operationName = clientRequest.getOperationName();
        final Long requestId = clientRequest.getRequestId();
        final Class<?>[] types = clientRequest.getTypes();

        try {
            if (types.length == 0) {
                method = target.getDeclaredMethod(operationName);
            } else {
                method = target.getDeclaredMethod(operationName, types);
            }
        } catch (NoSuchMethodException e) {
            logger.info(e.getMessage());
            exceptionWrite(channel, requestId, ExceptionType.NoSuchMethodException, e.getMessage());
            return;
        }

        queue.offer(new ClientCommand(method, clientRequest.getObjects(), channel, requestId));

    }

    private void constructQueue() {
        this.valuesQueue = new ConcurrentLinkedDeque<>();
        this.listQueue = new ConcurrentLinkedDeque<>();
        this.setQueue = new ConcurrentLinkedDeque<>();
        this.hashQueue = new ConcurrentLinkedDeque<>();
        this.orderSetQueue = new ConcurrentLinkedDeque<>();

        this.adminQueue = new ConcurrentLinkedDeque<>();
    }
}

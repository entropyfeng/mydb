package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.domain.*;
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


/**
 * @author entropyfeng
 */
public class ServerDomain {

    private static final Logger logger = LoggerFactory.getLogger(ServerDomain.class);

    public ServerDomain(TurtleServer turtleServer) {
        this.adminObject = new AdminObject(this);
        this.turtleServer = turtleServer;


        valuesDomain = new ValuesDomain();
        valuesQueue = new ConcurrentLinkedDeque<>();

        listDomain = new ListDomain();
        listQueue = new ConcurrentLinkedDeque<>();

        setDomain = new SetDomain();
        setQueue = new ConcurrentLinkedDeque<>();

        hashDomain = new HashDomain();
        hashQueue = new ConcurrentLinkedDeque<>();

        orderSetDomain = new OrderSetDomain();
        orderSetQueue = new ConcurrentLinkedDeque<>();

        start();
    }

    private final TurtleServer turtleServer;

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
            TurtleProtoBuf.ResponseData.Builder builder = TurtleProtoBuf.ResponseData.newBuilder();
            handlerException(command, builder, e.toString());
            builder.setExceptionType(TurtleProtoBuf.ExceptionType.IllegalAccessException);
            builder.setException("禁止访问！");
            command.getChannel().writeAndFlush(builder.build());
            return;
        } catch (InvocationTargetException e) {
            //调用函数的内部有未捕获的异常
            TurtleProtoBuf.ResponseData.Builder builder = TurtleProtoBuf.ResponseData.newBuilder();
            handlerException(command, builder, e.toString());
            builder.setExceptionType(TurtleProtoBuf.ExceptionType.InvocationTargetException);
            builder.setException("调用函数内部错误!");
            command.getChannel().writeAndFlush(builder.build());
            return;
        }

        if (res instanceof Collection) {
            ((Collection) res).forEach(object -> command.getChannel().write(addResponseId((TurtleProtoBuf.ResponseData) object, command.getRequestId())));
            command.getChannel().flush();
        } else {
            command.getChannel().writeAndFlush(addResponseId((TurtleProtoBuf.ResponseData) res, command.getRequestId()));
        }
    }


    /**
     * 为返回值套上ResponseId
     *
     * @param responseData {@link com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf.ResponseData}
     * @param requestId    long
     * @return 加上 requestId 后的返回值
     */
    private TurtleProtoBuf.ResponseData addResponseId(TurtleProtoBuf.ResponseData responseData, Long requestId) {

        //再次创建了对象，会影响性能
        return responseData.toBuilder().setResponseId(requestId).build();
    }

    private void handlerException(ICommand command, TurtleProtoBuf.ResponseData.Builder builder, String excMsg) {

        builder.setSuccess(false);
        //设置是否为集合
        if (command.getMethod().getReturnType().equals(Collection.class)) {
            builder.setCollectionAble(true);
        }
        builder.setResponseId(command.getRequestId());
        builder.setException(excMsg);
        builder.setExceptionType(TurtleProtoBuf.ExceptionType.InvocationTargetException);
    }


    public void accept(ClientRequest clientRequest,Channel channel){

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
                constructCommand(clientRequest, channel,  SetDomain.class, setQueue);
                return;
            case HASH:
                constructCommand(clientRequest, channel, HashDomain.class, hashQueue);
                return;
            case ZSET:
                constructCommand(clientRequest, channel,  OrderSet.class, orderSetQueue);
                return;
            default:
                throw new UnsupportedOperationException();
        }
    }

    private void constructCommand(ClientRequest clientRequest,Channel channel,Class<?> target,ConcurrentLinkedDeque<ClientCommand> queue){
        Method method=null;
        final String operationName = clientRequest.getOperationName();
        final Long requestId = clientRequest.getRequestId();
        final Class<?>[]types=clientRequest.getTypes();
        TurtleProtoBuf.ResponseData responseData = null;

        try {
            if (types.length == 0) {
                method = target.getDeclaredMethod(operationName);
            } else {
                method = target.getDeclaredMethod(operationName, types);
            }
        } catch (NoSuchMethodException e) {
            logger.info(e.getMessage());
            responseData = TurtleProtoBuf.ResponseData.newBuilder().setSuccess(false).setExceptionType(TurtleProtoBuf.ExceptionType.NoSuchMethodException).build();
        }
        if (method == null) {
            channel.writeAndFlush(responseData);
        } else {
            queue.offer(new ClientCommand(method, clientRequest.getObjects(), channel, requestId));
        }
    }
}

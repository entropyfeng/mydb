package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.common.protobuf.ProtoParaHelper;
import com.github.entropyfeng.mydb.common.protobuf.ProtoTurtleHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;
import com.github.entropyfeng.mydb.core.domain.*;
import com.github.entropyfeng.mydb.core.zset.OrderSet;
import com.github.entropyfeng.mydb.server.command.ClientCommand;
import com.github.entropyfeng.mydb.server.command.ICommand;
import com.github.entropyfeng.mydb.server.factory.*;
import io.netty.channel.Channel;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;


/**
 * @author entropyfeng
 */
public class ServerDomain {

    private static final Logger logger = LoggerFactory.getLogger(ServerDomain.class);

    public void xxx() {
        ScheduledExecutorService scheduled = new ScheduledThreadPoolExecutor(1, r -> {
            Thread thread = new Thread(r, "producer and customer thread");
            thread.setDaemon(true);
            return thread;
        });

    }

    public void xx() {
        if (valuesQueue.isEmpty()) {

        }
    }

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

                System.out.println("interrupt");
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
        Object res = null;

        try {
            if (command.getValues().size() == 0) {
                res = command.getMethod().invoke(target);
            } else {
                res = command.getMethod().invoke(target, command.getValues().toArray());
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
        Objects.requireNonNull(res);
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

    public void acceptClientCommand(TurtleProtoBuf.ClientCommand clientCommand, Channel channel) {
        parseCommand(clientCommand, channel);
    }

    public void parseCommand(TurtleProtoBuf.ClientCommand clientCommand, Channel channel) {

        final int paraNumbers = clientCommand.getKeysCount();
        final Class<?>[] types = new Class[paraNumbers];
        final List<Object> values = new ArrayList<>(paraNumbers);
        final List<TurtleProtoBuf.TurtleParaType> typesList = clientCommand.getKeysList();
        final List<TurtleProtoBuf.TurtleCommonValue> valuesList = clientCommand.getValuesList();

        for (int i = 0; i < paraNumbers; i++) {
            final TurtleProtoBuf.TurtleCommonValue value = valuesList.get(i);
            final TurtleProtoBuf.TurtleParaType type = typesList.get(i);
            switch (type) {
                case STRING:
                    types[i] = String.class;
                    values.add(value.getStringValue());
                    break;
                case DOUBLE:
                    types[i] = Double.class;
                    values.add(value.getDoubleValue());
                    break;
                case INTEGER:
                    types[i] = Integer.class;
                    values.add(value.getIntValue());
                    break;
                case LONG:
                    types[i] = Long.class;
                    values.add(value.getLongValue());
                    break;
                case NUMBER_INTEGER:
                    types[i] = BigInteger.class;
                    values.add(new BigInteger(value.getStringValue()));
                    break;
                case NUMBER_DECIMAL:
                    types[i] = BigDecimal.class;
                    values.add(new BigDecimal(value.getStringValue()));
                    break;
                case TURTLE_VALUE:
                    types[i] = TurtleValue.class;
                    values.add(ProtoTurtleHelper.convertToTurtleValue(value.getTurtleValue()));
                    break;
                case BOOL:
                    types[i] = Boolean.class;
                    values.add(value.getBoolValue());
                    break;
                case COLLECTION:
                    types[i] = Collection.class;
                    values.add(ProtoParaHelper.handlerCollection(type, value.getCollectionValue().getCollectionParasList()));
                    break;
                default:
                    throw new UnsupportedOperationException(type.name());
            }
        }


        switch (clientCommand.getModel()) {
            case VALUE:
                constructCommand(types, values, channel, clientCommand, ValuesDomain.class, valuesQueue);
                return;
            case ADMIN:
                System.out.println(clientCommand.getOperationName());
                return;
            case LIST:
                constructCommand(types, values, channel, clientCommand, ListDomain.class, listQueue);
                return;
            case SET:
                constructCommand(types, values, channel, clientCommand, SetDomain.class, setQueue);
                return;
            case HASH:
                constructCommand(types, values, channel, clientCommand, HashDomain.class, hashQueue);
                return;
            case ZSET:
                constructCommand(types, values, channel, clientCommand, OrderSet.class, orderSetQueue);
                return;
            default:
                throw new UnsupportedOperationException();
        }
    }


    private void constructCommand(@NotNull Class<?>[] types, @NotNull List<Object> values, @NotNull Channel channel, @NotNull TurtleProtoBuf.ClientCommand command, @NotNull Class<?> target, @NotNull ConcurrentLinkedDeque<ClientCommand> queue) {

        Method method = null;
        final String operationName = command.getOperationName();
        final Long requestId = command.getRequestId();
        TurtleProtoBuf.ResponseData responseData = null;
        //找到合适的方法
        try {
            if (types.length == 0) {
                method = target.getDeclaredMethod(operationName);
            } else {
                method = target.getDeclaredMethod(operationName, types);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            responseData = TurtleProtoBuf.ResponseData.newBuilder().setSuccess(false).setExceptionType(TurtleProtoBuf.ExceptionType.NoSuchMethodException).build();
        }
        if (method == null) {
            channel.writeAndFlush(responseData);
        } else {
            queue.offer(new ClientCommand(method, values, channel, requestId));
        }
    }

}

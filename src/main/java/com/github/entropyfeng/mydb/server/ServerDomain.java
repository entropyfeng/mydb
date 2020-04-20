package com.github.entropyfeng.mydb.server;


import com.github.entropyfeng.mydb.common.protobuf.ProtoParaHelper;
import com.github.entropyfeng.mydb.common.protobuf.ProtoTurtleHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.domain.ListDomain;
import com.github.entropyfeng.mydb.core.domain.SetDomain;
import com.github.entropyfeng.mydb.core.domain.ValuesDomain;
import com.github.entropyfeng.mydb.core.TurtleValue;
import com.github.entropyfeng.mydb.server.command.*;
import com.github.entropyfeng.mydb.server.factory.ListThreadFactory;
import com.github.entropyfeng.mydb.server.factory.SetThreadFactory;
import com.github.entropyfeng.mydb.server.factory.ValuesThreadFactory;
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

        setDomain=new SetDomain();
        setQueue=new ConcurrentLinkedDeque<>();

        start();
    }

    private final TurtleServer turtleServer;

    protected AdminObject adminObject;

    protected ValuesDomain valuesDomain;

    protected ListDomain listDomain;

    protected SetDomain setDomain;


    protected ConcurrentLinkedDeque<ValuesCommand> valuesQueue;

    protected ConcurrentLinkedDeque<ListCommand> listQueue;

    protected ConcurrentLinkedDeque<SetCommand> setQueue;

    public void start() {
        new ValuesThreadFactory().newThread(this::runValues).start();
        new ListThreadFactory().newThread(this::runList).start();
        new SetThreadFactory().newThread(this::runSet).start();
    }

    private void runList() {
        logger.info("runList");
        while (true) {
            ListCommand listCommand = listQueue.pollFirst();
            if (listCommand != null) {
                execute(listCommand, listDomain);
            }else {
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
            ValuesCommand valuesCommand = valuesQueue.pollFirst();
            if (valuesCommand != null) {

                execute(valuesCommand, valuesDomain);
            }else {
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
            SetCommand setCommand = setQueue.pollFirst();
            if (setCommand != null) {
                execute(setCommand, setDomain);
            }else {
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
            TurtleProtoBuf.ResponseData.Builder builder=TurtleProtoBuf.ResponseData.newBuilder();
            handlerException(command,builder,e.toString());
            builder.setExceptionType(TurtleProtoBuf.ExceptionType.IllegalAccessException);
            builder.setException("禁止访问！");
            command.getChannel().writeAndFlush(builder.build());
            return;
        } catch (InvocationTargetException e) {
            //调用函数的内部有未捕获的异常
            TurtleProtoBuf.ResponseData.Builder builder=TurtleProtoBuf.ResponseData.newBuilder();
            handlerException(command,builder,e.toString());
            builder.setExceptionType(TurtleProtoBuf.ExceptionType.InvocationTargetException);
            builder.setException("调用函数内部错误!");
            command.getChannel().writeAndFlush(builder.build());
            return;
        }
        Objects.requireNonNull(res);
        if (res instanceof Collection){
           ((Collection) res).forEach(object-> command.getChannel().write(addResponseId((TurtleProtoBuf.ResponseData)object,command.getRequestId())));
           command.getChannel().flush();
        }else {
            command.getChannel().writeAndFlush(addResponseId((TurtleProtoBuf.ResponseData)res,command.getRequestId()));
        }
    }


    /**
     * 为返回值套上ResponseId
     * @param responseData {@link com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf.ResponseData}
     * @param requestId long
     * @return 加上 requestId 后的返回值
     */
    private TurtleProtoBuf.ResponseData addResponseId(TurtleProtoBuf.ResponseData responseData, Long requestId){

        //再次创建了对象，会影响性能
        return responseData.toBuilder().setResponseId(requestId).build();
    }

    private void handlerException(ICommand command, TurtleProtoBuf.ResponseData.Builder builder, String excMsg){

        builder.setSuccess(false);
        //设置是否为集合
        if (command.getMethod().getReturnType().equals(Collection.class)){
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
                parseForValue(types, values, channel, clientCommand);
                return;
            case ADMIN:
                System.out.println(clientCommand.getOperationName());
                return;
            default:
                throw new UnsupportedOperationException();
        }
    }


    private void parseForValue(@NotNull Class<?>[] types, List<Object> values, Channel channel, @NotNull TurtleProtoBuf.ClientCommand command) {
        Method method = null;
        final String operationName = command.getOperationName();
        final Long requestId = command.getRequestId();
        TurtleProtoBuf.ResponseData responseData = null;
        //找到合适的方法
        try {
            if (types.length == 0) {
                method = ValuesDomain.class.getDeclaredMethod(operationName);
            } else {
                method = ValuesDomain.class.getDeclaredMethod(operationName, types);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            responseData = TurtleProtoBuf.ResponseData.newBuilder().setSuccess(false).setExceptionType(TurtleProtoBuf.ExceptionType.NoSuchMethodException).build();
        }
        if (method == null) {
            channel.writeAndFlush(responseData);
        } else {
            System.out.println(method.getName());
            valuesQueue.offer(new ValuesCommand(method, values, channel, requestId));
        }

    }


}

package com.github.entropyfeng.mydb.server;


import com.github.entropyfeng.mydb.common.protobuf.ProtoParaHelper;
import com.github.entropyfeng.mydb.common.protobuf.ProtoTurtleHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.obj.ListObject;
import com.github.entropyfeng.mydb.core.obj.SetObject;
import com.github.entropyfeng.mydb.core.obj.TurtleValue;
import com.github.entropyfeng.mydb.core.obj.ValuesObject;
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

        valuesObject = new ValuesObject();
        valuesQueue = new ConcurrentLinkedDeque<>();

        listObject = new ListObject();
        listQueue = new ConcurrentLinkedDeque<>();
    }

    private final TurtleServer turtleServer;

    protected AdminObject adminObject;

    protected ValuesObject valuesObject;

    protected ListObject listObject;

    protected SetObject setObject;


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
                execute(listCommand, listObject);
            }
        }
    }

    private void runValues() {
        logger.info("runValues");
        while (true) {
            ValuesCommand valuesCommand = valuesQueue.pollFirst();
            if (valuesCommand != null) {
                execute(valuesCommand, valuesObject);
            }
        }
    }

    private void runSet() {
        logger.info("runSet");
        while (true) {
            SetCommand setCommand = setQueue.pollFirst();
            if (setCommand != null) {
                execute(setCommand, setObject);
            }
        }
    }


    public void execute(ICommand command, Object target) {
        Object res = null;

        try {
            if (command.getValues().size() == 0) {
                res = command.getMethod().invoke(target);
            } else {
                res = command.getMethod().invoke(target, command.getValues());
            }
        } catch (IllegalAccessException e) {
            TurtleProtoBuf.ResponseData.Builder builder=TurtleProtoBuf.ResponseData.newBuilder();
            handlerException(command,builder,e.toString());
            builder.setExceptionType(TurtleProtoBuf.ExceptionType.IllegalAccessException);
            builder.setException("禁止访问！");
            command.getChannel().writeAndFlush(builder.build());
        } catch (InvocationTargetException e) {
            TurtleProtoBuf.ResponseData.Builder builder=TurtleProtoBuf.ResponseData.newBuilder();
            handlerException(command,builder,e.toString());
            builder.setExceptionType(TurtleProtoBuf.ExceptionType.InvocationTargetException);
            builder.setException("调用函数内部错误!");
            command.getChannel().writeAndFlush(builder.build());
        }
        Objects.requireNonNull(res);
        if (res instanceof Collection){

           ((Collection) res).forEach(object-> command.getChannel().write(dealResponseData((TurtleProtoBuf.ResponseData)object,command.getRequestId())));

           command.getChannel().flush();
        }else {
            command.getChannel().writeAndFlush(dealResponseData((TurtleProtoBuf.ResponseData)res,command.getRequestId()));
        }
    }


    /**
     * 为返回值套上ResponseId
     * @param responseData {@link com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf.ResponseData}
     * @param requestId long
     * @return 加上 requestId 后的返回值
     */
    private TurtleProtoBuf.ResponseData dealResponseData(TurtleProtoBuf.ResponseData responseData, Long requestId){
       return responseData.toBuilder().setResponseId(requestId).build();
    }

    private void handlerException(ICommand command, TurtleProtoBuf.ResponseData.Builder builder, String excMsg){

        builder.setSuccess(false);
        if (command.getMethod().getReturnType().equals(Collection.class)){
            builder.setCollectionAble(true);
        }
        builder.setResponseId(command.getRequestId());
        builder.setException(excMsg);
        builder.setExceptionType(TurtleProtoBuf.ExceptionType.InvocationTargetException);
    }

    private void handlerCollection(Collection<Object> objects, TurtleProtoBuf.ResponseData.Builder builder, Channel channel) {
        builder.setCollectionSize(objects.size());
        builder.setCollectionAble(true);
        builder.setResponseSequence(0L);
        if (objects.isEmpty()) {
            channel.writeAndFlush(builder.build());
        } else {
            TurtleProtoBuf.TurtleParaType type = ProtoParaHelper.checkObjectType(objects.iterator().next());
            TurtleProtoBuf.ResponseData.Builder resBuilder = TurtleProtoBuf.ResponseData.newBuilder();
            builder.setType(type);
            channel.writeAndFlush(builder.build());
            //netty可以保证发送的顺序
            switch (type) {

                case TURTLE_VALUE:
                    objects.forEach(turtle -> {
                        resBuilder.setTurtleValue(ProtoTurtleHelper.convertToProtoTurtleValue((TurtleValue) turtle));
                        channel.write(resBuilder.build());
                    });
                    break;

                case INTEGER:
                    objects.forEach(integer -> {
                        resBuilder.setIntValue((Integer) integer);
                        channel.write(resBuilder.build());
                    });
                    break;

                case LONG:
                    objects.forEach(object -> {
                        resBuilder.setLongValue((Long) object);

                        channel.write(resBuilder.build());
                    });
                    break;
                case BOOL:
                    objects.forEach(object -> {
                        resBuilder.setBoolValue((Boolean) object);
                        channel.write(resBuilder.build());
                    });
                    break;
                case NUMBER_DECIMAL:
                    objects.forEach(object -> {
                        resBuilder.setStringValue(((BigDecimal) object).toPlainString());
                        channel.write(resBuilder.build());
                    });
                    break;
                case DOUBLE:
                    objects.forEach(object -> {
                        resBuilder.setDoubleValue((Double) object);
                        channel.write(resBuilder.build());
                    });
                    break;
                case STRING:
                    objects.forEach(object -> {
                        resBuilder.setStringValue((String) object);
                        channel.write(resBuilder.build());
                    });
                    break;
                case NUMBER_INTEGER:
                    objects.forEach(object -> {
                        resBuilder.setStringValue(((BigInteger) object).toString());
                        channel.write(resBuilder.build());
                    });
                    break;
                default:
                    throw new UnsupportedOperationException(type.name());
            }
        }
        channel.flush();
    }

    private void handlerSingle(Object object, TurtleProtoBuf.ResponseData.Builder builder, Channel channel) {

        if (object instanceof String) {
            builder.setType(TurtleProtoBuf.TurtleParaType.STRING);
            builder.setStringValue((String) object);
        } else if (object instanceof Integer) {
            builder.setType(TurtleProtoBuf.TurtleParaType.INTEGER);
            builder.setIntValue((Integer) object);
        } else if (object instanceof Long) {
            builder.setType(TurtleProtoBuf.TurtleParaType.LONG);
            builder.setLongValue((Long) object);
        } else if (object instanceof Double) {
            builder.setType(TurtleProtoBuf.TurtleParaType.DOUBLE);
            builder.setDoubleValue((Double) object);
        } else if (object instanceof BigInteger) {
            builder.setType(TurtleProtoBuf.TurtleParaType.STRING);
            builder.setStringValue(((BigInteger) object).toString());
        } else if (object instanceof BigDecimal) {
            builder.setType(TurtleProtoBuf.TurtleParaType.STRING);
            builder.setStringValue(((BigDecimal) object).toPlainString());
        } else if (object instanceof TurtleValue) {
            builder.setType(TurtleProtoBuf.TurtleParaType.TURTLE_VALUE);
            TurtleProtoBuf.TurtleValue res = ProtoTurtleHelper.convertToProtoTurtleValue((TurtleValue) object);
            builder.setTurtleValue(res);
        } else if (object instanceof Boolean) {
            builder.setType(TurtleProtoBuf.TurtleParaType.BOOL);
            builder.setBoolValue((Boolean) object);
        } else {
            throw new UnsupportedOperationException(object.getClass().getName());
        }
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
                method = ValuesObject.class.getDeclaredMethod(operationName);
            } else {
                method = ValuesObject.class.getDeclaredMethod(operationName, types);
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            responseData = TurtleProtoBuf.ResponseData.newBuilder().setSuccess(false).setExceptionType(TurtleProtoBuf.ExceptionType.NoSuchMethodException).build();
        }
        if (method == null) {
            channel.writeAndFlush(responseData);
        } else {
            valuesQueue.offer(new ValuesCommand(method, values, channel, requestId));
        }

    }


}

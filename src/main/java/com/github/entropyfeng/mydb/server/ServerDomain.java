package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.common.protobuf.ProtoParaHelper;
import com.github.entropyfeng.mydb.common.protobuf.ProtoTurtleHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.obj.ListObject;
import com.github.entropyfeng.mydb.core.obj.SetObject;
import com.github.entropyfeng.mydb.core.obj.TurtleValue;
import com.github.entropyfeng.mydb.core.obj.ValuesObject;
import com.github.entropyfeng.mydb.server.command.ICommand;
import com.github.entropyfeng.mydb.server.command.ValuesCommand;
import com.github.entropyfeng.mydb.server.factory.ListThreadFactory;
import com.github.entropyfeng.mydb.server.factory.ValuesThreadFactory;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ConcurrentLinkedDeque;


/**
 * @author entropyfeng
 */
public class ServerDomain {

    private static final Logger logger = LoggerFactory.getLogger(ServerDomain.class);

    public ServerDomain(TurtleServer turtleServer) {
        this.turtleServer = turtleServer;

        valuesObject = new ValuesObject();
        valuesQueue = new ConcurrentLinkedDeque<>();

        listObject = new ListObject();
        listQueue = new ConcurrentLinkedDeque<>();
    }

    private final TurtleServer turtleServer;
    private ValuesObject valuesObject;

    private ListObject listObject;

    private SetObject setObject;

    private ConcurrentLinkedDeque<ValuesCommand> valuesQueue;

    private ConcurrentLinkedDeque<Runnable> listQueue;


    public void start() {
        new ValuesThreadFactory().newThread(this::runValues).start();
        new ListThreadFactory().newThread(this::runList).start();

    }

    private void runList() {
        logger.info("runList");
        while (true) {

        }
    }

    private void runValues() {
       while (true){
           ValuesCommand valuesCommand=valuesQueue.pollFirst();
           if (valuesCommand!=null){
               execute(valuesCommand,valuesObject);
           }
       }
    }

    public void execute(ICommand command, Object target) {

        Object res = null;
        TurtleProtoBuf.ResponseData.Builder builder = TurtleProtoBuf.ResponseData.newBuilder();
        builder.setResponseId(command.getRequestId());
        builder.setSuccess(true);
        try {
            if (command.getValues().size() == 0) {
                res = command.getMethod().invoke(target);
            } else {
                res = command.getMethod().invoke(target, command.getValues());
            }
        } catch (IllegalAccessException e) {
            builder.setSuccess(false);
            builder.setExceptionType(TurtleProtoBuf.ExceptionType.IllegalAccessException);
        } catch (NoSuchElementException e) {
            builder.setSuccess(false);
            builder.setExceptionType(TurtleProtoBuf.ExceptionType.NoSuchElementException);
        } catch (UnsupportedOperationException e) {
            builder.setSuccess(false);
            builder.setExceptionType(TurtleProtoBuf.ExceptionType.UnsupportedOperationException);
        } catch (InvocationTargetException e) {
            builder.setSuccess(false);
            builder.setExceptionType(TurtleProtoBuf.ExceptionType.InvocationTargetException);
            e.printStackTrace();
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


    private void parseForValue(Class<?>[] types, List<Object> values, Channel channel, TurtleProtoBuf.ClientCommand command) {
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
            responseData = TurtleProtoBuf.ResponseData.newBuilder().setSuccess(false).setExceptionType(TurtleProtoBuf.ExceptionType.NoSuchMethodException).build();
        }
        if (method == null) {
            channel.writeAndFlush(responseData);
        } else {
            valuesQueue.offer(new ValuesCommand(method, values, channel, requestId));
        }

    }

    public ValuesObject getValuesObject() {
        return valuesObject;
    }
}

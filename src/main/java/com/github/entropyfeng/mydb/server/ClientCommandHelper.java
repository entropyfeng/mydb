package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.obj.TurtleValue;
import com.github.entropyfeng.mydb.core.obj.ValuesObject;
import com.github.entropyfeng.mydb.server.command.ValuesCommand;
import io.netty.channel.Channel;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author enntropyfeng
 */
public class ClientCommandHelper {

    public static void parseCommand(TurtleProtoBuf.ClientCommand clientCommand, Channel channel) {
        final int paraNumbers = clientCommand.getKeysCount();
        assert paraNumbers == clientCommand.getValuesCount();
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
                    values.add(new TurtleValue(value.getTurtleValue()));
                    break;
                case COLLECTION:
                    types[i] = Collection.class;
                    values.add(handlerCollection(type, value.getCollectionValue().getCollectionParasList()));
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }


        switch (clientCommand.getModel()) {
            case VALUE:
                parseForValue(clientCommand.getOperationName(), types, values, channel);
                return;
            case ADMIN:

            case LIST:

            case SET:

            case HASH:

            default:
                throw new UnsupportedOperationException();
        }
    }
    private static void handlerAdmin(String operationName, Class<?>[] types, List<Object> values, Channel channel){


    }

    private static List<?> handlerCollection(TurtleProtoBuf.TurtleParaType type, List<TurtleProtoBuf.TurtleCommonValue> values) {


        final List<Object> res = new ArrayList<>();

        switch (type) {
            case STRING:
                values.forEach(value -> res.add(value.getStringValue()));
                break;

            case NUMBER_DECIMAL:
                values.forEach(value -> res.add(new BigDecimal(value.getStringValue())));
                break;

            case DOUBLE:
                values.forEach(value -> res.add(value.getDoubleValue()));
                break;
            case LONG:
                values.forEach(value -> res.add(value.getLongValue()));
                break;
            case INTEGER:
                values.forEach(value -> res.add(value.getIntValue()));
                break;
            case NUMBER_INTEGER:
                values.forEach(value -> res.add(new BigInteger(value.getStringValue())));
                break;
            case TURTLE_VALUE:
                values.forEach(value -> res.add(new TurtleValue(value.getTurtleValue())));
                break;
            default:
                throw new UnsupportedOperationException("unSupport operation " + type.toString());
        }
        return res;
    }

    private static ValuesCommand parseForValue(String operationName, Class<?>[] types, List<Object> values, Channel channel) {
        Method method = null;
        try {
            method = ValuesObject.class.getDeclaredMethod(operationName, types);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return new ValuesCommand(method, values, channel);

    }

}

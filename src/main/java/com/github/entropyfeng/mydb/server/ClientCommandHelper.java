package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.obj.TurtleValue;
import com.github.entropyfeng.mydb.core.obj.ValuesObject;
import com.github.entropyfeng.mydb.server.command.ValuesCommand;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author enntropyfeng
 */
public class ClientCommandHelper {

    public static void parseCommand(TurtleProtoBuf.ClientCommand clientCommand) {
        final int paraNumbers = clientCommand.getKeysCount();
        assert paraNumbers == clientCommand.getValuesCount();
        final Class<?>[] types = new Class[paraNumbers];
        final List<Object> values = new ArrayList<>(paraNumbers);
        final List<TurtleProtoBuf.TurtleParaType> typesList = clientCommand.getKeysList();
        final List<TurtleProtoBuf.TurtleCommonValue> valuesList = clientCommand.getValuesList();

        for (int i = 0; i < paraNumbers; i++) {
            final TurtleProtoBuf.TurtleCommonValue value = valuesList.get(i);
            switch (typesList.get(i)) {
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
                default:
                    throw new UnsupportedOperationException();
            }
        }

        switch (clientCommand.getModel()) {
            case ADMIN:

            case SET:

            case VALUE: parseForValue(clientCommand.getOperationName(),types,values);return;

            case HASH:

            default:
                throw new UnsupportedOperationException();
        }
    }


    private static ValuesCommand parseForValue(String operationName, Class<?>[] types, List<Object> values) {
        Method method = null;
        try {
            method = ValuesObject.class.getDeclaredMethod(operationName, types);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return new ValuesCommand(method, values);

    }

}

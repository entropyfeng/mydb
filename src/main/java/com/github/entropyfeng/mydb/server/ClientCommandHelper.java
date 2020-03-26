package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.obj.TurtleValue;
import org.checkerframework.checker.units.qual.C;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author enntropyfeng
 */
public class ClientCommandHelper {

    public static IClientCommand parseCommand(TurtleProtoBuf.ClientCommand clientCommand) {

        final int paraNumbers = clientCommand.getParasList().size();
        final Class<?>[] paraTypes = new Class[paraNumbers];
        final List<Object> paras = new ArrayList<>(paraNumbers);
        final List<TurtleProtoBuf.TurtlePara> parasList = clientCommand.getParasList();
        for (int i = 0; i < paraNumbers; i++) {

            final TurtleProtoBuf.TurtleCommonValue value = parasList.get(i).getValue();
            switch (parasList.get(i).getKey()) {
                case STRING:
                    paraTypes[i] = String.class;
                    paras.add(value.getStringValue());
                    break;
                case DOUBLE:
                    paraTypes[i] = Double.class;
                    paras.add(value.getDoubleValue());
                    break;
                case INTEGER:
                    paraTypes[i] = Integer.class;
                    paras.add(value.getIntValue());
                    break;
                case LONG:
                    paraTypes[i] = Long.class;
                    paras.add(value.getLongValue());
                    break;
                case NUMBER_INTEGER:
                    paraTypes[i] = BigInteger.class;
                    paras.add(new BigInteger(value.getStringValue()));
                    break;
                case NUMBER_DECIMAL:
                    paraTypes[i] = BigDecimal.class;
                    paras.add(new BigDecimal(value.getStringValue()));
                    break;
                case TURTLE_VALUE:
                    paraTypes[i] = TurtleValue.class;
                    paras.add(new TurtleValue(value.getTurtleValue()));
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        switch (clientCommand.getModel()) {
            case ADMIN:
              return  parseForAdmin(clientCommand);

            case CONCRETE:
                return parseForConcrete(clientCommand,paraTypes,paras);
            default:
                throw new UnsupportedOperationException();
        }
    }

    private static IClientCommand parseForConcrete(TurtleProtoBuf.ClientCommand clientCommand,Class<?>[] paraTypes,List<Object> paras) {

           switch (clientCommand.getObj()){
               case VALUE:return new ValuesCommand(clientCommand.getOperationName(),paraTypes,paras);
               default:throw new UnsupportedOperationException();
           }

    }



    private  static IClientCommand parseForAdmin(TurtleProtoBuf.ClientCommand clientCommand) {

        return new AdminCommand(clientCommand.getOperationName());
    }

}

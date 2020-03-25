package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.obj.TurtleValue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author enntropyfeng
 */
public class ClientCommandHelper {

    public void xx(TurtleProtoBuf.ClientCommand clientCommand) {

        switch (clientCommand.getModel()) {
            case COMMON:
                parseForCommon(clientCommand);
                break;
            case ADMIN:
                parseForAdmin();
                break;
            case CONCRETE:
                parseForConcrete();
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    private void parseForCommon(TurtleProtoBuf.ClientCommand clientCommand) {
        final String operationName = clientCommand.getOperationName();
        final int paraNumbers = clientCommand.getParasList().size();
        List<Object> paraTypes = new ArrayList<>(paraNumbers);
        clientCommand.getParasList().forEach(para -> {
            TurtleProtoBuf.TurtleParaType paraType = para.getKey();
            TurtleProtoBuf.TurtleCommonValue value = para.getValue();
            switch (paraType) {
                case STRING:
                    paraTypes.add(String.class);
                    break;
                case DOUBLE:
                    paraTypes.add(Double.class);
                    break;
                case INTEGER:
                    paraTypes.add(Integer.class);
                    break;
                case LONG:
                    paraTypes.add(Long.class);
                    break;
                case NUMBER_INTEGER:
                    paraTypes.add(BigInteger.class);
                    break;
                case NUMBER_DECIMAL:
                    paraTypes.add(BigDecimal.class);
                    break;
                case TURTLE_VALUE:
                    paraTypes.add(TurtleValue.class);
                    break;
                default:
                    throw new UnsupportedOperationException();
            }


        });
    }

    private String handleString(TurtleProtoBuf.TurtleCommonValue value) {
        return value.getStringValue();
    }

    private Double handleDouble(TurtleProtoBuf.TurtleCommonValue value) {
        return value.getDoubleValue();
    }

    private Integer handleInteger(TurtleProtoBuf.TurtleCommonValue value) {
        return value.getIntValue();
    }

    private Long handleLong(TurtleProtoBuf.TurtleCommonValue value) {
        return value.getLongValue();
    }

    private BigInteger handleBigInteger(TurtleProtoBuf.TurtleCommonValue value) {
        return new BigInteger(value.getStringValue());
    }

    private BigDecimal handleBigDecimal(TurtleProtoBuf.TurtleCommonValue value) {
        return new BigDecimal(value.getStringValue());
    }

    private TurtleValue handleTurtleValue(TurtleProtoBuf.TurtleCommonValue value) {
        final TurtleProtoBuf.TurtleParaType type = value.getTurtleValue().getTurtleParaType();
        value.getTurtleValue().getValues().toByteArray();
        TurtleValue res = null;
        switch (type) {
            case STRING:
                res = new TurtleValue()
        }
        return res;
    }

    private void parseForAdmin() {

    }

    private void parseForConcrete() {

    }
}

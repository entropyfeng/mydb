package com.github.entropyfeng.mydb.common.protobuf;

import com.github.entropyfeng.mydb.common.TurtleValueType;
import com.github.entropyfeng.mydb.core.obj.TurtleValue;
import com.google.protobuf.ByteString;

/**
 * @author entropyfeng
 */
public class ProtoTurtleHelper {

    public static TurtleValue convertToTurtleValue(TurtleProtoBuf.TurtleValue protoTurtleValue) {
        TurtleValueType type;
        switch (protoTurtleValue.getTurtleParaType()) {
            case LONG:
                type = TurtleValueType.LONG;
                break;
            case STRING:
                type = TurtleValueType.STRING;
                break;
            case DOUBLE:
                type = TurtleValueType.DOUBLE;
                break;
            case INTEGER:
                type = TurtleValueType.INTEGER;
                break;
            case NUMBER_DECIMAL:
                type = TurtleValueType.NUMBER_DECIMAL;
                break;
            case NUMBER_INTEGER:
                type = TurtleValueType.NUMBER_INTEGER;
                break;
            default:
                throw new UnsupportedOperationException("unSupportCast" + protoTurtleValue.getTurtleParaType());
        }
        byte[] values = protoTurtleValue.toByteArray();
        return new TurtleValue(values, type);
    }

    public static TurtleProtoBuf.TurtleValue convertToProtoTurtleValue(TurtleValue turtleValue) {
        TurtleProtoBuf.TurtleParaType type;
        switch (turtleValue.getType()) {
            case INTEGER:
                type = TurtleProtoBuf.TurtleParaType.INTEGER;
                break;
            case DOUBLE:
                type = TurtleProtoBuf.TurtleParaType.DOUBLE;
                break;
            case STRING:
                type = TurtleProtoBuf.TurtleParaType.STRING;
                break;
            case LONG:
                type = TurtleProtoBuf.TurtleParaType.LONG;
                break;
            case NUMBER_INTEGER:
                type = TurtleProtoBuf.TurtleParaType.NUMBER_INTEGER;
                break;
            case NUMBER_DECIMAL:
                type= TurtleProtoBuf.TurtleParaType.NUMBER_DECIMAL;
                break;
            default:throw new UnsupportedOperationException("unSupport"+turtleValue.getType());

        }

      return   TurtleProtoBuf.TurtleValue.newBuilder().setTurtleParaType(type).setValues(ByteString.copyFrom(turtleValue.getValues())).build();
    }
}

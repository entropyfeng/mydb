package com.github.entropyfeng.mydb.common.protobuf;

import com.github.entropyfeng.mydb.common.TurtleValueType;
import com.github.entropyfeng.mydb.core.TurtleValue;
import com.google.protobuf.ByteString;

/**
 * @author entropyfeng
 */
public class ProtoTurtleHelper {

    public static ProtoBuf.TurtleValueType convert(TurtleValueType turtleValueType) {
        ProtoBuf.TurtleValueType type;
        switch (turtleValueType) {
            case INTEGER:
                type = ProtoBuf.TurtleValueType.Integer;
                break;
            case DOUBLE:
                type = ProtoBuf.TurtleValueType.Double;
                break;
            case NUMBER_DECIMAL:
                type = ProtoBuf.TurtleValueType.NumberDecimal;
                break;
            case LONG:
                type = ProtoBuf.TurtleValueType.Long;
                break;
            case NUMBER_INTEGER:
                type = ProtoBuf.TurtleValueType.NumberInteger;
                break;
            default:
                type = ProtoBuf.TurtleValueType.Bytes;
                break;

        }
        return type;
    }

    public static TurtleValue convertToDbTurtle(ProtoBuf.TurtleValue protoTurtleValue) {
        TurtleValueType type;
        byte[] values = protoTurtleValue.getValues().toByteArray();
        switch (protoTurtleValue.getTurtleValueType()) {
            case Long:
                type = TurtleValueType.LONG;
                break;
            case Double:
                type = TurtleValueType.DOUBLE;
                break;
            case Integer:
                type = TurtleValueType.INTEGER;
                break;
            case NumberInteger:
                type = TurtleValueType.NUMBER_INTEGER;
                break;
            case NumberDecimal:
                type = TurtleValueType.NUMBER_DECIMAL;
                break;
            default:
                type = TurtleValueType.BYTES;
                break;
        }

        return new TurtleValue(values, type);
    }

    public static ProtoBuf.TurtleValue convertToProto(TurtleValue turtleValue) {
        ProtoBuf.TurtleValueType type;
        switch (turtleValue.getType()) {
            case INTEGER:
                type = ProtoBuf.TurtleValueType.Integer;
                break;
            case DOUBLE:
                type = ProtoBuf.TurtleValueType.Double;
                break;
            case LONG:
                type = ProtoBuf.TurtleValueType.Long;
                break;
            case NUMBER_INTEGER:
                type = ProtoBuf.TurtleValueType.NumberInteger;
                break;
            case NUMBER_DECIMAL:
                type = ProtoBuf.TurtleValueType.NumberDecimal;
                break;
            default:
                type= ProtoBuf.TurtleValueType.Bytes;
                break;

        }
        return ProtoBuf.TurtleValue.newBuilder().setTurtleValueType(type).setValues(ByteString.copyFrom(turtleValue.getValues())).build();
    }
}

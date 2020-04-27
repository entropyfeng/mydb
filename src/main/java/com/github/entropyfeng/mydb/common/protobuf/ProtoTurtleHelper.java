package com.github.entropyfeng.mydb.common.protobuf;

import com.github.entropyfeng.mydb.common.TurtleValueType;
import com.github.entropyfeng.mydb.core.TurtleValue;
import com.google.protobuf.ByteString;

/**
 * @author entropyfeng
 */
public class ProtoTurtleHelper {

    public static TurtleProtoBuf.TurtleValueType convert(TurtleValueType turtleValueType) {
        TurtleProtoBuf.TurtleValueType type;
        switch (turtleValueType) {
            case INTEGER:
                type = TurtleProtoBuf.TurtleValueType.Integer;
                break;
            case DOUBLE:
                type = TurtleProtoBuf.TurtleValueType.Double;
                break;
            case NUMBER_DECIMAL:
                type = TurtleProtoBuf.TurtleValueType.NumberDecimal;
                break;
            case LONG:
                type = TurtleProtoBuf.TurtleValueType.Long;
                break;
            case NUMBER_INTEGER:
                type = TurtleProtoBuf.TurtleValueType.NumberInteger;
                break;
            default:
                type = TurtleProtoBuf.TurtleValueType.Bytes;
                break;

        }
        return type;
    }

    public static TurtleValue convertToTurtleValue(TurtleProtoBuf.TurtleValue protoTurtleValue) {
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

    public static TurtleProtoBuf.TurtleValue convertToProto(TurtleValue turtleValue) {
        TurtleProtoBuf.TurtleValueType type;
        switch (turtleValue.getType()) {
            case INTEGER:
                type = TurtleProtoBuf.TurtleValueType.Integer;
                break;
            case DOUBLE:
                type = TurtleProtoBuf.TurtleValueType.Double;
                break;
            case LONG:
                type = TurtleProtoBuf.TurtleValueType.Long;
                break;
            case NUMBER_INTEGER:
                type = TurtleProtoBuf.TurtleValueType.NumberInteger;
                break;
            case NUMBER_DECIMAL:
                type = TurtleProtoBuf.TurtleValueType.NumberDecimal;
                break;
            default:
                type= TurtleProtoBuf.TurtleValueType.Bytes;
                break;

        }
        return TurtleProtoBuf.TurtleValue.newBuilder().setTurtleValueType(type).setValues(ByteString.copyFrom(turtleValue.getValues())).build();
    }
}

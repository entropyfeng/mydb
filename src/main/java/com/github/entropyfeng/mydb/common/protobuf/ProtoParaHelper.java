package com.github.entropyfeng.mydb.common.protobuf;

import com.github.entropyfeng.mydb.common.TurtleParaType;
import com.github.entropyfeng.mydb.core.TurtleValue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

/**
 * @author entropyfeng
 * @date 2020/04/04
 */
public class ProtoParaHelper {

    /**
     * 参数类型枚举，相互转化
     * @param turtleParaType {@link com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf.TurtleParaType}
     * @return {@link TurtleParaType}
     */
    public static TurtleParaType convertToTurtleParaType(TurtleProtoBuf.TurtleParaType turtleParaType) {
        Objects.requireNonNull(turtleParaType);
        switch (turtleParaType) {
            case NUMBER_DECIMAL:
                return TurtleParaType.NUMBER_DECIMAL;
            case NUMBER_INTEGER:
                return TurtleParaType.NUMBER_INTEGER;
            case LONG:
                return TurtleParaType.LONG;
            case STRING:
                return TurtleParaType.STRING;
            case DOUBLE:
                return TurtleParaType.DOUBLE;
            case INTEGER:
                return TurtleParaType.INTEGER;
            case BOOL:
                return TurtleParaType.BOOL;
            case COLLECTION:
                return TurtleParaType.COLLECTION;
            case TURTLE_VALUE:
                return TurtleParaType.TURTLE_VALUE;
            default:
                throw new UnsupportedOperationException("unSupport" + turtleParaType);
        }
    }

    public static TurtleProtoBuf.TurtleParaType convertToProtoParaType(TurtleParaType paraType) {
        Objects.requireNonNull(paraType);
        switch (paraType) {
            case TURTLE_VALUE:
                return TurtleProtoBuf.TurtleParaType.TURTLE_VALUE;
            case COLLECTION:
                return TurtleProtoBuf.TurtleParaType.COLLECTION;
            case INTEGER:
                return TurtleProtoBuf.TurtleParaType.INTEGER;
            case DOUBLE:
                return TurtleProtoBuf.TurtleParaType.DOUBLE;
            case STRING:
                return TurtleProtoBuf.TurtleParaType.STRING;

            case BOOL:
                return TurtleProtoBuf.TurtleParaType.BOOL;
            case NUMBER_INTEGER:
                return TurtleProtoBuf.TurtleParaType.NUMBER_INTEGER;
            case NUMBER_DECIMAL:
                return TurtleProtoBuf.TurtleParaType.NUMBER_DECIMAL;
            case LONG:
                return TurtleProtoBuf.TurtleParaType.LONG;
                
            default:
                throw new UnsupportedOperationException("unSupport" + paraType);
        }

    }

}

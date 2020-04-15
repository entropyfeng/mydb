package com.github.entropyfeng.mydb.common.protobuf;

import com.github.entropyfeng.mydb.common.TurtleParaType;
import com.github.entropyfeng.mydb.core.obj.TurtleValue;

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

    public static TurtleProtoBuf.TurtleCommonValue convertToCommonValue(TurtleParaType paraType, Object object) {

        switch (paraType) {
            case LONG:
                return TurtleProtoBuf.TurtleCommonValue.newBuilder().setLongValue((Long) object).build();
            case STRING:
                return TurtleProtoBuf.TurtleCommonValue.newBuilder().setStringValue((String) object).build();
            case NUMBER_INTEGER:
                return TurtleProtoBuf.TurtleCommonValue.newBuilder().setStringValue(((BigInteger) object).toString()).build();
            case NUMBER_DECIMAL:
                return TurtleProtoBuf.TurtleCommonValue.newBuilder().setStringValue(((BigDecimal) object).toString()).build();
            case INTEGER:
                return TurtleProtoBuf.TurtleCommonValue.newBuilder().setIntValue((Integer) object).build();
            case DOUBLE:
                return TurtleProtoBuf.TurtleCommonValue.newBuilder().setDoubleValue((Integer) object).build();
            case BOOL:
                return TurtleProtoBuf.TurtleCommonValue.newBuilder().setBoolValue((Boolean) object).build();

            case TURTLE_VALUE:
                return TurtleProtoBuf.TurtleCommonValue.newBuilder().setTurtleValue(ProtoTurtleHelper.convertToProtoTurtleValue((TurtleValue) object)).build();
            case COLLECTION:
                return constructCollection(((Collection<?>) object), paraType);
            default:
                throw new UnsupportedOperationException("unSupport" + paraType);
        }

    }

    public static TurtleProtoBuf.TurtleCommonValue constructCollection(Collection<?> collection, TurtleParaType turtleParaType) {
        Objects.requireNonNull(collection);
        //forbid nesting
        if (turtleParaType == TurtleParaType.COLLECTION) {
            throw new UnsupportedOperationException("collection's item can't be collection !");
        }

        TurtleProtoBuf.TurtleCollectionType.Builder builder = TurtleProtoBuf.TurtleCollectionType.newBuilder();
        builder.setCollectionType(convertToProtoParaType(turtleParaType));
        collection.forEach(o -> builder.addCollectionParas(convertToCommonValue(turtleParaType, o)));

        return TurtleProtoBuf.TurtleCommonValue.newBuilder().setCollectionValue(builder.build()).build();
    }


    /**
     * 将{@link com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf.TurtleCommonValue}
     * 转化为集合对象
     * @param type ProtoBuf中参数类型
     * @param values {@link com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf.TurtleCommonValue}集合
     * @return {@link Collection<?>}
     */
    public static Collection<?> handlerCollection(TurtleProtoBuf.TurtleParaType type, List<TurtleProtoBuf.TurtleCommonValue> values) {

        final int collectionSize = values.size();
        final List<Object> res = new ArrayList<>(collectionSize);

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
                values.forEach(value -> res.add(ProtoTurtleHelper.convertToTurtleValue(value.getTurtleValue())));
                break;
            case BOOL:
                values.forEach(value->res.add(value.getBoolValue()));
                break;
            default:
                throw new UnsupportedOperationException("unSupport operation " + type.toString());
        }
        return res;
    }


    /**
     * 判断object 是什么类的实例
     * @param object 不为集合
     * @return 参数类型
     */
    public static TurtleProtoBuf.TurtleParaType checkObjectType(Object object){

        if (object instanceof String) {
            return TurtleProtoBuf.TurtleParaType.STRING;
        } else if (object instanceof Integer) {
            return TurtleProtoBuf.TurtleParaType.INTEGER;
        } else if (object instanceof Long) {
            return TurtleProtoBuf.TurtleParaType.LONG;
        } else if (object instanceof Double) {
            return TurtleProtoBuf.TurtleParaType.DOUBLE;
        } else if (object instanceof BigInteger) {
            return TurtleProtoBuf.TurtleParaType.NUMBER_INTEGER;
        } else if (object instanceof BigDecimal) {
            return TurtleProtoBuf.TurtleParaType.NUMBER_DECIMAL;
        } else if (object instanceof TurtleValue) {
            return TurtleProtoBuf.TurtleParaType.TURTLE_VALUE;
        } else if (object instanceof Boolean) {
            return TurtleProtoBuf.TurtleParaType.BOOL;
        } else {
            throw new UnsupportedOperationException(object.getClass().getName());
        }
    }
}

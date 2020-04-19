package com.github.entropyfeng.mydb.common.protobuf;

import com.github.entropyfeng.mydb.core.TurtleValue;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public class ProtoCommonValueHelper {

    /**
     * 采用threadLocal可以避免频繁创建对象,但是可能会导致内存泄漏
     */
    public static ThreadLocal<TurtleProtoBuf.TurtleCommonValue.Builder> threadLocal=ThreadLocal.withInitial(TurtleProtoBuf.TurtleCommonValue::newBuilder);


    @NotNull
    public static TurtleProtoBuf.TurtleCommonValue turtleValue(TurtleValue turtleValue) {

        return threadLocal.get().clear().setTurtleValue(ProtoTurtleHelper.convertToProtoTurtleValue(turtleValue)).build();

    }

    @NotNull
    public static TurtleProtoBuf.TurtleCommonValue integerValue(Integer integer) {
        return threadLocal.get().clear().setIntValue(integer).build();
    }

    public static TurtleProtoBuf.TurtleCommonValue longValue(Long longValue) {
        return threadLocal.get().clear().setLongValue(longValue).build();
    }

    public static TurtleProtoBuf.TurtleCommonValue doubleValue(Double doubleValue) {
        return threadLocal.get().clear().setDoubleValue(doubleValue).build();
    }

    public static TurtleProtoBuf.TurtleCommonValue stringValue(String string) {
        return threadLocal.get().clear().setStringValue(string).build();
    }

    public static TurtleProtoBuf.TurtleCommonValue turtleValueCollection(Collection<TurtleValue> turtleValues) {
        TurtleProtoBuf.TurtleCollectionType.Builder collectionTypeBuilder = TurtleProtoBuf.TurtleCollectionType.newBuilder();
        collectionTypeBuilder.setCollectionType(TurtleProtoBuf.TurtleParaType.TURTLE_VALUE);
        turtleValues.forEach(turtleValue -> collectionTypeBuilder.addCollectionParas(turtleValue(turtleValue)));
        return threadLocal.get().clear().setCollectionValue(collectionTypeBuilder.build()).build();
    }


}

package com.github.entropyfeng.mydb.common.protobuf;

import com.github.entropyfeng.mydb.core.obj.TurtleValue;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public class CommonValueHelper {


    @NotNull
    public static TurtleProtoBuf.TurtleCommonValue turtleValue(TurtleValue turtleValue){

      return TurtleProtoBuf.TurtleCommonValue.newBuilder().setTurtleValue(ProtoTurtleHelper.convertToProtoTurtleValue(turtleValue)).build();

    }
    @NotNull
    public static TurtleProtoBuf.TurtleCommonValue integerValue(Integer integer){
        return TurtleProtoBuf.TurtleCommonValue.newBuilder().setIntValue(integer).build();
    }
    public static TurtleProtoBuf.TurtleCommonValue longValue(Long longValue){
        return TurtleProtoBuf.TurtleCommonValue.newBuilder().setLongValue(longValue).build();
    }

    public static TurtleProtoBuf.TurtleCommonValue turtleValueCollection(Collection<TurtleValue> turtleValues){

        TurtleProtoBuf.TurtleCollectionType.Builder collectionTypeBuilder= TurtleProtoBuf.TurtleCollectionType.newBuilder();
        collectionTypeBuilder.setCollectionType(TurtleProtoBuf.TurtleParaType.TURTLE_VALUE);

        turtleValues.forEach(turtleValue ->collectionTypeBuilder.addCollectionParas(turtleValue(turtleValue)));
        return TurtleProtoBuf.TurtleCommonValue.newBuilder().setCollectionValue(collectionTypeBuilder.build()).build();

    }


}

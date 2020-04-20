package com.github.entropyfeng.mydb.common.protobuf;

import com.github.entropyfeng.mydb.core.TurtleValue;
import org.jetbrains.annotations.NotNull;

/**
 * @author entropyfeng
 */
public class SingleResHelper {

    public static TurtleProtoBuf.ResponseData voidResponse() {

        TurtleProtoBuf.ResponseData.Builder builder = TurtleProtoBuf.ResponseData.newBuilder();
        builder.setCollectionAble(false);
        builder.setSuccess(true);
        return builder.build();
    }

    public static TurtleProtoBuf.ResponseData boolResponse(boolean res) {

        TurtleProtoBuf.ResponseData.Builder builder = TurtleProtoBuf.ResponseData.newBuilder();
        builder.setCollectionAble(false);
        builder.setSuccess(true);
        builder.setBoolValue(res);
        return builder.build();
    }

    public static TurtleProtoBuf.ResponseData integerResponse(Integer integer) {

        TurtleProtoBuf.ResponseData.Builder builder = TurtleProtoBuf.ResponseData.newBuilder();
        builder.setCollectionAble(false);
        builder.setSuccess(true);
        builder.setIntValue(integer);
        return builder.build();
    }

    public static TurtleProtoBuf.ResponseData turtleValueResponse(@NotNull TurtleValue turtleValue) {
        TurtleProtoBuf.ResponseData.Builder builder = TurtleProtoBuf.ResponseData.newBuilder();
        builder.setCollectionAble(false);
        builder.setSuccess(true);
        builder.setTurtleValue(ProtoTurtleHelper.convertToProtoTurtleValue(turtleValue));
        return builder.build();
    }

    public static TurtleProtoBuf.ResponseData nullResponse() {
        TurtleProtoBuf.ResponseData.Builder builder = TurtleProtoBuf.ResponseData.newBuilder();
        builder.setCollectionAble(false);
        builder.setSuccess(true);
        builder.setNullable(true);
        return builder.build();
    }

    public static TurtleProtoBuf.ResponseData noSuchElementException() {
        TurtleProtoBuf.ResponseData.Builder builder = TurtleProtoBuf.ResponseData.newBuilder();
        builder.setSuccess(false);
        builder.setCollectionAble(false);
        builder.setExceptionType(TurtleProtoBuf.ExceptionType.NoSuchElementException);
        return builder.build();
    }
    public static TurtleProtoBuf.ResponseData unSupportOperationException() {
        TurtleProtoBuf.ResponseData.Builder builder = TurtleProtoBuf.ResponseData.newBuilder();
        builder.setSuccess(false);
        builder.setCollectionAble(false);
        builder.setExceptionType(TurtleProtoBuf.ExceptionType.UnsupportedOperationException);
        return builder.build();
    }
    public static TurtleProtoBuf.ResponseData fatalException(@NotNull String msg){
        TurtleProtoBuf.ResponseData.Builder builder = TurtleProtoBuf.ResponseData.newBuilder();
        builder.setSuccess(false);
        builder.setCollectionAble(false);
        builder.setExceptionType(TurtleProtoBuf.ExceptionType.TurtleFatalError);
        builder.setException(msg);
        return builder.build();
    }
    public static TurtleProtoBuf.ResponseData elementOutOfBoundException(@NotNull String msg){
        TurtleProtoBuf.ResponseData.Builder builder = TurtleProtoBuf.ResponseData.newBuilder();
        builder.setSuccess(false);
        builder.setCollectionAble(false);
        builder.setExceptionType(TurtleProtoBuf.ExceptionType.ElementOutOfBoundException);
        builder.setException(msg);
        return builder.build();
    }
    public static TurtleProtoBuf.ResponseData turtleDesignException(@NotNull String msg){
        TurtleProtoBuf.ResponseData.Builder builder = TurtleProtoBuf.ResponseData.newBuilder();
        builder.setSuccess(false);
        builder.setCollectionAble(false);
        builder.setExceptionType(TurtleProtoBuf.ExceptionType.TurtleDesignError);
        builder.setException(msg);
        return builder.build();
    }
}

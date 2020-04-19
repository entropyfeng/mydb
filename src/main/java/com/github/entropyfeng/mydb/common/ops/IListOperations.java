package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author entropyfeng
 */
public interface IListOperations {

    public @NotNull TurtleProtoBuf.ResponseData size();


    public @NotNull TurtleProtoBuf.ResponseData sizeOf(String key);

    public @NotNull TurtleProtoBuf.ResponseData leftPush(String key, TurtleValue value);

    public default @NotNull TurtleProtoBuf.ResponseData leftPushAll(String key, TurtleValue... values) {
       return leftPushAll(key, Arrays.asList(values));
       
    }

    public @NotNull TurtleProtoBuf.ResponseData leftPushAll(String key, Collection<TurtleValue> values);
    
    public @NotNull TurtleProtoBuf.ResponseData leftPushIfPresent(String key, TurtleValue value);

    public @NotNull TurtleProtoBuf.ResponseData leftPushIfAbsent(String key, TurtleValue value);

    public @NotNull TurtleProtoBuf.ResponseData rightPush(String key, TurtleValue value);


    public @NotNull TurtleProtoBuf.ResponseData rightPushAll(String key, Collection<TurtleValue> values);

    public @NotNull TurtleProtoBuf.ResponseData rightPushIfPresent(String key, TurtleValue value);

    public @NotNull TurtleProtoBuf.ResponseData rightPushIfAbsent(String key, TurtleValue value);

    public @NotNull TurtleProtoBuf.ResponseData leftPop(String key);
    
    public @NotNull TurtleProtoBuf.ResponseData left(String key);

    public @NotNull TurtleProtoBuf.ResponseData rightPop(String key);

    public @NotNull TurtleProtoBuf.ResponseData right(String key);

    public @NotNull TurtleProtoBuf.ResponseData clear(String key);

    public @NotNull TurtleProtoBuf.ResponseData clearAll();

    public @NotNull TurtleProtoBuf.ResponseData exist(String key);

    public @NotNull TurtleProtoBuf.ResponseData exist(String key,TurtleValue value);
}

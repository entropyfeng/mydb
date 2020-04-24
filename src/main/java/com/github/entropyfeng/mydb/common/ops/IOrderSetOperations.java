package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public interface IOrderSetOperations {

    public @NotNull TurtleProtoBuf.ResponseData exists(String key, TurtleValue value, Double score);

    public @NotNull TurtleProtoBuf.ResponseData exists(String key, TurtleValue value);

    public @NotNull TurtleProtoBuf.ResponseData exists(String key);

    public @NotNull TurtleProtoBuf.ResponseData add(String key, TurtleValue value, Double score);

    public @NotNull TurtleProtoBuf.ResponseData add(String key, Collection<TurtleValue> values, Collection<Double> doubles);

    public @NotNull TurtleProtoBuf.ResponseData count(String key, Double begin, Double end);

    public @NotNull  Collection<TurtleProtoBuf.ResponseData> range(String key, Double begin, Double end);

    public @NotNull TurtleProtoBuf.ResponseData inRange(String key, Double begin, Double end);

    public @NotNull TurtleProtoBuf.ResponseData delete(String key, TurtleValue value, Double score);

    public @NotNull TurtleProtoBuf.ResponseData delete(String key, Double begin, Double end);

    public @NotNull TurtleProtoBuf.ResponseData delete(String key, TurtleValue value);

    public @NotNull TurtleProtoBuf.ResponseData delete(String key);



}

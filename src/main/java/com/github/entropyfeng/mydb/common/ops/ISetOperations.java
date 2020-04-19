package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public interface ISetOperations {

    public @NotNull TurtleProtoBuf.ResponseData exist(String key);

    public @NotNull TurtleProtoBuf.ResponseData exist(String key, TurtleValue value);

    public @NotNull TurtleProtoBuf.ResponseData add(String key, TurtleValue value);

    public @NotNull TurtleProtoBuf.ResponseData union(String key, String otherKey);

    public @NotNull TurtleProtoBuf.ResponseData union(String key, Collection<TurtleValue> turtleValues);

    public @NotNull TurtleProtoBuf.ResponseData intersect(String key, String otherKey);

    public @NotNull TurtleProtoBuf.ResponseData intersect(String key, Collection<TurtleValue> turtleValues);

    public @NotNull TurtleProtoBuf.ResponseData difference(String key, String otherKey);

    public @NotNull TurtleProtoBuf.ResponseData difference(String key, Collection<TurtleValue> turtleValues);

    public @NotNull Collection<TurtleProtoBuf.ResponseData> entries(String key);
}

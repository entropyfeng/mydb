package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public interface IHashOperations {

    @NotNull TurtleProtoBuf.ResponseData get(@NotNull String key, @NotNull TurtleValue tKey);

    @NotNull Collection<TurtleProtoBuf.ResponseData> get(@NotNull String key);

    @NotNull TurtleProtoBuf.ResponseData exists(@NotNull String key, @NotNull TurtleValue tKey, TurtleValue tValue);

    @NotNull TurtleProtoBuf.ResponseData exists(@NotNull String key);

    @NotNull TurtleProtoBuf.ResponseData put(@NotNull String key, @NotNull TurtleValue tKey, @NotNull TurtleValue tValue);

    @NotNull TurtleProtoBuf.ResponseData delete(@NotNull String key);

    @NotNull TurtleProtoBuf.ResponseData delete(@NotNull String key, @NotNull TurtleValue tKey);

    @NotNull TurtleProtoBuf.ResponseData sizeOf(@NotNull String key);

}

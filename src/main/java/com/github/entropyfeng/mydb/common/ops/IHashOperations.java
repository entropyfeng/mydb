package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;
import org.checkerframework.checker.initialization.qual.NotOnlyInitialized;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public interface IHashOperations {

    public @NotNull TurtleProtoBuf.ResponseData get(@NotNull String key, @NotNull TurtleValue tKey);

    public @NotNull Collection<TurtleProtoBuf.ResponseData> get(@NotNull String key);

    public @NotNull TurtleProtoBuf.ResponseData exists(@NotNull String key,@NotNull TurtleValue tKey,TurtleValue tValue);

    public @NotNull TurtleProtoBuf.ResponseData exists(@NotNull String key);

    public @NotNull TurtleProtoBuf.ResponseData put(@NotNull String key,@NotNull TurtleValue tKey,@NotNull TurtleValue tValue);

    public @NotNull TurtleProtoBuf.ResponseData delete(@NotNull String key);

    public @NotNull TurtleProtoBuf.ResponseData delete(@NotNull String key,@NotNull TurtleValue tKey);

    public @NotNull TurtleProtoBuf.ResponseData sizeOf(@NotNull String key);

    public @NotNull TurtleProtoBuf.ResponseData sizeOf(@NotNull String key,@NotNull TurtleValue tKey);
}

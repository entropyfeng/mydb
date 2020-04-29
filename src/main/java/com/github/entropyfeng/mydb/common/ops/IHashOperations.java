package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public interface IHashOperations {

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> get(@NotNull String key, @NotNull TurtleValue tKey);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> get(@NotNull String key);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> exists(@NotNull String key, @NotNull TurtleValue tKey, TurtleValue tValue);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> exists(@NotNull String key);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> put(@NotNull String key, @NotNull TurtleValue tKey, @NotNull TurtleValue tValue);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> delete(@NotNull String key);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> delete(@NotNull String key, @NotNull TurtleValue tKey);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> sizeOf(@NotNull String key);

}

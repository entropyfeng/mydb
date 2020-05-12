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

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> get(@NotNull String key, @NotNull TurtleValue tKey);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> get(@NotNull String key);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> exists(@NotNull String key, @NotNull TurtleValue tKey, TurtleValue tValue);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> exists(@NotNull String key);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> put(@NotNull String key, @NotNull TurtleValue tKey, @NotNull TurtleValue tValue);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> delete(@NotNull String key);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> delete(@NotNull String key, @NotNull TurtleValue tKey);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> sizeOf(@NotNull String key);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> clear();

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> dump();
}

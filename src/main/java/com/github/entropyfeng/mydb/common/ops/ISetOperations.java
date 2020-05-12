package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public interface ISetOperations {

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> exist(String key);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> exist(String key, TurtleValue value);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> add(String key, TurtleValue value);

    /**
     * 只做并操作不返回值
     *
     * @param key      key
     * @param otherKey other key
     * @return responseData
     */
    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> union(String key, String otherKey);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> union(String key, Collection<TurtleValue> turtleValues);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> unionAndGet(String key, String otherKey);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> unionAndGet(String key, Collection<TurtleValue> turtleValues);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> intersect(String key, String otherKey);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> intersect(String key, Collection<TurtleValue> turtleValues);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> difference(String key, String otherKey);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> difference(String key, Collection<TurtleValue> turtleValues);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> entries(String key);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> clear();

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> dump();
}

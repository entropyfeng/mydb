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

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> exist(String key);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> exist(String key, TurtleValue value);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> add(String key, TurtleValue value);

    /**
     * 只做并操作不返回值
     *
     * @param key      key
     * @param otherKey other key
     * @return responseData
     */
    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> union(String key, String otherKey);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> union(String key, Collection<TurtleValue> turtleValues);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> unionAndGet(String key, String otherKey);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> unionAndGet(String key, Collection<TurtleValue> turtleValues);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> intersect(String key, String otherKey);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> intersect(String key, Collection<TurtleValue> turtleValues);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> difference(String key, String otherKey);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> difference(String key, Collection<TurtleValue> turtleValues);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> entries(String key);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> clear();
}

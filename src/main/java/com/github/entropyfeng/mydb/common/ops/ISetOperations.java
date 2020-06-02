package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.TurtleValue;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.DataBody;
import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;

/**
 * @author entropyfeng
 */
public interface ISetOperations {

    /**
     * 判断是否存在以key为键值的集合
     * @param key 键
     * @return data
     */
    @NotNull Pair<ResHead, Collection<DataBody>> exist(String key);

    /**
     * 判断是否存在以key为键的集合，其具有一个value值
     * @param key 键
     * @param value 值
     * @return data
     */
    @NotNull Pair<ResHead, Collection<DataBody>> exist(String key, TurtleValue value);

    @NotNull Pair<ResHead, Collection<DataBody>> add(String key, TurtleValue value);

    /**
     * 只做并操作不返回值
     *
     * @param key      key
     * @param otherKey other key
     * @return responseData
     */
    @NotNull Pair<ResHead, Collection<DataBody>> union(String key, String otherKey);

    @NotNull Pair<ResHead, Collection<DataBody>> union(String key, Collection<TurtleValue> turtleValues);

    @NotNull Pair<ResHead, Collection<DataBody>> unionAndGet(String key, String otherKey);

    @NotNull Pair<ResHead, Collection<DataBody>> unionAndGet(String key, Collection<TurtleValue> turtleValues);

    @NotNull Pair<ResHead, Collection<DataBody>> intersect(String key, String otherKey);

    @NotNull Pair<ResHead, Collection<DataBody>> intersect(String key, Collection<TurtleValue> turtleValues);

    @NotNull Pair<ResHead, Collection<DataBody>> difference(String key, String otherKey);

    @NotNull Pair<ResHead, Collection<DataBody>> difference(String key, Collection<TurtleValue> turtleValues);

    /**
     * 返回该key所对应的所有值
     * @param key 键
     * @return data
     */
    @NotNull Pair<ResHead, Collection<DataBody>> entries(String key);

    @NotNull Pair<ResHead, Collection<DataBody>> size();

    @NotNull Pair<ResHead, Collection<DataBody>> sizeOf(String key);

    @NotNull Pair<ResHead, Collection<DataBody>> clear();

    @NotNull Pair<ResHead, Collection<DataBody>> dump();
}

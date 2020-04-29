package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.common.exception.ElementOutOfBoundException;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;
import com.github.entropyfeng.mydb.core.helper.Pair;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * @author entropyfeng
 */
public interface IValueOperations {

    /**
     * 根据 key 插入 value 并在time时过期（以毫秒为单位）
     * @param key   {@link String}
     * @param value 值{@link TurtleValue}
     * @param time  过期时间
     * @throws ElementOutOfBoundException when current mapSize>=Integer.MAX will throw this exception
     */
    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> set(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time);

    /**
     * 如果存在key所对应的值啧用新值替换旧值
     *
     * @param key   {@link String}
     * @param value {@link TurtleValue}
     * @return null->调用失败,并抛出异常
     * true->设置成功
     * false->设置失败
     */
    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> setIfAbsent(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time);


    /**
     * 如果存在key所对应的值，则用新值替换旧值,并设置过期时间
     *
     * @param key      {@link String}
     * @param value    {@link TurtleValue}
     * @param time     时间戳 毫秒单位
     * @return null->调用失败,并抛出异常
     * true->设置成功
     * false->设置失败
     */
    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> setIfPresent(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time);


    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> get(@NotNull String key);


    /**
     *
     * @param key key
     * @param intValue intValue
     * @throws UnsupportedOperationException 不支持该操作
     * @throws NoSuchElementException        数据库中不含该key
     */
    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> increment(@NotNull String key, @NotNull Integer intValue) throws UnsupportedOperationException, NoSuchElementException;

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> increment(@NotNull String key, @NotNull Long longValue) throws UnsupportedOperationException, NoSuchElementException;

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> increment(@NotNull String key, @NotNull Double doubleValue) throws UnsupportedOperationException, NoSuchElementException;

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> increment(@NotNull String key, @NotNull BigInteger bigInteger) throws UnsupportedOperationException, NoSuchElementException;


    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> increment(@NotNull String key, @NotNull BigDecimal bigDecimal) throws UnsupportedOperationException, NoSuchElementException;

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> append(@NotNull String key, @NotNull String appendValue) throws UnsupportedOperationException, NoSuchElementException;

    /**
     * 返回所有的values(不包含key)
     * @return res collection
     */
    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> allValues();

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> allEntries();

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> allKeys();
}

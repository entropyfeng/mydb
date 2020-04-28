package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;
import com.github.entropyfeng.mydb.core.helper.Pair;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

/**
 * @author entropyfeng
 */
public interface IValueOperations {
    /**
     * 根据 key 插入 value,无过期时间
     * @param key   {@link String}
     * @param value {@link TurtleValue}
     */
    default @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> set(@NotNull String key, @NotNull TurtleValue value) {
        return set(key, value, 0L);

    }
    
    /**
     * 根据 key 插入 value 并在time时过期（mill）
     *
     * @param key   {@link String}
     * @param value 值{@link TurtleValue}
     * @param time  过期时间
     */
    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> set(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time);

    /**
     * @param key      {@link String}
     * @param value    {@link TurtleValue}
     * @param time     时间戳(毫秒表示)
     * @param timeUnit {@link TimeUnit}
     */
    default @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> set(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time, TimeUnit timeUnit) {
       return this.set(key, value, timeUnit.toMillis(time));
    }

    default @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> setIfAbsent(@NotNull String key, @NotNull TurtleValue value) {
        return this.setIfAbsent(key, value, 0L);
    }


    default @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> setIfAbsent(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time, @NotNull TimeUnit timeUnit) {

        return setIfAbsent(key, value, timeUnit.toMillis(time));
    }


    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> setIfAbsent(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time);


    /**
     * 如果存在key所对应的值啧用新值替换旧值
     *
     * @param key   {@link String}
     * @param value {@link TurtleValue}
     * @return null->调用失败,并抛出异常
     * true->设置成功
     * false->设置失败
     */
    default @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> setIfPresent(@NotNull String key, @NotNull TurtleValue value) {
        return this.setIfPresent(key, value, 0L);
    }

    /**
     * 如果存在key所对应的值，则用新值替换旧值,并设置过期时间
     *
     * @param key      {@link String}
     * @param value    {@link TurtleValue}
     * @param time     时间戳 毫秒单位
     * @param timeUnit {@link TimeUnit}
     * @return null->调用失败,并抛出异常
     * true->设置成功
     * false->设置失败
     */
    default @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> setIfPresent(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time, @NotNull TimeUnit timeUnit) {

        return setIfPresent(key, value, timeUnit.toMillis(time));
    }


    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> setIfPresent(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time);


    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> get(@NotNull String key);


    /**
     *
     * @param key key
     * @param intValue intValue
     * @return {@link Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>>}
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

package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.common.exception.ElementOutOfBoundException;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.Pair;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author entropyfeng
 */
public interface ValueOperations {

    /**
     * 根据 key 插入 value,无过期时间
     *
     * @param key   {@link String}
     * @param value {@link TurtleValue}
     * @exception ElementOutOfBoundException 元素超限
     */
    default void set(String key, TurtleValue value)throws ElementOutOfBoundException {
        this.set(key, value, 0L);
    }

    /**
     *
     * 根据 key 插入 value 并在time时过期（mill）
     * @param key   {@link String}
     * @param value 值{@link TurtleValue}
     * @param time  过期时间
     * @exception ElementOutOfBoundException 元素超限
     */
    void set(String key, TurtleValue value, Long time)throws ElementOutOfBoundException;

    /**
     * 设置包含过期时间的键值对
     * @param key      {@link String}
     * @param value    {@link TurtleValue}
     * @param time     时间戳(毫秒表示)
     * @param timeUnit {@link TimeUnit}
     * @exception ElementOutOfBoundException 元素超限
     */
    default void set(String key, TurtleValue value, Long time, TimeUnit timeUnit)throws ElementOutOfBoundException {
        Objects.requireNonNull(timeUnit);
        this.set(key, value, timeUnit.toMillis(time));
    }

    default Boolean setIfAbsent(String key, TurtleValue value)throws ElementOutOfBoundException {
        return setIfAbsent(key, value, 0L);
    }


    default Boolean setIfAbsent(String key, TurtleValue value, Long time, TimeUnit timeUnit)throws ElementOutOfBoundException {

        Objects.requireNonNull(timeUnit);
        return setIfAbsent(key, value, timeUnit.toMillis(time));
    }


    Boolean setIfAbsent(String key, TurtleValue value, Long time)throws ElementOutOfBoundException;


    /**
     * 如果存在key所对应的值啧用新值替换旧值
     *
     * @param key   {@link String}
     * @param value {@link TurtleValue}
     * @return null->调用失败,并抛出异常
     * true->设置成功
     * false->设置失败
     */
    default Boolean setIfPresent(String key, TurtleValue value) {
        return setIfPresent(key, value, 0L);
    }

    /**
     * 如果存在key所对应的值，则用新值替换旧值,并设置过期时间
     * @param key      {@link String}
     * @param value    {@link TurtleValue}
     * @param time     时间戳 毫秒单位
     * @param timeUnit {@link TimeUnit}
     * @return null->调用失败,并抛出异常
     * true->设置成功
     * false->设置失败
     */
    default Boolean setIfPresent(String key, TurtleValue value, Long time, TimeUnit timeUnit) {

        Objects.requireNonNull(timeUnit);
        return setIfPresent(key, value, timeUnit.toMillis(time));
    }


    Boolean setIfPresent(String key, TurtleValue value, Long time);


    TurtleValue get(String key);


    /**
     * 将key所对应的value 增加 int
     * @param key the key
     * @param intValue 整数值
     * @return {@link TurtleValue}
     * @throws UnsupportedOperationException  when operation on a byte[]
     */
    TurtleValue increment(String key, Integer intValue) throws UnsupportedOperationException;

    TurtleValue increment(String key, Long longValue) throws UnsupportedOperationException;

    TurtleValue increment(String key, Double doubleValue) throws UnsupportedOperationException;

    TurtleValue increment(String key, BigInteger bigInteger) throws UnsupportedOperationException;

    TurtleValue increment(String key, BigDecimal bigDecimal) throws UnsupportedOperationException;

    void append(String key, String appendValue) throws UnsupportedOperationException;

    Collection<TurtleValue> allValues();

    Collection<String> allKeys();

    Collection<Pair<String,TurtleValue>> allEntries();
}

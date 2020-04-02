package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.core.obj.TurtleValue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author entropyfeng
 */
public interface ValueOperations {

    /**
     * 根据 key 插入 value,无过期时间
     * @param key {@link String}
     * @param value {@link TurtleValue}
     */
    default void set(String key, TurtleValue value) {
        this.set(key, value, 0L);
    }

    /**
     * 根据 key 插入 value 并在time时过期（mill）
     * @param key {@link String}
     * @param value 值{@link TurtleValue}
     * @param time 过期时间
     */
    void set(String key, TurtleValue value, long time);

    /**
     * @param key {@link String}
     * @param value {@link TurtleValue}
     * @param time 时间戳(毫秒表示)
     * @param timeUnit {@link TimeUnit}
     */
    default void set(String key, TurtleValue value , long time, TimeUnit timeUnit) {
        Objects.requireNonNull(timeUnit);
        this.set(key, value,timeUnit.toMillis(time));
    }

   default Boolean setIfAbsent(String key, TurtleValue value){
       return setIfAbsent(key, value,0L);
   }


   default Boolean setIfAbsent(String key, TurtleValue value, long time, TimeUnit timeUnit){

        Objects.requireNonNull(timeUnit);
       return setIfAbsent(key, value,timeUnit.toMillis(time));
   }


    Boolean setIfAbsent(String key, TurtleValue value, long time);


    /**
     * 如果存在key所对应的值啧用新值替换旧值
     * @param key {@link String}
     * @param value {@link TurtleValue}
     * @return null->调用失败,并抛出异常
     *         true->设置成功
     *         false->设置失败
     */
    default Boolean setIfPresent(String key, TurtleValue value){
        return setIfPresent(key, value,0L);
    }

    /**
     * 如果存在key所对应的值，则用新值替换旧值,并设置过期时间
     * @param key {@link String}
     * @param value {@link TurtleValue}
     * @param time 时间戳 毫秒单位
     * @param timeUnit {@link TimeUnit}
     * @return null->调用失败,并抛出异常
     *         true->设置成功
     *         false->设置失败
     */
    default Boolean setIfPresent(String key, TurtleValue value, long time, TimeUnit timeUnit){

        Objects.requireNonNull(timeUnit);
        return setIfPresent(key, value,timeUnit.toMillis(time));
    }


    Boolean setIfPresent(String key, TurtleValue value, long time);


    TurtleValue get(String key);


    TurtleValue increment(String key,int intValue)throws UnsupportedOperationException, NoSuchElementException;

    TurtleValue increment(String key, long longValue)throws UnsupportedOperationException,NoSuchElementException;

    TurtleValue increment(String key, double doubleValue)throws UnsupportedOperationException,NoSuchElementException;

    TurtleValue increment(String key, BigInteger bigInteger)throws UnsupportedOperationException,NoSuchElementException;

    TurtleValue increment(String key, BigDecimal bigDecimal)throws UnsupportedOperationException,NoSuchElementException;

    void append(String key, String appendValue)throws UnsupportedOperationException,NoSuchElementException;

}

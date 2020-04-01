package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.core.obj.TurtleValue;
import com.github.entropyfeng.mydb.expection.TurtleNullPointerException;

import java.math.BigDecimal;
import java.math.BigInteger;
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
       if (timeUnit==null){
           throw new TurtleNullPointerException("timeUnit or time is null .");
       }
       return setIfAbsent(key, value,timeUnit.toMillis(time));
   }


    Boolean setIfAbsent(String key, TurtleValue value, long time);


    default Boolean setIfPresent(String key, TurtleValue value){
        return setIfPresent(key, value,0L);
    }

    default Boolean setIfPresent(String key, TurtleValue value, long time, TimeUnit timeUnit){
        if (timeUnit==null){
            throw new TurtleNullPointerException("timeUnit is null .");
        }
        return setIfPresent(key, value,timeUnit.toMillis(time));
    }


    Boolean setIfPresent(String key, TurtleValue value, long time);


    TurtleValue get(String key);


    TurtleValue increment(String key,int intValue)throws UnsupportedOperationException;

    TurtleValue increment(String key, long longValue)throws UnsupportedOperationException;

    TurtleValue increment(String key, double doubleValue)throws UnsupportedOperationException;

    TurtleValue increment(String key, BigInteger bigInteger)throws UnsupportedOperationException;

    TurtleValue increment(String key, BigDecimal bigDecimal)throws UnsupportedOperationException;

    Boolean append(String key, String appendValue)throws UnsupportedOperationException;

}

package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.core.obj.TurtleValue;
import com.github.entropyfeng.mydb.expection.TurtleNullPointerException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author entropyfeng
 */
public interface ValueOperations {

    /**
     *
     * @param key
     * @param value
     */
    default void set(String key, TurtleValue value) {
        this.set(key, value, 0L);
    }

    /**
     *
     * @param key 键
     * @param value 值
     * @param time 过期时间
     */
    void set(String key, TurtleValue value, long time);

    default void set(String key, TurtleValue value , long time, TimeUnit timeUnit) {
        if (timeUnit==null){
            throw new TurtleNullPointerException("timeUnit is null .");
        }
        this.set(key, value,timeUnit.toMillis(time));
    }

   default boolean setIfAbsent(String key, TurtleValue value){
       return setIfAbsent(key, value,0L);
   }


   default boolean setIfAbsent(String key, TurtleValue value, long time, TimeUnit timeUnit){
       if (timeUnit==null){
           throw new TurtleNullPointerException("timeUnit or time is null .");
       }
       return setIfAbsent(key, value,timeUnit.toMillis(time));
   }


    boolean setIfAbsent(String key, TurtleValue value, long time);


    default boolean setIfPresent(String key, TurtleValue value){
        return setIfPresent(key, value,0L);
    }


    default boolean setIfPresent(String key, TurtleValue value, long time, TimeUnit timeUnit){
        if (timeUnit==null){
            throw new TurtleNullPointerException("timeUnit is null .");
        }
        return setIfPresent(key, value,timeUnit.toMillis(time));
    }


    boolean setIfPresent(String key, TurtleValue value, long time);


    TurtleValue get(String key);


    Object increment(String key,int intValue)throws UnsupportedOperationException;

    Object increment(String key, long longValue)throws UnsupportedOperationException;

    Object increment(String key, double doubleValue)throws UnsupportedOperationException;

    Object increment(String key, BigInteger bigInteger)throws UnsupportedOperationException;

    Object increment(String key, BigDecimal bigDecimal)throws UnsupportedOperationException;


    boolean append(String key, String appendValue)throws UnsupportedOperationException;




}

package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.expection.TurtleNullPointerException;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author entropyfeng
 */
public interface ValueOperations {

    default void set(String key, TurtleValue value) {
        this.set(key, value, 0L);
    }

    /**
     *
     * @param key 键
     * @param value 值
     * @param time 过期时间
     */
    void set(String key, TurtleValue value, Long time);

    default void set(String key, TurtleValue value , Long time, TimeUnit timeUnit) {
        if (timeUnit==null){
            throw new TurtleNullPointerException("timeUnit is null .");
        }
        this.set(key, value,timeUnit.toMillis(time));
    }

   default boolean setIfAbsent(String key, TurtleValue value){
       return setIfAbsent(key, value,0L);
   }


   default boolean setIfAbsent(String key, TurtleValue value, Long time, TimeUnit timeUnit){
       if (timeUnit==null){
           throw new TurtleNullPointerException("timeUnit is null .");
       }
       return setIfAbsent(key, value,timeUnit.toMillis(time));
   }


    boolean setIfAbsent(String key, TurtleValue value, Long time);


    default boolean setIfPresent(String key, TurtleValue value){
        return setIfPresent(key, value,0L);
    }


    default boolean setIfPresent(String key, TurtleValue value, Long time, TimeUnit timeUnit){
        if (timeUnit==null){
            throw new TurtleNullPointerException("timeUnit is null .");
        }
        return setIfPresent(key, value,timeUnit.toMillis(time));
    }


    boolean setIfPresent(String key, TurtleValue value, Long time);


    TurtleValue get(String key);


    Object increment(String key)throws UnsupportedOperationException;


    Object increment(String key, long longValue)throws UnsupportedOperationException;


    Object increment(String key, double doubleValue)throws UnsupportedOperationException;



    boolean append(String key, String appendValue);




}

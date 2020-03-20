package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.core.obj.TurtleObject;
import com.github.entropyfeng.mydb.expection.TurtleNullPointerException;

import java.util.concurrent.TimeUnit;

/**
 *
 * @author entropyfeng
 */
public interface ValueOperations {

    default void set(String key, TurtleObject value) {
        this.set(key, value, 0);
    }

    /**
     *
     * @param key 键
     * @param value 值
     * @param time 过期时间
     */
    void set(String key, TurtleObject value, long time);

    default void set(String key, TurtleObject value , long time, TimeUnit timeUnit) {
        if (timeUnit==null){
            throw new TurtleNullPointerException("timeUnit is null .");
        }
        this.set(key, value,timeUnit.toMillis(time));
    }

   default boolean setIfAbsent(String key, TurtleObject value){
       return setIfAbsent(key, value,0);
   }


   default boolean setIfAbsent(String key, TurtleObject value, long time, TimeUnit timeUnit){
       if (timeUnit==null){
           throw new TurtleNullPointerException("timeUnit is null .");
       }
       return setIfAbsent(key, value,timeUnit.toMillis(time));
   }


    boolean setIfAbsent(String key, TurtleObject value, long time);


    default boolean setIfPresent(String key, TurtleObject value){
        return setIfPresent(key, value,0);
    }


    default boolean setIfPresent(String key, TurtleObject value, long time, TimeUnit timeUnit){
        if (timeUnit==null){
            throw new TurtleNullPointerException("timeUnit is null .");
        }
        return setIfPresent(key, value,timeUnit.toMillis(time));
    }


    boolean setIfPresent(String key, TurtleObject value, long time);


    TurtleObject get(String key);


    Object increment(String key);


    Object increment(String key, long longValue);


    Object increment(String key, double doubleValue);



    boolean append(String key, String appendValue);




}

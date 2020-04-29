package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.common.TurtleValue;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author entropyfeng
 */
public interface ListOperations {

    Integer size();

    Integer sizeOf(String key);

    void leftPush(String key, TurtleValue value);

    default void leftPushAll(String key, TurtleValue... values) {
        leftPushAll(key, Arrays.asList(values));

    }

    void leftPushAll(String key, Collection<TurtleValue> values);


    Boolean leftPushIfPresent(String key, TurtleValue value);

    Boolean leftPushIfAbsent(String key, TurtleValue value);

    void rightPush(String key, TurtleValue value);


    void rightPushAll(String key, Collection<TurtleValue> values);

    Boolean rightPushIfPresent(String key, TurtleValue value);

    Boolean rightPushIfAbsent(String key, TurtleValue value);

    TurtleValue leftPop(String key);
    TurtleValue left(String key);

    TurtleValue rightPop(String key);

    TurtleValue right(String key);

    void clear(String key);

    void clear();

    Boolean exist(String key);

    Boolean exist(String key, TurtleValue value);

}

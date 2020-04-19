package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.core.TurtleValue;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author entropyfeng
 */
public interface ListOperations {

    public Integer size();


    public Integer sizeOf(String key);

    public Void leftPush(String key, TurtleValue value);

    public Void leftPush(String key,TurtleValue value,long time);

    public default Void leftPushAll(String key, TurtleValue... values) {
        leftPushAll(key, Arrays.asList(values));
        return null;
    }

    public Void leftPushAll(String key, Collection<TurtleValue> values);


    public Boolean leftPushIfPresent(String key, TurtleValue value);

    public Boolean leftPushIfAbsent(String key, TurtleValue value);

    public Void rightPush(String key, TurtleValue value);


    public Void rightPushAll(String key, Collection<TurtleValue> values);

    public Boolean rightPushIfPresent(String key, TurtleValue value);

    public Boolean rightPushIfAbsent(String key, TurtleValue value);

    public TurtleValue leftPop(String key);
    public TurtleValue left(String key);

    public TurtleValue rightPop(String key);

    public TurtleValue right(String key);

    public Void clear(String key);

    public Void clearAll();

    public Boolean exist(String key);

    public Boolean exist(String key,TurtleValue value);

}

package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.core.TurtleValue;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public interface SetOperations {

    Boolean exist(String key);

    Boolean exist(String key, TurtleValue value);

    void add(String key, TurtleValue value);

    Collection<TurtleValue> union(String key, String otherKey);

    Collection<TurtleValue> union(String key, Collection<TurtleValue> turtleValues);

    Collection<TurtleValue> intersect(String key, String otherKey);

    Collection<TurtleValue> intersect(String key, Collection<TurtleValue> turtleValues);

    Collection<TurtleValue> difference(String key, String otherKey);

    Collection<TurtleValue> difference(String key, Collection<TurtleValue> turtleValues);

    Collection<TurtleValue> entries(String key);
}

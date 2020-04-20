package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.core.TurtleValue;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public interface SetOperations {
    public Boolean exist(String key);

    public Boolean exist(String key, TurtleValue value);

    public void add(String key,TurtleValue value);

    public Collection<TurtleValue> union (String key,String otherKey);

    public Collection<TurtleValue> union (String key,Collection<TurtleValue> turtleValues);

    public Collection<TurtleValue> intersect (String key,String otherKey);

    public Collection<TurtleValue> intersect (String key,Collection<TurtleValue> turtleValues);

    public Collection<TurtleValue> difference (String key,String otherKey);

    public Collection<TurtleValue> difference (String key,Collection<TurtleValue> turtleValues);

    public Collection<TurtleValue> entries(String key);
}

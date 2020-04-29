package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.core.TurtleValue;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public interface OrderSetOperations {

    Boolean exists(String key, TurtleValue value, Double score);

    Boolean exists(String key, TurtleValue value);

    Boolean exists(String key);

    Boolean add(String key, TurtleValue value, Double score);

    Integer add(String key, Collection<TurtleValue> values, Collection<Double> doubles);

    Integer count(String key, Double begin, Double end);

    Collection<TurtleValue> range(String key, Double begin, Double end);

    Boolean inRange(String key, Double begin, Double end);

    Boolean delete(String key, TurtleValue value, Double score);

    Integer delete(String key, Double begin, Double end);

    Boolean delete(String key, TurtleValue value);

    Integer delete(String key);


}

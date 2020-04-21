package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.core.TurtleValue;

import java.util.Collection;
import java.util.List;

/**
 * @author entropyfeng
 */
public interface OrderSetOperations {

    public Boolean exists(String key, TurtleValue value, Double score);

    public Boolean exists(String key, TurtleValue value);

    public Boolean exists(String key);

    public Boolean add(String key, TurtleValue value, Double score);

    public Long add(String key, Collection<TurtleValue> values, Collection<Double> doubles);

    public Integer count(String key, Double begin, Double end);

    public List<TurtleValue> range(String key, Double begin, Double end);

    public Boolean inRange(String key, Double begin, Double end);

    public Boolean delete(String key, TurtleValue value, Double score);

    public Integer delete(String key, Double begin, Double end);

    public Boolean delete(String key, TurtleValue value);

    public Integer delete(String key);

    public List<TurtleValue> union(String key, String otherKey);

    public List<TurtleValue> unionAndStore(String key, String otherKey);








}

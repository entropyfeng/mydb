package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.client.ops.ResponseOrderSetOperations;
import com.github.entropyfeng.mydb.common.ops.IOrderSetOperations;
import com.github.entropyfeng.mydb.common.ops.OrderSetOperations;
import com.github.entropyfeng.mydb.core.TurtleValue;

import java.util.Collection;
import java.util.List;

/**
 * @author entropyfeng
 */
public class DefaultOrderSetOperations implements OrderSetOperations {

    private IOrderSetOperations setOperations=new ResponseOrderSetOperations();

    @Override
    public Boolean exists(String key, TurtleValue value, Double score) {
        return null;
    }

    @Override
    public Boolean exists(String key, TurtleValue value) {
        return null;
    }

    @Override
    public Boolean exists(String key) {
        return null;
    }

    @Override
    public Boolean add(String key, TurtleValue value, Double score) {
        return null;
    }

    @Override
    public Long add(String key, Collection<TurtleValue> values, Collection<Double> doubles) {
        return null;
    }

    @Override
    public Integer count(String key, Double begin, Double end) {
        return null;
    }

    @Override
    public List<TurtleValue> range(String key, Double begin, Double end) {
        return null;
    }

    @Override
    public Boolean inRange(String key, Double begin, Double end) {
        return null;
    }

    @Override
    public Boolean delete(String key, TurtleValue value, Double score) {
        return null;
    }

    @Override
    public Integer delete(String key, Double begin, Double end) {
        return null;
    }

    @Override
    public Boolean delete(String key, TurtleValue value) {
        return null;
    }

    @Override
    public Integer delete(String key) {
        return null;
    }

    @Override
    public List<TurtleValue> union(String key, String otherKey) {
        return null;
    }

    @Override
    public List<TurtleValue> unionAndStore(String key, String otherKey) {
        return null;
    }
}

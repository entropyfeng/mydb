package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.client.ops.ResponseSetOperations;
import com.github.entropyfeng.mydb.common.ops.ISetOperations;
import com.github.entropyfeng.mydb.common.ops.SetOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoExceptionHelper;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;

import java.util.Collection;

/**
 * @// TODO: 2020/4/21  
 * @author entropyfeng
 */
public class DefaultSetOperations implements SetOperations {
    private ISetOperations setOperations = new ResponseSetOperations();


    @Override
    public Boolean exist(String key) {
        return null;
    }

    @Override
    public Boolean exist(String key, TurtleValue value) {
        return null;
    }

    @Override
    public void add(String key, TurtleValue value) {

    }

    @Override
    public Collection<TurtleValue> union(String key, String otherKey) {
        return null;
    }

    @Override
    public Collection<TurtleValue> union(String key, Collection<TurtleValue> turtleValues) {
        return null;
    }

    @Override
    public Collection<TurtleValue> intersect(String key, String otherKey) {
        return null;
    }

    @Override
    public Collection<TurtleValue> intersect(String key, Collection<TurtleValue> turtleValues) {
        return null;
    }

    @Override
    public Collection<TurtleValue> difference(String key, String otherKey) {
        return null;
    }

    @Override
    public Collection<TurtleValue> difference(String key, Collection<TurtleValue> turtleValues) {
        return null;
    }

    @Override
    public Collection<TurtleValue> entries(String key) {
        return null;
    }
}

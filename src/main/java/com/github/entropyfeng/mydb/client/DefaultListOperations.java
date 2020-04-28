package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.client.ops.ResponseListOperations;
import com.github.entropyfeng.mydb.common.ops.IListOperations;
import com.github.entropyfeng.mydb.common.ops.ListOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.common.protobuf.ProtoExceptionHelper;
import com.github.entropyfeng.mydb.common.protobuf.ProtoTurtleHelper;
import com.github.entropyfeng.mydb.core.TurtleValue;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public class DefaultListOperations implements ListOperations {

    private IListOperations listOperations = new ResponseListOperations();


    @Override
    public Integer size() {
        return null;
    }

    @Override
    public Integer sizeOf(String key) {
        return null;
    }

    @Override
    public void leftPush(String key, TurtleValue value) {

    }

    @Override
    public void leftPushAll(String key, Collection<TurtleValue> values) {

    }

    @Override
    public Boolean leftPushIfPresent(String key, TurtleValue value) {
        return null;
    }

    @Override
    public Boolean leftPushIfAbsent(String key, TurtleValue value) {
        return null;
    }

    @Override
    public void rightPush(String key, TurtleValue value) {

    }

    @Override
    public void rightPushAll(String key, Collection<TurtleValue> values) {

    }

    @Override
    public Boolean rightPushIfPresent(String key, TurtleValue value) {
        return null;
    }

    @Override
    public Boolean rightPushIfAbsent(String key, TurtleValue value) {
        return null;
    }

    @Override
    public TurtleValue leftPop(String key) {
        return null;
    }

    @Override
    public TurtleValue left(String key) {
        return null;
    }

    @Override
    public TurtleValue rightPop(String key) {
        return null;
    }

    @Override
    public TurtleValue right(String key) {
        return null;
    }

    @Override
    public void clear(String key) {

    }

    @Override
    public void clearAll() {

    }

    @Override
    public Boolean exist(String key) {
        return null;
    }

    @Override
    public Boolean exist(String key, TurtleValue value) {
        return null;
    }
}

package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.client.ops.ResponseListOperations;
import com.github.entropyfeng.mydb.common.ops.IListOperations;
import com.github.entropyfeng.mydb.common.ops.ListOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoExceptionHelper;
import com.github.entropyfeng.mydb.common.protobuf.ProtoTurtleHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public class DefaultListOperations implements ListOperations {

    private IListOperations listOperations = new ResponseListOperations();

    @Override
    public Integer size() {

        TurtleProtoBuf.ResponseData res = listOperations.size();
        if (res.getSuccess()) {
            return res.getIntValue();
        } else {
            ProtoExceptionHelper.handler(res.getExceptionType(), res.getException());
            return null;
        }
    }

    @Override
    public Integer sizeOf(String key) {
        TurtleProtoBuf.ResponseData res = listOperations.sizeOf(key);
        if (res.getSuccess()) {
            return res.getIntValue();
        } else {
            ProtoExceptionHelper.handler(res.getExceptionType(), res.getException());
            return null;
        }
    }

    @Override
    public void leftPush(String key, TurtleValue value) {
        TurtleProtoBuf.ResponseData res = listOperations.leftPush(key, value);

        if (!res.getSuccess()){
            ProtoExceptionHelper.handler(res.getExceptionType(),res.getException());
        }
    }
    @Override
    public void leftPushAll(String key, Collection<TurtleValue> values) {
        TurtleProtoBuf.ResponseData res = listOperations.leftPushAll(key,values);
        if (!res.getSuccess()){
            ProtoExceptionHelper.handler(res.getExceptionType(),res.getException());
        }
    }

    @Override
    public Boolean leftPushIfPresent(String key, TurtleValue value) {
        TurtleProtoBuf.ResponseData res = listOperations.leftPushIfPresent(key, value);
        if (res.getSuccess()){
            return res.getBoolValue();
        }else {
            ProtoExceptionHelper.handler(res.getExceptionType(),res.getException());
            return null;
        }
    }

    @Override
    public Boolean leftPushIfAbsent(String key, TurtleValue value) {
        TurtleProtoBuf.ResponseData res = listOperations.leftPushIfAbsent(key, value);
        if (res.getSuccess()){
            return res.getBoolValue();
        }else {
            ProtoExceptionHelper.handler(res.getExceptionType(),res.getException());
            return null;
        }
    }

    @Override
    public void rightPush(String key, TurtleValue value) {
        TurtleProtoBuf.ResponseData res = listOperations.rightPush(key, value);

        if (!res.getSuccess()){
            ProtoExceptionHelper.handler(res.getExceptionType(),res.getException());
        }
    }

    @Override
    public void rightPushAll(String key, Collection<TurtleValue> values) {
        TurtleProtoBuf.ResponseData res = listOperations.rightPushAll(key,values);
        if (!res.getSuccess()){
            ProtoExceptionHelper.handler(res.getExceptionType(),res.getException());
        }
    }

    @Override
    public Boolean rightPushIfPresent(String key, TurtleValue value) {
        TurtleProtoBuf.ResponseData res = listOperations.rightPushIfPresent(key, value);
        if (res.getSuccess()){
            return res.getBoolValue();
        }else {
            ProtoExceptionHelper.handler(res.getExceptionType(),res.getException());
            return null;
        }
    }

    @Override
    public Boolean rightPushIfAbsent(String key, TurtleValue value) {
        TurtleProtoBuf.ResponseData res = listOperations.rightPushIfAbsent(key, value);
        if (res.getSuccess()){
            return res.getBoolValue();
        }else {
            ProtoExceptionHelper.handler(res.getExceptionType(),res.getException());
            return null;
        }
    }

    @Override
    public TurtleValue leftPop(String key) {

        TurtleProtoBuf.ResponseData res = listOperations.leftPop(key);
        if (res.getSuccess()){
            return ProtoTurtleHelper.convertToTurtleValue(res.getTurtleValue());
        }else {
            ProtoExceptionHelper.handler(res.getExceptionType(),res.getException());
            return null;
        }
    }

    @Override
    public TurtleValue left(String key) {
        TurtleProtoBuf.ResponseData res = listOperations.left(key);
        if (res.getSuccess()){
            return ProtoTurtleHelper.convertToTurtleValue(res.getTurtleValue());
        }else {
            ProtoExceptionHelper.handler(res.getExceptionType(),res.getException());
            return null;
        }
    }

    @Override
    public TurtleValue rightPop(String key) {
        TurtleProtoBuf.ResponseData res = listOperations.rightPop(key);
        if (res.getSuccess()){
            return ProtoTurtleHelper.convertToTurtleValue(res.getTurtleValue());
        }else {
            ProtoExceptionHelper.handler(res.getExceptionType(),res.getException());
            return null;
        }
    }

    @Override
    public TurtleValue right(String key) {
        TurtleProtoBuf.ResponseData res = listOperations.right(key);
        if (res.getSuccess()){
            return ProtoTurtleHelper.convertToTurtleValue(res.getTurtleValue());
        }else {
            ProtoExceptionHelper.handler(res.getExceptionType(),res.getException());
            return null;
        }
    }

    @Override
    public void clear(String key) {
        TurtleProtoBuf.ResponseData res = listOperations.clear(key);
        if (!res.getSuccess()){
            ProtoExceptionHelper.handler(res.getExceptionType(),res.getException());
        }
    }

    @Override
    public void clearAll() {
        TurtleProtoBuf.ResponseData res = listOperations.clearAll();
        if (!res.getSuccess()){
            ProtoExceptionHelper.handler(res.getExceptionType(),res.getException());
        }
    }

    @Override
    public Boolean exist(String key) {
        TurtleProtoBuf.ResponseData res = listOperations.exist(key);
        if (res.getSuccess()){
            return res.getBoolValue();
        }else {
            ProtoExceptionHelper.handler(res.getExceptionType(),res.getException());
            return null;
        }
    }

    @Override
    public Boolean exist(String key, TurtleValue value) {
        TurtleProtoBuf.ResponseData res = listOperations.exist(key, value);
        if (res.getSuccess()){
            return res.getBoolValue();
        }else {
            ProtoExceptionHelper.handler(res.getExceptionType(),res.getException());
            return null;
        }
    }
}

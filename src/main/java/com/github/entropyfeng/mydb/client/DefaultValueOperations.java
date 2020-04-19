package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.client.ops.ResponseValueOperations;
import com.github.entropyfeng.mydb.common.exception.ElementOutOfBoundException;
import com.github.entropyfeng.mydb.common.ops.IValueOperations;
import com.github.entropyfeng.mydb.common.ops.ValueOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoExceptionHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * @author entropyfeng
 */
public class DefaultValueOperations implements ValueOperations {
    private IValueOperations valueOperations=new ResponseValueOperations();

    @Override
    public void set(String key, TurtleValue value, Long time) throws ElementOutOfBoundException {
        TurtleProtoBuf.ResponseData res=valueOperations.set(key, value, time);
        if (!res.getSuccess()){
            ProtoExceptionHelper.handler(res.getExceptionType(),res.getException());
        }
    }

    @Override
    public Boolean setIfAbsent(String key, TurtleValue value, Long time) {
        return null;
    }

    @Override
    public Boolean setIfPresent(String key, TurtleValue value, Long time) {
        return null;
    }

    @Override
    public TurtleValue get(String key) {
        return null;
    }

    @Override
    public TurtleValue increment(String key, Integer intValue) throws UnsupportedOperationException, NoSuchElementException {
        return null;
    }

    @Override
    public TurtleValue increment(String key, Long longValue) throws UnsupportedOperationException, NoSuchElementException {
        return null;
    }

    @Override
    public TurtleValue increment(String key, Double doubleValue) throws UnsupportedOperationException, NoSuchElementException {
        return null;
    }

    @Override
    public TurtleValue increment(String key, BigInteger bigInteger) throws UnsupportedOperationException, NoSuchElementException {
        return null;
    }

    @Override
    public TurtleValue increment(String key, BigDecimal bigDecimal) throws UnsupportedOperationException, NoSuchElementException {
        return null;
    }

    @Override
    public Void append(String key, String appendValue) throws UnsupportedOperationException, NoSuchElementException {
        return null;
    }

    @Override
    public Collection<TurtleValue> allValues() {
        return null;
    }
}

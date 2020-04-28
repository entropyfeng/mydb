package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.client.ops.ResponseValueOperations;
import com.github.entropyfeng.mydb.common.exception.ElementOutOfBoundException;
import com.github.entropyfeng.mydb.common.ops.IValueOperations;
import com.github.entropyfeng.mydb.common.ops.ValueOperations;
import com.github.entropyfeng.mydb.core.TurtleValue;
import com.github.entropyfeng.mydb.core.helper.Pair;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * @author entropyfeng
 */
public class DefaultValueOperations implements ValueOperations {
    private IValueOperations valueOperations = new ResponseValueOperations();


    @Override
    public void set(String key, TurtleValue value, Long time) throws ElementOutOfBoundException {

    }

    @Override
    public Boolean setIfAbsent(String key, TurtleValue value, Long time) throws ElementOutOfBoundException {
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
    public void append(String key, String appendValue) throws UnsupportedOperationException, NoSuchElementException {

    }

    @Override
    public Collection<TurtleValue> allValues() {
        return null;
    }

    @Override
    public Collection<String> allKeys() {
        return null;
    }

    @Override
    public Collection<Pair<String, TurtleValue>> allEntries() {
        return null;
    }
}

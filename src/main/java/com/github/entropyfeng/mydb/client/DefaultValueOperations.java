package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.ops.ValueOperations;
import com.github.entropyfeng.mydb.core.obj.TurtleValue;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author entropyfeng
 */
public class DefaultValueOperations implements ValueOperations {


    @Override
    public void set(String key, TurtleValue value, long time) {

    }

    @Override
    public boolean setIfAbsent(String key, TurtleValue value, long time) {
        return false;
    }

    @Override
    public boolean setIfPresent(String key, TurtleValue value, long time) {
        return false;
    }

    @Override
    public TurtleValue get(String key) {
        return null;
    }

    @Override
    public Object increment(String key, int intValue) throws UnsupportedOperationException {
        return null;
    }

    @Override
    public Object increment(String key, long longValue) throws UnsupportedOperationException {
        return null;
    }

    @Override
    public Object increment(String key, double doubleValue) throws UnsupportedOperationException {
        return null;
    }

    @Override
    public Object increment(String key, BigInteger bigInteger) throws UnsupportedOperationException {
        return null;
    }

    @Override
    public Object increment(String key, BigDecimal bigDecimal) throws UnsupportedOperationException {
        return null;
    }

    @Override
    public boolean append(String key, String appendValue) throws UnsupportedOperationException {

        return false;
    }


}

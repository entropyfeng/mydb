package com.github.entropyfeng.mydb.client;

/**
 * @author entropyfeng
 */
public class DefaultValueOperations implements ValueOperations {


    @Override
    public void set(String key, TurtleValue value, Long time) {

    }

    @Override
    public boolean setIfAbsent(String key, TurtleValue value, Long time) {
        return false;
    }

    @Override
    public boolean setIfPresent(String key, TurtleValue value, Long time) {
        return false;
    }

    @Override
    public TurtleValue get(String key) {
        return null;
    }

    @Override
    public Object increment(String key) throws UnsupportedOperationException {
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
    public boolean append(String key, String appendValue) {
        return false;
    }
}

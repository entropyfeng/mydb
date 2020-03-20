package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.core.obj.TurtleObject;

/**
 * @author entropyfeng
 */
public class DefaultValueOperations implements ValueOperations {

    @Override
    public void set(String key, TurtleObject value, long time) {

    }

    @Override
    public boolean setIfAbsent(String key, TurtleObject value, long time) {
        return false;
    }

    @Override
    public boolean setIfPresent(String key, TurtleObject value, long time) {
        return false;
    }

    @Override
    public TurtleObject get(String key) {
        return null;
    }

    @Override
    public Object increment(String key) {
        return null;
    }

    @Override
    public Object increment(String key, long longValue) {
        return null;
    }

    @Override
    public Object increment(String key, double doubleValue) {
        return null;
    }

    @Override
    public boolean append(String key, String appendValue) {
        return false;
    }
}

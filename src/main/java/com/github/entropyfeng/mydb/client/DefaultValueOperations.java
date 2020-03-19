package com.github.entropyfeng.mydb.client;

public class DefaultValueOperations implements ValueOperations {
    @Override
    public void set(String String, Object value, long time) {
        
    }

    @Override
    public boolean setIfAbsent(String key, Object value, long time) {
        return false;
    }

    @Override
    public boolean setIfPresent(String key, Object value, long time) {
        return false;
    }

    @Override
    public Object get(String key) {
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

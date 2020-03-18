package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.core.obj.ValueObject;

import java.util.Collection;

public class TurtleTemplate implements TurtleOperations {

    private ValueOperations valueOperations;

    public ValueOperations opsForValues(){
        return this.valueOperations;
    }

    @Override
    public boolean haskey(String key) {
        return false;
    }

    @Override
    public boolean delete(String key) {
        return false;
    }

    @Override
    public long delete(Collection<String> keys) {
        return 0;
    }

    @Override
    public boolean expireAt(String key, long times) {
        return false;
    }

    @Override
    public long getExpire(String key) {
        return 0;
    }
}

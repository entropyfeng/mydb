package com.github.entropyfeng.mydb.client;

import java.util.Collection;
import java.util.Set;

public interface TurtleOperations {

    public boolean haskey(String key);

    public boolean delete(String key);

    abstract public long delete(Collection<String> keys);

    public boolean expireAt(String key,long times);

    public long getExpire(String key);

}

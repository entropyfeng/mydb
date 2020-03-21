package com.github.entropyfeng.mydb.client;

import java.util.Collection;
import java.util.Set;

/**
 * 通用操作
 */
public interface TurtleOperations {

    public boolean hasKey(String key);

    public boolean delete(String key);

    abstract public long delete(Collection<String> keys);

    public boolean expireAt(String key,long times);

    public long getExpire(String key);

}

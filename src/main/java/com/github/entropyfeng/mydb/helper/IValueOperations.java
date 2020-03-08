package com.github.entropyfeng.mydb.helper;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author entropyfeng
 * @date 2020/3/5 19:57
 */
public interface IValueOperations {

    /**
     * 新建一个新的Value
     * @param key
     * @param value
     */
    public void newValue(String key,String value);

    public void newValue(String key, String value, Duration timeout);

    public void newValue(String key, String value, long time, TimeUnit timeUnit);

    public boolean deleteValue(String string);

    public boolean clearExpireTime(String string);

    public String getValue(String key);

    public void setValue(String key,String value);

    public void appendValue(String key,String extra);








}

package com.github.entropyfeng.mydb.helper;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author entropyfeng
 * @date 2020/3/5 19:57
 */
public interface ValueOperations {

    public void setStringIfAbsent(String key,String value);
    public void setStringIfAbsent(String key, String value, Duration timeout);
    public void setStringIfAbsent(String key, String value, long time, TimeUnit timeUnit);


    public void appendIfPresent(String key,String extra);

    public String getString(String key);



}

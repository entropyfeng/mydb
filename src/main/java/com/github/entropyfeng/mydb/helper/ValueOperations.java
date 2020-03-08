package com.github.entropyfeng.mydb.helper;

import com.sun.beans.decoder.ValueObject;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * @author entropyfeng
 * @date 2020/3/8 16:41
 */
public class ValueOperations implements IValueOperations {
   public ValueOperations(ValuesObject valuesObject){
        this.valuesObject=valuesObject;
    }

    private ValuesObject valuesObject;

    @Override
    public void newValue(String key, String value) {
        valuesObject.stringMap.put(key,value);
    }

    @Override
    public void newValue(String key, String value, Duration timeout) {
        valuesObject.stringMap.put(key, value);
        valuesObject.expireMap.put(key,System.currentTimeMillis()+timeout.toMillis());
    }

    @Override
    public void newValue(String key, String value, long time, TimeUnit timeUnit) {

        valuesObject.stringMap.put(key,value);
       
        valuesObject.expireMap.put(key,)
    }

    @Override
    public boolean deleteValue(String string) {
        return false;
    }

    @Override
    public boolean clearExpireTime(String string) {
        return false;
    }

    @Override
    public String getValue(String key) {
        return null;
    }

    @Override
    public void setValue(String key, String value) {

    }

    @Override
    public void appendValue(String key, String extra) {

    }

    public static void main(String[] args) {
        Duration duration=Duration.ofMinutes(1);
        System.out.println(duration.toMillis());
    }
}

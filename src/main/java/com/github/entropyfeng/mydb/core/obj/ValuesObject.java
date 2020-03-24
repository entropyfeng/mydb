package com.github.entropyfeng.mydb.core.obj;

import com.github.entropyfeng.mydb.server.ExpireHandler;
import com.github.entropyfeng.mydb.util.TimeUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

public class ValuesObject extends ExpireHandler  {
    private HashMap<String, TurtleValue> valueMap;

    public ValuesObject(HashMap<String, TurtleValue> valueMap) {
        super();
        this.valueMap = valueMap;
    }

    public TurtleValue get(String key) {
        removeExpireKey(key);
        return valueMap.get(key);
    }

    public boolean remove(String key) {
        deleteExpire(key);
        return valueMap.remove(key) != null;
    }

    public boolean isExist(String key) {
        removeExpireKey(key);
        return valueMap.containsKey(key);
    }

    public void set(String key, TurtleValue value, long time) {
        valueMap.put(key, value);
        if (!TimeUtil.isExpire(time)) {
            putExpire(key, time);
        }
    }

    public void setIfPresent(String key, TurtleValue value, long time) {
        removeExpireKey(key);
        if (valueMap.containsKey(key)) {
            set(key, value, time);
        }
    }

    public void setIfAbsent(String key, TurtleValue value, long time) {
        removeExpireKey(key);
        if (!valueMap.containsKey(key)) {
            set(key, value, time);
        }
    }

    private boolean removeExpireKey(String key) {
        if (isExpire(key)) {
            deleteExpire(key);
            valueMap.remove(key);

            return true;
        }
        return false;
    }

    public void append(String key, String value) throws UnsupportedOperationException {
        removeExpireKey(key);
        TurtleValue turtleValue = valueMap.get(key);
        if (turtleValue != null) {
            turtleValue.append(value);
        }
    }

    public boolean increment(String key, long longValue) throws UnsupportedOperationException {
        removeExpireKey(key);
        TurtleValue turtleValue = valueMap.get(key);
        if (turtleValue != null) {
            turtleValue.increment(longValue);
            return true;
        }
        return false;
    }

    public boolean increment(String key, double doubleValue) {
        return false;
    }

    public static void main(String[] args) throws NoSuchMethodException {
        ValuesObject valuesObject = new ValuesObject(new HashMap<>());
        Method method = valuesObject.getClass().getDeclaredMethod("increment", String.class, long.class);
        try {
            System.out.println(method.invoke(valuesObject, "d", 100));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }
}

package com.github.entropyfeng.mydb.core.obj;

import com.github.entropyfeng.mydb.common.TurtleValueType;
import com.github.entropyfeng.mydb.common.ops.ValueOperations;
import com.github.entropyfeng.mydb.util.TimeUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * @author entropyfeng
 */
public class ValuesObject extends BaseObject implements ValueOperations {
    private HashMap<String, TurtleValue> valueMap;

    public ValuesObject() {
        super();
        valueMap = new HashMap<>();
    }


    @Override
    public void set(String key, TurtleValue value, long time) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        //如果过期
        if (isExpire(key)) {
            //删除过期字典条目
            deleteExpireTime(key);
        }
        valueMap.put(key, value);
        if (!TimeUtil.isExpire(time)) {
            putExpireTime(key, time);
        }
    }

    @Override
    public Boolean setIfAbsent(String key, TurtleValue value, long time) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        handleExpire(key);
        if (!valueMap.containsKey(key)) {
            valueMap.put(key, value);
            if (!TimeUtil.isExpire(time)) {
                putExpireTime(key, time);
            }
            return true;
        } else {
            return false;
        }

    }

    @Override
    public Boolean setIfPresent(String key, TurtleValue value, long time) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        if (isExpire(key)) {
            deleteExpireTime(key);
            valueMap.remove(key);
        }
        if (valueMap.containsKey(key)) {
            valueMap.put(key, value);
            if (!TimeUtil.isExpire(time)) {
                putExpireTime(key, time);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public TurtleValue get(String key) {
        handleExpire(key);
        return valueMap.get(key);
    }

    @Override
    public TurtleValue increment(String key, int intValue) throws UnsupportedOperationException, NoSuchElementException {
        handleExpire(key);
        TurtleValue turtleValue = valueMap.get(key);
        if (turtleValue == null) {
            throw new NoSuchElementException();
        }
        turtleValue.increment(intValue);
        return turtleValue;
    }

    @Override
    public TurtleValue increment(String key, long longValue) throws UnsupportedOperationException, NoSuchElementException {
        handleExpire(key);
        TurtleValue turtleValue = valueMap.get(key);
        if (turtleValue == null) {
            throw new NoSuchElementException();
        }
        turtleValue.increment(longValue);
        return turtleValue;
    }

    @Override
    public TurtleValue increment(String key, double doubleValue) throws UnsupportedOperationException, NoSuchElementException {
        handleExpire(key);
        TurtleValue turtleValue = valueMap.get(key);
        if (turtleValue == null) {
            throw new NoSuchElementException();
        }
        turtleValue.increment(doubleValue);
        return turtleValue;
    }

    @Override
    public TurtleValue increment(String key, BigInteger bigInteger) throws UnsupportedOperationException, NoSuchElementException {
        handleExpire(key);
        TurtleValue turtleValue = valueMap.get(key);
        if (turtleValue == null) {
            throw new NoSuchElementException();
        }
        turtleValue.increment(bigInteger);
        return turtleValue;
    }

    @Override
    public TurtleValue increment(String key, BigDecimal bigDecimal) throws UnsupportedOperationException, NoSuchElementException {
        handleExpire(key);
        TurtleValue turtleValue = valueMap.get(key);
        if (turtleValue == null) {
            throw new NoSuchElementException();
        }
        turtleValue.increment(bigDecimal);
        return turtleValue;
    }

    @Override
    public void append(String key, String appendValue) throws UnsupportedOperationException, NoSuchElementException {
        TurtleValue turtleValue = valueMap.get(key);
        if (turtleValue == null) {
            throw new NoSuchElementException();
        }
        if (turtleValue.getType() != TurtleValueType.STRING) {
            throw new UnsupportedOperationException("turtleValue require String but finds " + turtleValue.getType());
        }
        turtleValue.append(appendValue);
    }

    private void handleExpire(String key) {
        if (isExpire(key)) {
            deleteExpireTime(key);
            valueMap.remove(key);
        }
    }
}

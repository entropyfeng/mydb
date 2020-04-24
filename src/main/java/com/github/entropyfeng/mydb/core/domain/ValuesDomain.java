package com.github.entropyfeng.mydb.core.domain;

import com.github.entropyfeng.mydb.common.TurtleValueType;
import com.github.entropyfeng.mydb.common.exception.TurtleDesignError;
import com.github.entropyfeng.mydb.common.ops.IValueOperations;
import com.github.entropyfeng.mydb.common.protobuf.CollectionResHelper;
import com.github.entropyfeng.mydb.common.protobuf.SingleResHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;
import com.github.entropyfeng.mydb.core.obj.BaseObject;
import com.github.entropyfeng.mydb.util.TimeUtil;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.HashMap;
import java.util.NoSuchElementException;

/**
 * @author entropyfeng
 */
public class ValuesDomain extends BaseObject implements IValueOperations {

    private final HashMap<String, TurtleValue> valueMap;

    public ValuesDomain() {
        super();
        this.valueMap = new HashMap<>();
    }


    @Override
    public @NotNull TurtleProtoBuf.ResponseData set(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time) {
        //如果过期
        if (isExpire(key)) {
            //删除过期字典条目
            deleteExpireTime(key);
        }
        if (valueMap.size() >= Integer.MAX_VALUE) {
            return SingleResHelper.elementOutOfBoundException("size of map out of limit .!");
        }

        valueMap.put(key, value);
        if (!TimeUtil.isExpire(time)) {
            putExpireTime(key, time);
        }
        return SingleResHelper.voidResponse();
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData setIfAbsent(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time) {
        boolean res = false;
        handleExpire(key);
        if (!valueMap.containsKey(key)) {
            valueMap.put(key, value);
            if (!TimeUtil.isExpire(time)) {
                putExpireTime(key, time);
            }
            res = true;
        }


        return SingleResHelper.boolResponse(res);
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData setIfPresent(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time) {
        boolean res = false;
        if (isExpire(key)) {
            deleteExpireTime(key);
            valueMap.remove(key);
        }
        if (valueMap.containsKey(key)) {
            valueMap.put(key, value);
            if (!TimeUtil.isExpire(time)) {
                putExpireTime(key, time);
            }
            res = true;
        }
        return SingleResHelper.boolResponse(res);
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData get(@NotNull String key) {
        handleExpire(key);

        TurtleValue turtleValue = valueMap.get(key);
        if (turtleValue == null) {
            return SingleResHelper.nullResponse();
        } else {
            return SingleResHelper.turtleValueResponse(turtleValue);
        }
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData increment(@NotNull String key, @NotNull Integer intValue) throws UnsupportedOperationException, NoSuchElementException {


        return modifyHelper(key, TurtleValueType.INTEGER, intValue);

    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData increment(@NotNull String key, @NotNull Long longValue) throws UnsupportedOperationException, NoSuchElementException {

        return modifyHelper(key, TurtleValueType.LONG, longValue);
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData increment(@NotNull String key, @NotNull Double doubleValue) throws UnsupportedOperationException, NoSuchElementException {

        return modifyHelper(key, TurtleValueType.DOUBLE, doubleValue);
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData increment(@NotNull String key, @NotNull BigInteger bigInteger) throws UnsupportedOperationException, NoSuchElementException {
        return modifyHelper(key, TurtleValueType.NUMBER_INTEGER, bigInteger);
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData increment(@NotNull String key, @NotNull BigDecimal bigDecimal) throws UnsupportedOperationException, NoSuchElementException {
        return modifyHelper(key, TurtleValueType.NUMBER_DECIMAL, bigDecimal);
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData append(@NotNull String key, @NotNull String appendValue) throws UnsupportedOperationException, NoSuchElementException {
        return modifyHelper(key, TurtleValueType.STRING, appendValue);
    }


    @Override
    @NotNull
    public Collection<TurtleProtoBuf.ResponseData> allValues() {
        return CollectionResHelper.turtleResponse(valueMap.values());
    }

    @Override
    public @NotNull Collection<TurtleProtoBuf.ResponseData> allEntries() {
        return CollectionResHelper.stringTurtleResponse(valueMap.entrySet());
    }

    @Override
    public @NotNull Collection<TurtleProtoBuf.ResponseData> dump() {
        return CollectionResHelper.stringResponse(valueMap.keySet());
    }

    /**
     * 移除过期键
     *
     * @param key key
     */
    private void handleExpire(String key) {
        if (isExpire(key)) {
            deleteExpireTime(key);
            valueMap.remove(key);
        }
    }

    private @NotNull TurtleProtoBuf.ResponseData modifyHelper(String key, TurtleValueType type, Object value) {
        handleExpire(key);
        TurtleValue turtleValue = valueMap.get(key);
        if (turtleValue == null) {
            return SingleResHelper.noSuchElementException();
        }
        try {
            switch (type) {
                case STRING:
                    turtleValue.append((String) value);
                    break;
                case LONG:
                    turtleValue.increment((Long) value);
                    break;
                case DOUBLE:
                    turtleValue.increment((Double) value);
                    break;
                case INTEGER:
                    turtleValue.increment((Integer) value);
                    break;
                case NUMBER_INTEGER:
                    turtleValue.increment((BigInteger) value);
                    break;
                case NUMBER_DECIMAL:
                    turtleValue.increment((BigDecimal) value);
                    break;
                default:
                    throw new TurtleDesignError("unSupport enum type: " + type);
            }
        } catch (UnsupportedOperationException e) {
            return SingleResHelper.unSupportOperationException();
        } catch (TurtleDesignError e) {
            return SingleResHelper.turtleDesignException(e.getMessage());
        }
        return SingleResHelper.turtleValueResponse(turtleValue);
    }

}

package com.github.entropyfeng.mydb.core.obj;

import com.github.entropyfeng.mydb.common.TurtleValueType;
import com.github.entropyfeng.mydb.common.exception.TurtleFatalError;
import com.github.entropyfeng.mydb.common.ops.IValueOperations;
import com.github.entropyfeng.mydb.common.protobuf.ResponseDataHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
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


    public ValuesDomain(HashMap<String, TurtleValue> valueMap) {
        super();
        this.valueMap = valueMap;
    }


    @Override
    public TurtleProtoBuf.ResponseData set(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time) {
        //如果过期
        if (isExpire(key)) {
            //删除过期字典条目
            deleteExpireTime(key);
        }
        valueMap.put(key, value);
        if (!TimeUtil.isExpire(time)) {
            putExpireTime(key, time);
        }
        return ResponseDataHelper.voidResponse();
    }

    @Override
    public TurtleProtoBuf.ResponseData setIfAbsent(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time) {
        boolean res = false;
        handleExpire(key);
        if (!valueMap.containsKey(key)) {
            valueMap.put(key, value);
            if (!TimeUtil.isExpire(time)) {
                putExpireTime(key, time);
            }
            res = true;
        }

        return ResponseDataHelper.boolResponse(res);
    }

    @Override
    public TurtleProtoBuf.ResponseData setIfPresent(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time) {
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
        return ResponseDataHelper.boolResponse(res);
    }

    @Override
    public TurtleProtoBuf.ResponseData get(@NotNull String key) {
        handleExpire(key);

        TurtleValue turtleValue = valueMap.get(key);
        if (turtleValue == null) {
            return ResponseDataHelper.nullResponse();
        } else {
            return ResponseDataHelper.turtleValueResponse(turtleValue);
        }
    }

    @Override
    public TurtleProtoBuf.ResponseData increment(@NotNull String key, @NotNull Integer intValue) throws UnsupportedOperationException, NoSuchElementException {
        handleExpire(key);
        TurtleValue turtleValue = valueMap.get(key);
        if (turtleValue == null) {
            return ResponseDataHelper.noSuchElementException();
        }
        try {
            turtleValue.increment(intValue);
        } catch (UnsupportedOperationException e) {
            return ResponseDataHelper.unSupportOperationException();
        }
        return ResponseDataHelper.turtleValueResponse(turtleValue);


    }

    @Override
    public TurtleProtoBuf.ResponseData increment(@NotNull String key, @NotNull Long longValue) throws UnsupportedOperationException, NoSuchElementException {
        handleExpire(key);
        TurtleValue turtleValue = valueMap.get(key);
        if (turtleValue == null) {
            return ResponseDataHelper.noSuchElementException();
        }
        try {
            turtleValue.increment(longValue);
        } catch (UnsupportedOperationException e) {
            return ResponseDataHelper.unSupportOperationException();
        }
        return ResponseDataHelper.turtleValueResponse(turtleValue);
    }

    @Override
    public TurtleProtoBuf.ResponseData increment(@NotNull String key, @NotNull Double doubleValue) throws UnsupportedOperationException, NoSuchElementException {
        handleExpire(key);
        TurtleValue turtleValue = valueMap.get(key);
        if (turtleValue == null) {
            return ResponseDataHelper.noSuchElementException();
        }
        try {
            turtleValue.increment(doubleValue);
        } catch (UnsupportedOperationException e) {
            return ResponseDataHelper.unSupportOperationException();
        }
        return ResponseDataHelper.turtleValueResponse(turtleValue);
    }

    @Override
    public TurtleProtoBuf.ResponseData increment(@NotNull String key, @NotNull BigInteger bigInteger) throws UnsupportedOperationException, NoSuchElementException {
        return null;
    }

    @Override
    public TurtleProtoBuf.ResponseData increment(@NotNull String key, @NotNull BigDecimal bigDecimal) throws UnsupportedOperationException, NoSuchElementException {
        return null;
    }

    @Override
    public TurtleProtoBuf.ResponseData append(@NotNull String key, @NotNull String appendValue) throws UnsupportedOperationException, NoSuchElementException {
        return null;
    }

    @Override
    public Collection<TurtleProtoBuf.ResponseData> allValues() {
        return null;
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

    private TurtleProtoBuf.ResponseData incrementHelper(String key, TurtleValueType type, Object value){
        handleExpire(key);
        TurtleValue turtleValue = valueMap.get(key);
        if (turtleValue == null) {
            return ResponseDataHelper.noSuchElementException();
        }
        try {
           switch (type){
               case STRING:turtleValue.append((String)value);break;
               case LONG:turtleValue.increment((Long)value);break;
               case DOUBLE:turtleValue.increment((Double)value);break;
               case INTEGER:turtleValue.increment((Integer)value);break;
               case NUMBER_INTEGER:turtleValue.increment((BigInteger)value);break;
               case NUMBER_DECIMAL:turtleValue.increment((BigDecimal)value);break;
               default:throw new TurtleFatalError("unSupport enum type: "+type);
           }
        } catch (UnsupportedOperationException e) {
            return ResponseDataHelper.unSupportOperationException();
        }catch (TurtleFatalError e){
            return ResponseDataHelper.fatalException(e.getMessage());
        }
        return ResponseDataHelper.turtleValueResponse(turtleValue);
    }

}

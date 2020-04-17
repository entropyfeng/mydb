package com.github.entropyfeng.mydb.core.obj;

import com.github.entropyfeng.mydb.common.TurtleValueType;
import com.github.entropyfeng.mydb.common.exception.TurtleFatalError;
import com.github.entropyfeng.mydb.common.ops.IValueOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoTurtleHelper;
import com.github.entropyfeng.mydb.common.protobuf.SingleResponseDataHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.util.TimeUtil;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
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
        return SingleResponseDataHelper.voidResponse();
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

        return SingleResponseDataHelper.boolResponse(res);
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
        return SingleResponseDataHelper.boolResponse(res);
    }

    @Override
    public TurtleProtoBuf.ResponseData get(@NotNull String key) {
        handleExpire(key);

        TurtleValue turtleValue = valueMap.get(key);
        if (turtleValue == null) {
            return SingleResponseDataHelper.nullResponse();
        } else {
            return SingleResponseDataHelper.turtleValueResponse(turtleValue);
        }
    }

    @Override
    public TurtleProtoBuf.ResponseData increment(@NotNull String key, @NotNull Integer intValue) throws UnsupportedOperationException, NoSuchElementException {


        return modifyHelper(key,TurtleValueType.INTEGER,intValue);

    }

    @Override
    public TurtleProtoBuf.ResponseData increment(@NotNull String key, @NotNull Long longValue) throws UnsupportedOperationException, NoSuchElementException {

        return modifyHelper(key,TurtleValueType.LONG,longValue);
    }

    @Override
    public TurtleProtoBuf.ResponseData increment(@NotNull String key, @NotNull Double doubleValue) throws UnsupportedOperationException, NoSuchElementException {

        return modifyHelper(key,TurtleValueType.DOUBLE,doubleValue);
    }

    @Override
    public TurtleProtoBuf.ResponseData increment(@NotNull String key, @NotNull BigInteger bigInteger) throws UnsupportedOperationException, NoSuchElementException {
        return modifyHelper(key,TurtleValueType.NUMBER_INTEGER,bigInteger);
    }

    @Override
    public TurtleProtoBuf.ResponseData increment(@NotNull String key, @NotNull BigDecimal bigDecimal) throws UnsupportedOperationException, NoSuchElementException {
        return modifyHelper(key,TurtleValueType.NUMBER_DECIMAL,bigDecimal);
    }

    @Override
    public TurtleProtoBuf.ResponseData append(@NotNull String key, @NotNull String appendValue) throws UnsupportedOperationException, NoSuchElementException {
        return modifyHelper(key,TurtleValueType.STRING,appendValue);
    }

    /**
     *
     * @return null
     */
    @Override
    public Collection<TurtleProtoBuf.ResponseData> allValues() {

        final int mapSize=valueMap.size();
        ArrayList<TurtleProtoBuf.ResponseData> resList=new ArrayList<>(mapSize);
        //第一个responseData
        TurtleProtoBuf.ResponseData.Builder builder= TurtleProtoBuf.ResponseData.newBuilder();
        builder.setCollectionSize(mapSize);
        builder.setSuccess(true);
        builder.setCollectionAble(true);
        builder.setEndAble(false);
        resList.add(builder.build());


        TurtleProtoBuf.StringTurtleValueEntry.Builder entryBuilder= TurtleProtoBuf.StringTurtleValueEntry.newBuilder();

        valueMap.forEach((key, value) -> {
            builder.clear();
            entryBuilder.clear();

            entryBuilder.setKey(key);
            entryBuilder.setTurtleValue(ProtoTurtleHelper.convertToProtoTurtleValue(value));

            builder.setStringTurtleValueEntry(entryBuilder.build());
            builder.setEndAble(false);
            resList.add(builder.build());
        });


        return resList;
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

    private TurtleProtoBuf.ResponseData modifyHelper(String key, TurtleValueType type, Object value){
        handleExpire(key);
        TurtleValue turtleValue = valueMap.get(key);
        if (turtleValue == null) {
            return SingleResponseDataHelper.noSuchElementException();
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
            return SingleResponseDataHelper.unSupportOperationException();
        }catch (TurtleFatalError e){
            return SingleResponseDataHelper.fatalException(e.getMessage());
        }
        return SingleResponseDataHelper.turtleValueResponse(turtleValue);
    }

}

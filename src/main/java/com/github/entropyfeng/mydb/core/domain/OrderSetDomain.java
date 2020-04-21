package com.github.entropyfeng.mydb.core.domain;

import com.github.entropyfeng.mydb.common.ops.IOrderSetOperations;
import com.github.entropyfeng.mydb.common.protobuf.SingleResHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;
import com.github.entropyfeng.mydb.core.zset.OrderSet;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author entropyfeng
 */
public class OrderSetDomain implements IOrderSetOperations {

    private final HashMap<String, OrderSet<TurtleValue>> hashMap=new HashMap<>();

    public boolean add(String key,TurtleValue value ,double score){
        createIfNotExists(key);
       return hashMap.get(key).add(value, score);
    }

    private void  createIfNotExists(String string){
        if (!hashMap.containsKey(string)){
            hashMap.put(string, new OrderSet<>());
        }
    }


    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData exists(String key, TurtleValue value, Double score) {
        OrderSet<TurtleValue> orderSet=hashMap.get(key);
        boolean res=false;
        if (orderSet!=null){
          res=  orderSet.exists(value,score);
        }
        return SingleResHelper.boolResponse(res);
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData exists(String key, TurtleValue value) {
        OrderSet<TurtleValue> orderSet=hashMap.get(key);
        boolean res=false;
        if (orderSet!=null){
            res=  orderSet.exists(value);
        }
        return SingleResHelper.boolResponse(res);
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData exists(String key) {
        return SingleResHelper.boolResponse(hashMap.containsKey(key));
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData add(String key, TurtleValue value, Double score) {
        createIfNotExists(key);

        return SingleResHelper.boolResponse(hashMap.get(key).add(value,score));
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData add(String key, Collection<TurtleValue> values, Collection<Double> doubles) {


        return null;
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData count(String key, Double begin, Double end) {
        int res=0;
        OrderSet<TurtleValue> set=hashMap.get(key);
        if (set!=null){
         res=set.count(begin, end);
        }
        return SingleResHelper.integerResponse(res);
    }

    @NotNull
    @Override
    public Collection<TurtleProtoBuf.ResponseData> range(String key, Double begin, Double end) {
        return null;
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData inRange(String key, Double begin, Double end) {
        return null;
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData delete(String key, TurtleValue value, Double score) {
        return null;
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData delete(String key, Double begin, Double end) {


        return null;
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData delete(String key, TurtleValue value) {
        return null;
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData delete(String key) {
        return null;
    }

    @NotNull
    @Override
    public Collection<TurtleProtoBuf.ResponseData> union(String key, String otherKey) {
        return null;
    }

    @NotNull
    @Override
    public Collection<TurtleProtoBuf.ResponseData> unionAndStore(String key, String otherKey) {
        return null;
    }
}

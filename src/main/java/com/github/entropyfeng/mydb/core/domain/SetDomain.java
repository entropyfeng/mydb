package com.github.entropyfeng.mydb.core.domain;

import com.github.entropyfeng.mydb.common.ops.ISetOperations;
import com.github.entropyfeng.mydb.common.protobuf.CollectionResponseDataHelper;
import com.github.entropyfeng.mydb.common.protobuf.SingleResponseDataHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.obj.TurtleValue;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author entropyfeng
 */
public class SetDomain implements ISetOperations {
    private HashMap<String, HashSet<TurtleValue>> setHashMap;

    public SetDomain() {
        this.setHashMap = new HashMap<>();
    }


    @Override
    public @NotNull TurtleProtoBuf.ResponseData exist(String key) {

        return SingleResponseDataHelper.boolResponse(setHashMap.containsKey(key));
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData exist(String key, TurtleValue value) {
        HashSet<TurtleValue> set = setHashMap.get(key);
        boolean res = false;
        if (set != null && set.contains(value)) {
            res = true;
        }
        return SingleResponseDataHelper.boolResponse(res);
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData add(String key, TurtleValue value) {
        createIfNotExists(key);
        setHashMap.get(key).add(value);
        return SingleResponseDataHelper.voidResponse();
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData union(String key, String otherKey) {

        setUnion(key, otherKey);
        return SingleResponseDataHelper.voidResponse();
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData union(String key, Collection<TurtleValue> turtleValues) {
        setUnion(key, turtleValues);
        return SingleResponseDataHelper.voidResponse();
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData intersect(String key, String otherKey) {

        setIntersect(key, otherKey);
        return SingleResponseDataHelper.voidResponse();
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData intersect(String key, Collection<TurtleValue> turtleValues) {
        setIntersect(key,turtleValues);
        return SingleResponseDataHelper.voidResponse();
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData difference(String key, String otherKey) {

        setDifference(key, otherKey);
        return SingleResponseDataHelper.voidResponse();
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData difference(String key, Collection<TurtleValue> turtleValues) {
        setDifference(key, turtleValues);
        return SingleResponseDataHelper.voidResponse();
    }

    @Override
    public @NotNull Collection<TurtleProtoBuf.ResponseData> entries(String key) {
       HashSet<TurtleValue> res= setHashMap.get(key);
       if (res==null){
           return CollectionResponseDataHelper.nullResponse();
       }else {
           return CollectionResponseDataHelper.turtleResponse(res);
       }
    }


    private void createIfNotExists(String key) {
        setHashMap.computeIfAbsent(key, k -> new HashSet<>());
    }


    private Collection<TurtleValue> setUnion(String key, String otherKey) {

        createIfNotExists(key);
        createIfNotExists(otherKey);
        setHashMap.get(key).addAll(setHashMap.get(otherKey));
        return setHashMap.get(key);
    }


    private Collection<TurtleValue> setUnion(String key, Collection<TurtleValue> turtleValues) {
        createIfNotExists(key);
        setHashMap.get(key).addAll(turtleValues);
        return setHashMap.get(key);
    }


    private Collection<TurtleValue> setIntersect(String key, String otherKey) {
        createIfNotExists(key);
        createIfNotExists(otherKey);
        setHashMap.get(key).retainAll(setHashMap.get(key));
        return setHashMap.get(key);
    }


    private Collection<TurtleValue> setIntersect(String key, Collection<TurtleValue> turtleValues) {
        createIfNotExists(key);
        setHashMap.get(key).retainAll(turtleValues);
        return setHashMap.get(key);
    }


    private Collection<TurtleValue> setDifference(String key, String otherKey) {
        createIfNotExists(key);
        createIfNotExists(otherKey);
        setHashMap.get(key).removeAll(setHashMap.get(otherKey));
        return setHashMap.get(key);
    }


   private Collection<TurtleValue> setDifference(String key, Collection<TurtleValue> turtleValues) {
        createIfNotExists(key);
        setHashMap.get(key).removeAll(turtleValues);
        return setHashMap.get(key);
    }


}

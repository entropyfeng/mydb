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

    public SetDomain(HashMap<String, HashSet<TurtleValue>> setHashMap) {
        this.setHashMap = setHashMap;
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
    public @NotNull Collection<TurtleProtoBuf.ResponseData> union(String key, String otherKey) {
        createIfNotExists(key);
        createIfNotExists(otherKey);
        setHashMap.get(key).addAll(setHashMap.get(otherKey));
        HashSet<TurtleValue> res = setHashMap.get(key);
        return CollectionResponseDataHelper.turtleResponse(res);
    }

    @Override
    public @NotNull Collection<TurtleProtoBuf.ResponseData> union(String key, Collection<TurtleValue> turtleValues) {
        createIfNotExists(key);
        setHashMap.get(key).addAll(turtleValues);

        return CollectionResponseDataHelper.turtleResponse(setHashMap.get(key));
    }

    @Override
    public @NotNull Collection<TurtleProtoBuf.ResponseData> intersect(String key, String otherKey) {
        return null;
    }

    @Override
    public @NotNull Collection<TurtleProtoBuf.ResponseData> intersect(String key, Collection<TurtleValue> turtleValues) {
        return null;
    }

    @Override
    public @NotNull Collection<TurtleProtoBuf.ResponseData> difference(String key, String otherKey) {
        return null;
    }

    @Override
    public @NotNull Collection<TurtleProtoBuf.ResponseData> difference(String key, Collection<TurtleValue> turtleValues) {
        return null;
    }

    @Override
    public @NotNull Collection<TurtleProtoBuf.ResponseData> entries(String key) {
        return null;
    }


    private void createIfNotExists(String key) {
        setHashMap.computeIfAbsent(key, k -> new HashSet<>());
    }

}

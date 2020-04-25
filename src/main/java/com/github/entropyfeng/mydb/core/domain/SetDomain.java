package com.github.entropyfeng.mydb.core.domain;

import com.github.entropyfeng.mydb.common.ops.ISetOperations;
import com.github.entropyfeng.mydb.common.protobuf.CollectionResHelper;
import com.github.entropyfeng.mydb.common.protobuf.SingleResHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/**
 * @author entropyfeng
 */
public class SetDomain implements ISetOperations, Serializable {

    private final HashMap<String, HashSet<TurtleValue>> setHashMap;

    public SetDomain() {
        this.setHashMap = new HashMap<>();
    }

    public SetDomain(HashMap<String, HashSet<TurtleValue>> map) {
        this.setHashMap = map;
    }


    @Override
    public @NotNull TurtleProtoBuf.ResponseData exist(String key) {

        return SingleResHelper.boolResponse(setHashMap.containsKey(key));
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData exist(String key, TurtleValue value) {
        HashSet<TurtleValue> set = setHashMap.get(key);
        boolean res = false;
        if (set != null && set.contains(value)) {
            res = true;
        }
        return SingleResHelper.boolResponse(res);
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData add(String key, TurtleValue value) {
        createIfNotExists(key);
        setHashMap.get(key).add(value);
        return SingleResHelper.voidResponse();
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData union(String key, String otherKey) {

        setUnion(key, otherKey);
        return SingleResHelper.voidResponse();
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData union(String key, Collection<TurtleValue> turtleValues) {
        setUnion(key, turtleValues);
        return SingleResHelper.voidResponse();
    }

    @Override
    public @NotNull Collection<TurtleProtoBuf.ResponseData> unionAndGet(String key, String otherKey) {


        return CollectionResHelper.turtleResponse(setUnion(key, otherKey));
    }

    @Override
    public @NotNull Collection<TurtleProtoBuf.ResponseData> unionAndGet(String key, Collection<TurtleValue> turtleValues) {
        return CollectionResHelper.turtleResponse(setUnion(key, turtleValues));
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData intersect(String key, String otherKey) {

        setIntersect(key, otherKey);
        return SingleResHelper.voidResponse();
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData intersect(String key, Collection<TurtleValue> turtleValues) {
        setIntersect(key, turtleValues);
        return SingleResHelper.voidResponse();
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData difference(String key, String otherKey) {

        setDifference(key, otherKey);
        return SingleResHelper.voidResponse();
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData difference(String key, Collection<TurtleValue> turtleValues) {
        setDifference(key, turtleValues);
        return SingleResHelper.voidResponse();
    }

    @Override
    public @NotNull Collection<TurtleProtoBuf.ResponseData> entries(String key) {
        HashSet<TurtleValue> res = setHashMap.get(key);
        if (res == null) {
            return CollectionResHelper.emptyResponse();
        } else {
            return CollectionResHelper.turtleResponse(res);
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


    public static void write(SetDomain setDomain, DataOutputStream outputStream) throws IOException {
        HashMap<String, HashSet<TurtleValue>> map = setDomain.setHashMap;
        outputStream.writeInt(map.size());
        for (Map.Entry<String, HashSet<TurtleValue>> entry : map.entrySet()) {
            String s = entry.getKey();
            byte[] stringBytes = s.getBytes();
            HashSet<TurtleValue> turtleValues = entry.getValue();
            outputStream.writeInt(stringBytes.length);
            outputStream.write(stringBytes);
            outputStream.flush();
            outputStream.writeInt(turtleValues.size());
            for (TurtleValue value : turtleValues) {
                TurtleValue.write(value, outputStream);
            }
        }
    }

    public static SetDomain read(DataInputStream inputStream) throws IOException {
        int mapSize = inputStream.readInt();
        HashMap<String, HashSet<TurtleValue>> map = new HashMap<>();
        SetDomain setDomain = new SetDomain(map);

        for (int i = 0; i < mapSize; i++) {

            int bytesSize = inputStream.readInt();
            byte[] stringBytes = new byte[bytesSize];
            inputStream.readFully(stringBytes);
            String string = new String(stringBytes);

            HashSet<TurtleValue> set = new HashSet<>();
            map.put(string, set);

            int turtleSize = inputStream.readInt();
            for (int j = 0; j < turtleSize; j++) {
                set.add(TurtleValue.read(inputStream));
            }
        }
        return setDomain;
    }

    public HashMap<String, HashSet<TurtleValue>> getSetHashMap() {
        return setHashMap;
    }
}

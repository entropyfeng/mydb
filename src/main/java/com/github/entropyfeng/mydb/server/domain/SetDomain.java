package com.github.entropyfeng.mydb.server.domain;

import com.github.entropyfeng.mydb.common.exception.DumpFileException;
import com.github.entropyfeng.mydb.common.ops.ISetOperations;
import com.github.entropyfeng.mydb.server.PersistenceHelper;
import com.github.entropyfeng.mydb.server.ResServerHelper;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.server.config.Constant;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.server.persistence.dump.SetDumpTask;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * @author entropyfeng
 */
public class SetDomain implements ISetOperations, Serializable {

    private final HashMap<String, HashSet<TurtleValue>> setHashMap;

    private static final Logger logger= LoggerFactory.getLogger(SetDomain.class);
    public SetDomain() {
        this.setHashMap = new HashMap<>();
    }

    public SetDomain(HashMap<String, HashSet<TurtleValue>> map) {
        this.setHashMap = map;
    }


    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> exist(String key) {

        return ResServerHelper.boolRes(setHashMap.containsKey(key));
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> exist(String key, TurtleValue value) {
        HashSet<TurtleValue> set = setHashMap.get(key);
        boolean res = false;
        if (set != null && set.contains(value)) {
            res = true;
        }
        return ResServerHelper.boolRes(res);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> add(String key, TurtleValue value) {
        createIfNotExists(key);
        setHashMap.get(key).add(value);
        return ResServerHelper.emptyRes();
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> union(String key, String otherKey) {

        setUnion(key, otherKey);
        return ResServerHelper.emptyRes();
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> union(String key, Collection<TurtleValue> turtleValues) {
        setUnion(key, turtleValues);
        return ResServerHelper.emptyRes();
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> unionAndGet(String key, String otherKey) {


        return ResServerHelper.turtleCollectionRes(setUnion(key, otherKey));
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> unionAndGet(String key, Collection<TurtleValue> turtleValues) {
        return ResServerHelper.turtleCollectionRes(setUnion(key, turtleValues));
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> intersect(String key, String otherKey) {

        setIntersect(key, otherKey);
        return ResServerHelper.emptyRes();
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> intersect(String key, Collection<TurtleValue> turtleValues) {
        setIntersect(key, turtleValues);
        return ResServerHelper.emptyRes();
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> difference(String key, String otherKey) {

        setDifference(key, otherKey);
        return ResServerHelper.emptyRes();
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> difference(String key, Collection<TurtleValue> turtleValues) {
        setDifference(key, turtleValues);
        return ResServerHelper.emptyRes();
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> entries(String key) {
        HashSet<TurtleValue> res = setHashMap.get(key);
        if (res == null) {
            return ResServerHelper.emptyRes();
        } else {
            return ResServerHelper.turtleCollectionRes(res);
        }
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> clear(){
        setHashMap.clear();
        return ResServerHelper.emptyRes();
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> dump() {

        return PersistenceHelper.singleDump(new SetDumpTask(new CountDownLatch(1),this,System.currentTimeMillis()));
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
        //magic number
        outputStream.write(Constant.MAGIC_NUMBER);
        HashMap<String, HashSet<TurtleValue>> map = setDomain.setHashMap;
        outputStream.writeInt(map.size());
        logger.error("writeMapSize{}",map.size());
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
        byte[] magicNumber=new byte[Constant.MAGIC_NUMBER.length];
        inputStream.readFully(magicNumber);
        if (!Arrays.equals(Constant.MAGIC_NUMBER,magicNumber)){
            throw new DumpFileException("error set dump file.");
        }

        int mapSize = inputStream.readInt();
        HashMap<String, HashSet<TurtleValue>> map = new HashMap<>(mapSize);
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


    //--------------------getter---------------

    public HashMap<String, HashSet<TurtleValue>> getSetHashMap() {
        return setHashMap;
    }
}
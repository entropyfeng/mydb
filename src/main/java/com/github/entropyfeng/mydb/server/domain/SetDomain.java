package com.github.entropyfeng.mydb.server.domain;

import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.exception.DumpFileException;
import com.github.entropyfeng.mydb.common.ops.ISetOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.server.PersistenceHelper;
import com.github.entropyfeng.mydb.server.ResServerHelper;
import com.github.entropyfeng.mydb.server.config.ServerConstant;
import com.github.entropyfeng.mydb.server.persistence.dump.SetDumpTask;
import com.google.common.base.Objects;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * @author entropyfeng
 */
public class SetDomain implements ISetOperations {

    private final HashMap<String, HashSet<TurtleValue>> setHashMap;


    public SetDomain() {
        this.setHashMap = new HashMap<>();
    }

    public SetDomain(HashMap<String, HashSet<TurtleValue>> map) {
        this.setHashMap = map;
    }


    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> exist(String key) {

        return ResServerHelper.boolRes(setHashMap.containsKey(key));
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> exist(String key, TurtleValue value) {
        HashSet<TurtleValue> set = setHashMap.get(key);
        boolean res = false;
        if (set != null && set.contains(value)) {
            res = true;
        }
        return ResServerHelper.boolRes(res);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> add(String key, TurtleValue value) {
        createIfNotExists(key);
        setHashMap.get(key).add(value);
        return ResServerHelper.emptyRes();
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> union(String key, String otherKey) {

        setUnion(key, otherKey);
        return ResServerHelper.emptyRes();
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> union(String key, Collection<TurtleValue> turtleValues) {
        setUnion(key, turtleValues);
        return ResServerHelper.emptyRes();
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> unionAndGet(String key, String otherKey) {


        return ResServerHelper.turtleCollectionRes(setUnion(key, otherKey));
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> unionAndGet(String key, Collection<TurtleValue> turtleValues) {
        return ResServerHelper.turtleCollectionRes(setUnion(key, turtleValues));
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> intersect(String key, String otherKey) {

        setIntersect(key, otherKey);
        return ResServerHelper.emptyRes();
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> intersect(String key, Collection<TurtleValue> turtleValues) {
        setIntersect(key, turtleValues);
        return ResServerHelper.emptyRes();
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> difference(String key, String otherKey) {

        setDifference(key, otherKey);
        return ResServerHelper.emptyRes();
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> difference(String key, Collection<TurtleValue> turtleValues) {
        setDifference(key, turtleValues);
        return ResServerHelper.emptyRes();
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> entries(String key) {
        HashSet<TurtleValue> res = setHashMap.get(key);
        if (res == null) {
            return ResServerHelper.emptyRes();
        } else {
            return ResServerHelper.turtleCollectionRes(res);
        }
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> size() {

        return ResServerHelper.intRes(setHashMap.size());
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> sizeOf(String key) {
        HashSet<TurtleValue> res = setHashMap.get(key);
        int resData=0;
        if (res!=null){
            resData=res.size();
        }
        return ResServerHelper.intRes(resData);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> clear(){
        setHashMap.clear();
        return ResServerHelper.emptyRes();
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> dump() {

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
        outputStream.write(ServerConstant.MAGIC_NUMBER);
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
        byte[] magicNumber=new byte[ServerConstant.MAGIC_NUMBER.length];
        inputStream.readFully(magicNumber);
        if (!Arrays.equals(ServerConstant.MAGIC_NUMBER,magicNumber)){
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


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SetDomain)){
            return false;
        }
        SetDomain setDomain = (SetDomain) o;
        return Objects.equal(setHashMap, setDomain.setHashMap);
    }

}

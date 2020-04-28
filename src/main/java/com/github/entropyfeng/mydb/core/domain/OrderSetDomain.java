package com.github.entropyfeng.mydb.core.domain;

import com.github.entropyfeng.mydb.common.exception.DumpFileException;
import com.github.entropyfeng.mydb.common.ops.IOrderSetOperations;
import com.github.entropyfeng.mydb.common.protobuf.ResHelper;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.config.Constant;
import com.github.entropyfeng.mydb.core.TurtleValue;
import com.github.entropyfeng.mydb.core.helper.Pair;
import com.github.entropyfeng.mydb.core.zset.OrderSet;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;

/**
 * @author entropyfeng
 */
public class OrderSetDomain implements IOrderSetOperations {

    private final HashMap<String, OrderSet<TurtleValue>> hashMap;

    public OrderSetDomain(HashMap<String, OrderSet<TurtleValue>> hashMap) {
        this.hashMap = hashMap;
    }

    public OrderSetDomain() {
        this.hashMap = new HashMap<>();
    }

    public boolean add(String key, TurtleValue value, double score) {
        createIfNotExists(key);
        return hashMap.get(key).add(value, score);
    }

    private void createIfNotExists(String string) {
        if (!hashMap.containsKey(string)) {
            hashMap.put(string, new OrderSet<>());
        }
    }


    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> exists(String key, TurtleValue value, Double score) {
        OrderSet<TurtleValue> orderSet = hashMap.get(key);
        boolean res = false;
        if (orderSet != null) {
            res = orderSet.exists(value, score);
        }
        return ResHelper.boolRes(res);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> exists(String key, TurtleValue value) {
        OrderSet<TurtleValue> orderSet = hashMap.get(key);
        boolean res = false;
        if (orderSet != null) {
            res = orderSet.exists(value);
        }
        return ResHelper.boolRes(res);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> exists(String key) {
        return ResHelper.boolRes(hashMap.containsKey(key));
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> add(String key, TurtleValue value, Double score) {
        createIfNotExists(key);

        return ResHelper.boolRes(hashMap.get(key).add(value, score));
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> add(String key, Collection<TurtleValue> values, Collection<Double> doubles) {

        if (values.size() != doubles.size()) {
            return ResHelper.illegalArgumentException("para not consistence");
        }

        createIfNotExists(key);
        OrderSet<TurtleValue> orderSet = hashMap.get(key);
        TurtleValue[] resValues = values.toArray(new TurtleValue[0]);
        Double[] resDoubles = doubles.toArray(new Double[0]);
        int size = values.size();
        for (int i = 0; i < size; i++) {
            orderSet.add(resValues[i], resDoubles[i]);
        }
        return ResHelper.intRes(size);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> count(String key, Double begin, Double end) {
        int res = 0;
        OrderSet<TurtleValue> set = hashMap.get(key);
        if (set != null) {
            res = set.count(begin, end);
        }
        return ResHelper.intRes(res);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> range(String key, Double begin, Double end) {
        OrderSet<TurtleValue> set = hashMap.get(key);
        List<TurtleValue> res = null;
        if (set != null) {
            res = set.range(begin, end);
        }
        if (res != null && res.size() != 0) {
            return ResHelper.turtleCollectionRes(res);
        } else {
            return ResHelper.emptyRes();
        }
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> inRange(String key, Double begin, Double end) {
        OrderSet<TurtleValue> set = hashMap.get(key);
        int res = set.count(begin, end);

        return ResHelper.boolRes(res != 0);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> delete(String key, TurtleValue value, Double score) {
        OrderSet<TurtleValue> set = hashMap.get(key);
        boolean res = false;
        if (set != null) {
            res = set.delete(value, score);
        }
        return ResHelper.boolRes(res);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> delete(String key, Double begin, Double end) {
        OrderSet<TurtleValue> set = hashMap.get(key);
        int res = 0;
        if (set != null) {
            res = set.delete(begin, end);
        }


        return ResHelper.intRes(res);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> delete(String key, TurtleValue value) {
        OrderSet<TurtleValue> set = hashMap.get(key);
        boolean res = false;
        if (set != null) {
            res = set.delete(value);
        }

        return ResHelper.boolRes(res);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> delete(String key) {

        hashMap.remove(key);
        return ResHelper.emptyRes();
    }


    public static void write(OrderSetDomain orderSetDomain, DataOutputStream outputStream) throws IOException {

        outputStream.write(Constant.MAGIC_NUMBER);

        HashMap<String, OrderSet<TurtleValue>> map = orderSetDomain.hashMap;
        outputStream.writeInt(map.size());
        for (Map.Entry<String, OrderSet<TurtleValue>> entry : map.entrySet()) {
            String s = entry.getKey();
            OrderSet<TurtleValue> orderSet = entry.getValue();
            outputStream.writeInt(orderSet.size());
            byte[] stringBytes = s.getBytes();
            outputStream.writeInt(stringBytes.length);
            outputStream.write(stringBytes);
            for (Map.Entry<TurtleValue, Double> e : orderSet.getHashMap().entrySet()) {
                TurtleValue turtleValue = e.getKey();
                Double aDouble = e.getValue();
                TurtleValue.write(turtleValue, outputStream);
                outputStream.writeDouble(aDouble);
            }

        }

    }

    public static OrderSetDomain read(DataInputStream inputStream) throws IOException {
        byte[] magicNumber = new byte[Constant.MAGIC_NUMBER.length];
        inputStream.readFully(magicNumber);
        if (!Arrays.equals(Constant.MAGIC_NUMBER, magicNumber)) {
            throw new DumpFileException("error order set dump file.");
        }

        int mapSize = inputStream.readInt();
        HashMap<String, OrderSet<TurtleValue>> map = new HashMap<>(mapSize);
        OrderSetDomain orderSetDomain = new OrderSetDomain(map);
        for (int i = 0; i < mapSize; i++) {
            int size = inputStream.readInt();
            int stringSize = inputStream.readInt();
            byte[] stringBytes = new byte[stringSize];
            inputStream.readFully(stringBytes);
            String string = new String(stringBytes);
            OrderSet<TurtleValue> orderSet = new OrderSet<>();
            map.put(string, orderSet);
            for (int j = 0; j < size; j++) {

                TurtleValue turtleValue = TurtleValue.read(inputStream);
                double aDouble = inputStream.readDouble();
                orderSet.add(turtleValue, aDouble);
            }
        }
        return orderSetDomain;
    }

    //----------------getter------------------

    public HashMap<String, OrderSet<TurtleValue>> getHashMap() {
        return hashMap;
    }
}

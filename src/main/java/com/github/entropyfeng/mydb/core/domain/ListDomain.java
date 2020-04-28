package com.github.entropyfeng.mydb.core.domain;

import com.github.entropyfeng.mydb.common.exception.DumpFileException;
import com.github.entropyfeng.mydb.common.ops.IListOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResBody;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;
import com.github.entropyfeng.mydb.common.protobuf.ResHelper;
import com.github.entropyfeng.mydb.config.Constant;
import com.github.entropyfeng.mydb.core.TurtleValue;
import com.github.entropyfeng.mydb.core.helper.Pair;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;


/**
 * @author entropyfeng
 */
public class ListDomain implements IListOperations {

    private final HashMap<String, LinkedList<TurtleValue>> listMap;

    public ListDomain() {
        super();
        this.listMap = new HashMap<>();
    }

    public ListDomain(HashMap<String, LinkedList<TurtleValue>> map) {
        this.listMap = map;
    }
   
    @Override
    public @NotNull  Pair<ResHead, Collection<ResBody>> size() {
        return ResHelper.intRes(listMap.size());
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<ResBody>> sizeOf(String key) {

        LinkedList<TurtleValue> list = listMap.get(key);
        int res = list == null ? 0 : listMap.size();
        return ResHelper.intRes(res);
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<ResBody>> leftPush(String key, TurtleValue value) {
        createIfNotExist(key);
        listMap.get(key).addFirst(value);

        return ResHelper.emptyRes();
    }


    @Override
    public @NotNull  Pair<ResHead, Collection<ResBody>> leftPushAll(String key, Collection<TurtleValue> values) {
        createIfNotExist(key);
        listMap.get(key).addAll(0, values);
        return ResHelper.emptyRes();
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<ResBody>> leftPushIfPresent(String key, TurtleValue value) {
        boolean res = false;

        if (listMap.containsKey(key)) {
            listMap.get(key).addFirst(value);
            res = true;
        }
        return ResHelper.boolRes(res);
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<ResBody>> leftPushIfAbsent(String key, TurtleValue value) {
        boolean res = false;
        if (!listMap.containsKey(key)) {
            listMap.put(key, new LinkedList<>());
            listMap.get(key).addFirst(value);
            res = true;
        }
        return ResHelper.boolRes(res);
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<ResBody>> rightPush(String key, TurtleValue value) {
        createIfNotExist(key);
        listMap.get(key).addLast(value);
        return ResHelper.emptyRes();
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<ResBody>> rightPushAll(String key, Collection<TurtleValue> values) {
        createIfNotExist(key);
        listMap.get(key).addAll(values);
        return ResHelper.emptyRes();
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<ResBody>> rightPushIfPresent(String key, TurtleValue value) {
        boolean res = false;

        if (listMap.containsKey(key)) {
            listMap.get(key).addLast(value);
            res = true;
        }
        return ResHelper.boolRes(res);

    }

    @Override
    public @NotNull  Pair<ResHead, Collection<ResBody>> rightPushIfAbsent(String key, TurtleValue value) {
        boolean res = false;
        if (!listMap.containsKey(key)) {
            listMap.put(key, new LinkedList<>());
            listMap.get(key).addLast(value);
            res = true;
        }
        return ResHelper.boolRes(res);
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<ResBody>> leftPop(String key) {

        TurtleValue res = null;
        if (listMap.containsKey(key)) {
            res = listMap.get(key).pollFirst();
        }
        if (res == null) {
            return ResHelper.emptyRes();
        } else {
            return ResHelper.turtleRes(res);
        }

    }

    @Override
    public @NotNull  Pair<ResHead, Collection<ResBody>> left(String key) {
        TurtleValue res = null;
        if (listMap.containsKey(key)) {
            res = listMap.get(key).peekFirst();
        }
        if (res == null) {
            return ResHelper.emptyRes();
        } else {
            return ResHelper.turtleRes(res);
        }
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<ResBody>> rightPop(String key) {
        TurtleValue res = null;
        if (listMap.containsKey(key)) {
            res = listMap.get(key).pollLast();
        }
        if (res == null) {
            return ResHelper.emptyRes();
        } else {
            return ResHelper.turtleRes(res);
        }

    }

    @Override
    public @NotNull  Pair<ResHead, Collection<ResBody>> right(String key) {
        TurtleValue res = null;
        if (listMap.containsKey(key)) {
            res = listMap.get(key).peekLast();
        }
        if (res == null) {
            return ResHelper.emptyRes();
        } else {
            return ResHelper.turtleRes(res);
        }
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<ResBody>> clear(String key) {
        listMap.remove(key);
        return ResHelper.emptyRes();
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<ResBody>> clearAll() {
        listMap.clear();
        return ResHelper.emptyRes();
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<ResBody>> exist(String key) {
        return ResHelper.boolRes(listMap.get(key) != null);
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<ResBody>> exist(String key, TurtleValue value) {
        LinkedList<TurtleValue> list = listMap.get(key);
        boolean res = list != null && list.contains(value);
        return ResHelper.boolRes(res);
    }

    private void createIfNotExist(String key) {

        listMap.putIfAbsent(key, new LinkedList<>());
    }

    public static void write(ListDomain listDomain, DataOutputStream outputStream) throws IOException {

        outputStream.write(Constant.MAGIC_NUMBER);

        outputStream.writeInt(listDomain.listMap.size());
        for (Map.Entry<String, LinkedList<TurtleValue>> entry : listDomain.listMap.entrySet()) {
            String s = entry.getKey();
            byte[] stringBytes=s.getBytes();
            LinkedList<TurtleValue> turtleValues = entry.getValue();
            outputStream.writeInt(stringBytes.length);
            outputStream.write(stringBytes);
            outputStream.writeInt(turtleValues.size());
            for (TurtleValue value : turtleValues) {
                TurtleValue.write(value, outputStream);
            }
        }
        outputStream.flush();
    }

    public static ListDomain read(DataInputStream inputStream) throws IOException {
        byte[] magicNumber=new byte[Constant.MAGIC_NUMBER.length];
        inputStream.readFully(magicNumber);
        if (!Arrays.equals(Constant.MAGIC_NUMBER,magicNumber)){
            throw new DumpFileException("error list dump file.");
        }

        int mapSize = inputStream.readInt();
        HashMap<String, LinkedList<TurtleValue>> map = new HashMap<>(mapSize);
        ListDomain listDomain = new ListDomain(map);
        for (int i = 0; i < mapSize; i++) {

            int bytesSize = inputStream.readInt();
            byte[] stringBytes = new byte[bytesSize];
            inputStream.readFully(stringBytes);
            String string = new String(stringBytes);

            LinkedList<TurtleValue> linkedList = new LinkedList<>();
            map.put(string, linkedList);

            int turtleSize = inputStream.readInt();
            for (int j = 0; j < turtleSize; j++) {
                linkedList.push(TurtleValue.read(inputStream));
            }
        }
        return listDomain;
    }

    //------------------getter-------------------

    public HashMap<String, LinkedList<TurtleValue>> getListMap() {
        return listMap;
    }
}

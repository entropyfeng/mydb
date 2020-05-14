package com.github.entropyfeng.mydb.server.domain;

import com.github.entropyfeng.mydb.common.exception.DumpFileException;
import com.github.entropyfeng.mydb.common.ops.IListOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.DataBody;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;
import com.github.entropyfeng.mydb.server.PersistenceHelper;
import com.github.entropyfeng.mydb.server.ResServerHelper;
import com.github.entropyfeng.mydb.server.config.ServerConstant;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.server.persistence.dump.ListDumpTask;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CountDownLatch;


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
    public @NotNull  Pair<ResHead, Collection<DataBody>> size() {
        return ResServerHelper.intRes(listMap.size());
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<DataBody>> sizeOf(String key) {

        LinkedList<TurtleValue> list = listMap.get(key);

        int res = (list == null) ? 0 : list.size();
        return ResServerHelper.intRes(res);
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<DataBody>> leftPush(String key, TurtleValue value) {
        createIfNotExist(key);
        listMap.get(key).addFirst(value);

        return ResServerHelper.emptyRes();
    }


    @Override
    public @NotNull  Pair<ResHead, Collection<DataBody>> leftPushAll(String key, Collection<TurtleValue> values) {
        createIfNotExist(key);
        listMap.get(key).addAll(0, values);
        return ResServerHelper.emptyRes();
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<DataBody>> leftPushIfPresent(String key, TurtleValue value) {
        boolean res = false;

        if (listMap.containsKey(key)) {
            listMap.get(key).addFirst(value);
            res = true;
        }
        return ResServerHelper.boolRes(res);
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<DataBody>> leftPushIfAbsent(String key, TurtleValue value) {
        boolean res = false;
        if (!listMap.containsKey(key)) {
            listMap.put(key, new LinkedList<>());
            listMap.get(key).addFirst(value);
            res = true;
        }
        return ResServerHelper.boolRes(res);
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<DataBody>> rightPush(String key, TurtleValue value) {
        createIfNotExist(key);
        listMap.get(key).addLast(value);
        return ResServerHelper.emptyRes();
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<DataBody>> rightPushAll(String key, Collection<TurtleValue> values) {
        createIfNotExist(key);
        listMap.get(key).addAll(values);
        return ResServerHelper.emptyRes();
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<DataBody>> rightPushIfPresent(String key, TurtleValue value) {
        boolean res = false;

        if (listMap.containsKey(key)) {
            listMap.get(key).addLast(value);
            res = true;
        }
        return ResServerHelper.boolRes(res);

    }

    @Override
    public @NotNull  Pair<ResHead, Collection<DataBody>> rightPushIfAbsent(String key, TurtleValue value) {
        boolean res = false;
        if (!listMap.containsKey(key)) {
            listMap.put(key, new LinkedList<>());
            listMap.get(key).addLast(value);
            res = true;
        }
        return ResServerHelper.boolRes(res);
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<DataBody>> leftPop(String key) {

        TurtleValue res = null;
        if (listMap.containsKey(key)) {
            res = listMap.get(key).pollFirst();
        }
        if (res == null) {
            return ResServerHelper.emptyRes();
        } else {
            return ResServerHelper.turtleRes(res);
        }

    }

    @Override
    public @NotNull  Pair<ResHead, Collection<DataBody>> left(String key) {
        TurtleValue res = null;
        if (listMap.containsKey(key)) {
            res = listMap.get(key).peekFirst();
        }
        if (res == null) {
            return ResServerHelper.emptyRes();
        } else {
            return ResServerHelper.turtleRes(res);
        }
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<DataBody>> rightPop(String key) {
        TurtleValue res = null;
        if (listMap.containsKey(key)) {
            res = listMap.get(key).pollLast();
        }
        if (res == null) {
            return ResServerHelper.emptyRes();
        } else {
            return ResServerHelper.turtleRes(res);
        }

    }

    @Override
    public @NotNull  Pair<ResHead, Collection<DataBody>> right(String key) {
        TurtleValue res = null;
        if (listMap.containsKey(key)) {
            res = listMap.get(key).peekLast();
        }
        if (res == null) {
            return ResServerHelper.emptyRes();
        } else {
            return ResServerHelper.turtleRes(res);
        }
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<DataBody>> clear(String key) {
        listMap.remove(key);
        return ResServerHelper.emptyRes();
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<DataBody>> clear() {
        listMap.clear();
        return ResServerHelper.emptyRes();
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<DataBody>> exist(String key) {
        return ResServerHelper.boolRes(listMap.get(key) != null);
    }

    @Override
    public @NotNull  Pair<ResHead, Collection<DataBody>> exist(String key, TurtleValue value) {
        LinkedList<TurtleValue> list = listMap.get(key);
        boolean res = list != null && list.contains(value);
        return ResServerHelper.boolRes(res);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> dump() {
        return PersistenceHelper.singleDump(new ListDumpTask(new CountDownLatch(1),this,System.currentTimeMillis()));
    }

    private void createIfNotExist(String key) {

        listMap.putIfAbsent(key, new LinkedList<>());
    }

    public static void write(ListDomain listDomain, DataOutputStream outputStream) throws IOException {

        outputStream.write(ServerConstant.MAGIC_NUMBER);

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
        byte[] magicNumber=new byte[ServerConstant.MAGIC_NUMBER.length];
        inputStream.readFully(magicNumber);
        if (!Arrays.equals(ServerConstant.MAGIC_NUMBER,magicNumber)){
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

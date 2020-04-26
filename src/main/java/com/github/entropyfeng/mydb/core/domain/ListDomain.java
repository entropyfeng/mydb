package com.github.entropyfeng.mydb.core.domain;

import com.github.entropyfeng.mydb.common.ops.IListOperations;
import com.github.entropyfeng.mydb.common.protobuf.SingleResHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.config.Constant;
import com.github.entropyfeng.mydb.core.TurtleValue;
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
    public @NotNull TurtleProtoBuf.ResponseData size() {
        return SingleResHelper.integerResponse(listMap.size());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData sizeOf(String key) {

        LinkedList<TurtleValue> list = listMap.get(key);
        int res = list == null ? 0 : listMap.size();
        return SingleResHelper.integerResponse(res);
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData leftPush(String key, TurtleValue value) {
        createIfNotExist(key);
        listMap.get(key).addFirst(value);

        return SingleResHelper.voidResponse();
    }


    @Override
    public @NotNull TurtleProtoBuf.ResponseData leftPushAll(String key, Collection<TurtleValue> values) {
        createIfNotExist(key);
        listMap.get(key).addAll(0, values);
        return SingleResHelper.voidResponse();
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData leftPushIfPresent(String key, TurtleValue value) {
        boolean res = false;

        if (listMap.containsKey(key)) {
            listMap.get(key).addFirst(value);
            res = true;
        }
        return SingleResHelper.boolResponse(res);
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData leftPushIfAbsent(String key, TurtleValue value) {
        boolean res = false;
        if (!listMap.containsKey(key)) {
            listMap.put(key, new LinkedList<>());
            listMap.get(key).addFirst(value);
            res = true;
        }
        return SingleResHelper.boolResponse(res);
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData rightPush(String key, TurtleValue value) {
        createIfNotExist(key);
        listMap.get(key).addLast(value);
        return SingleResHelper.voidResponse();
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData rightPushAll(String key, Collection<TurtleValue> values) {
        createIfNotExist(key);
        listMap.get(key).addAll(values);
        return SingleResHelper.voidResponse();
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData rightPushIfPresent(String key, TurtleValue value) {
        boolean res = false;

        if (listMap.containsKey(key)) {
            listMap.get(key).addLast(value);
            res = true;
        }
        return SingleResHelper.boolResponse(res);

    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData rightPushIfAbsent(String key, TurtleValue value) {
        boolean res = false;
        if (!listMap.containsKey(key)) {
            listMap.put(key, new LinkedList<>());
            listMap.get(key).addLast(value);
            res = true;
        }
        return SingleResHelper.boolResponse(res);
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData leftPop(String key) {

        TurtleValue res = null;
        if (listMap.containsKey(key)) {
            res = listMap.get(key).pollFirst();
        }
        if (res == null) {
            return SingleResHelper.nullResponse();
        } else {
            return SingleResHelper.turtleValueResponse(res);
        }

    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData left(String key) {
        TurtleValue res = null;
        if (listMap.containsKey(key)) {
            res = listMap.get(key).peekFirst();
        }
        if (res == null) {
            return SingleResHelper.nullResponse();
        } else {
            return SingleResHelper.turtleValueResponse(res);
        }
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData rightPop(String key) {
        TurtleValue res = null;
        if (listMap.containsKey(key)) {
            res = listMap.get(key).pollLast();
        }
        if (res == null) {
            return SingleResHelper.nullResponse();
        } else {
            return SingleResHelper.turtleValueResponse(res);
        }

    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData right(String key) {
        TurtleValue res = null;
        if (listMap.containsKey(key)) {
            res = listMap.get(key).peekLast();
        }
        if (res == null) {
            return SingleResHelper.nullResponse();
        } else {
            return SingleResHelper.turtleValueResponse(res);
        }
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData clear(String key) {
        listMap.remove(key);
        return SingleResHelper.voidResponse();
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData clearAll() {
        listMap.clear();
        return SingleResHelper.voidResponse();
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData exist(String key) {
        return SingleResHelper.boolResponse(listMap.get(key) != null);
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData exist(String key, TurtleValue value) {
        LinkedList<TurtleValue> list = listMap.get(key);
        boolean res = list != null && list.contains(value);
        return SingleResHelper.boolResponse(res);
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
            throw new IOException("un support dump file !");
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

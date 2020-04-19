package com.github.entropyfeng.mydb.core.domain;

import com.github.entropyfeng.mydb.common.ops.IListOperations;
import com.github.entropyfeng.mydb.common.protobuf.SingleResponseDataHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.obj.TurtleValue;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;


/**
 * @author entropyfeng
 */
public class ListDomain implements IListOperations {

    private final HashMap<String, LinkedList<TurtleValue>> listMap;

    public ListDomain() {
        super();
        this.listMap = new HashMap<>();
    }


    @Override
    public @NotNull TurtleProtoBuf.ResponseData size() {
        return SingleResponseDataHelper.integerResponse(listMap.size());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData sizeOf(String key) {

        LinkedList<TurtleValue> list=listMap.get(key);
        int res=list==null?0:listMap.size();
        return SingleResponseDataHelper.integerResponse(res);
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData leftPush(String key, TurtleValue value) {
        createIfNotExist(key);
        listMap.get(key).addFirst(value);

        return SingleResponseDataHelper.voidResponse();
    }


    @Override
    public @NotNull TurtleProtoBuf.ResponseData leftPushAll(String key, Collection<TurtleValue> values) {
       createIfNotExist(key);
       listMap.get(key).addAll(0,values);
        return SingleResponseDataHelper.voidResponse();
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData leftPushIfPresent(String key, TurtleValue value) {
        boolean res=false;

        if (listMap.containsKey(key)) {
            listMap.get(key).addFirst(value);
            res=true;
        }
        return SingleResponseDataHelper.boolResponse(res);
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData leftPushIfAbsent(String key, TurtleValue value) {
        boolean res=false;
        if (!listMap.containsKey(key)) {
            listMap.put(key,new LinkedList<>());
            listMap.get(key).addFirst(value);
            res=true;
        }
        return SingleResponseDataHelper.boolResponse(res);
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData rightPush(String key, TurtleValue value) {
        createIfNotExist(key);
        listMap.get(key).addLast(value);
        return SingleResponseDataHelper.voidResponse();
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData rightPushAll(String key, Collection<TurtleValue> values) {
        createIfNotExist(key);
        listMap.get(key).addAll(values);
        return SingleResponseDataHelper.voidResponse();
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData rightPushIfPresent(String key, TurtleValue value) {
        boolean res=false;

        if (listMap.containsKey(key)) {
            listMap.get(key).addLast(value);
            res=true;
        }
        return SingleResponseDataHelper.boolResponse(res);

    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData rightPushIfAbsent(String key, TurtleValue value) {
        boolean res=false;
        if (!listMap.containsKey(key)) {
            listMap.put(key,new LinkedList<>());
            listMap.get(key).addLast(value);
            res=true;
        }
        return SingleResponseDataHelper.boolResponse(res);
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData leftPop(String key) {

        TurtleValue res=null;
        if (listMap.containsKey(key)) {
           res=listMap.get(key).pollFirst();
        }
        if (res==null){
            return SingleResponseDataHelper.nullResponse();
        }else {
            return SingleResponseDataHelper.turtleValueResponse(res);
        }

    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData left(String key) {
        TurtleValue res=null;
        if (listMap.containsKey(key)) {
            res=listMap.get(key).peekFirst();
        }
        if (res==null){
            return SingleResponseDataHelper.nullResponse();
        }else {
            return SingleResponseDataHelper.turtleValueResponse(res);
        }
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData rightPop(String key) {
        TurtleValue res=null;
        if (listMap.containsKey(key)) {
            res=listMap.get(key).pollLast();
        }
        if (res==null){
            return SingleResponseDataHelper.nullResponse();
        }else {
            return SingleResponseDataHelper.turtleValueResponse(res);
        }

    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData right(String key) {
        TurtleValue res=null;
        if (listMap.containsKey(key)) {
            res=listMap.get(key).peekLast();
        }
        if (res==null){
            return SingleResponseDataHelper.nullResponse();
        }else {
            return SingleResponseDataHelper.turtleValueResponse(res);
        }
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData clear(String key) {
        listMap.remove(key);
        return SingleResponseDataHelper.voidResponse();
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData clearAll() {
        listMap.clear();
        return SingleResponseDataHelper.voidResponse();
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData exist(String key) {
        return SingleResponseDataHelper.boolResponse(listMap.get(key)!=null);
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData exist(String key, TurtleValue value) {
        LinkedList<TurtleValue> list= listMap.get(key);
        boolean res= list != null && list.contains(value);
        return SingleResponseDataHelper.boolResponse(res);
    }

    private void createIfNotExist(String key) {

        listMap.putIfAbsent(key,new LinkedList<>());
    }
}
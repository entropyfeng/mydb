package com.github.entropyfeng.mydb.common.protobuf;

import com.github.entropyfeng.mydb.core.TurtleValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author entropyfeng
 */
public class CollectionResHelper {


    public static Collection<TurtleProtoBuf.ResponseData> emptyResponse() {

        TurtleProtoBuf.ResponseData.Builder builder = TurtleProtoBuf.ResponseData.newBuilder();
        builder.setCollectionAble(true);
        builder.setSuccess(true);
        builder.setEndAble(true);
        ArrayList<TurtleProtoBuf.ResponseData> arrayList = new ArrayList<>(1);
        arrayList.add(builder.build());
        return arrayList;
    }

    public static Collection<TurtleProtoBuf.ResponseData> stringTurtleResponse(Set<Map.Entry<String, TurtleValue>> entrySet) {

        final int entrySize = entrySet.size();
        ArrayList<TurtleProtoBuf.ResponseData> resList = new ArrayList<>(entrySize + 1);
        //第一个responseData
        TurtleProtoBuf.ResponseData.Builder builder = TurtleProtoBuf.ResponseData.newBuilder();

        resList.add(buildFirst(builder, entrySize).build());


        TurtleProtoBuf.StringTurtleValueEntry.Builder entryBuilder = TurtleProtoBuf.StringTurtleValueEntry.newBuilder();

        entrySet.forEach(stringTurtleValueEntry -> {
            builder.clear();
            entryBuilder.clear();

            entryBuilder.setKey(stringTurtleValueEntry.getKey());
            entryBuilder.setValue(ProtoTurtleHelper.convertToProtoTurtleValue(stringTurtleValueEntry.getValue()));

            builder.setStringTurtleValueEntry(entryBuilder.build());
            builder.setEndAble(false);
            resList.add(builder.build());
        });

        //即使返回为空置也可将第一个res设置为end
        TurtleProtoBuf.ResponseData last = resList.get(entrySize).toBuilder().setEndAble(true).build();
        resList.set(entrySize, last);
        return resList;

    }

    public static Collection<TurtleProtoBuf.ResponseData> turtleTurtleResponse(Set<Map.Entry<TurtleValue, TurtleValue>> entries) {
        final int entrySize = entries.size();
        ArrayList<TurtleProtoBuf.ResponseData> resList = new ArrayList<>(entrySize + 1);

        TurtleProtoBuf.ResponseData.Builder builder = TurtleProtoBuf.ResponseData.newBuilder();
        resList.add(buildFirst(builder, entrySize).build());

        TurtleProtoBuf.TurtleTurtleValueEntry.Builder entryBuilder = TurtleProtoBuf.TurtleTurtleValueEntry.newBuilder();

        entries.forEach(entry -> {
            builder.clear();
            entryBuilder.clear();

            entryBuilder.setKey(ProtoTurtleHelper.convertToProtoTurtleValue(entry.getKey()));
            entryBuilder.setValue(ProtoTurtleHelper.convertToProtoTurtleValue(entry.getValue()));

            builder.setEndAble(false);
            builder.setTurtleTurtleValueEntry(entryBuilder.build());
            resList.add(builder.build());
        });

        //即使返回为空置也可将第一个res设置为end
        TurtleProtoBuf.ResponseData last = resList.get(entrySize).toBuilder().setEndAble(true).build();
        resList.set(entrySize, last);
        return resList;
    }

    public static Collection<TurtleProtoBuf.ResponseData> stringResponse(Collection<String> stringCollection) {
        final int stringsSize = stringCollection.size();
        ArrayList<TurtleProtoBuf.ResponseData> resList = new ArrayList<>(stringsSize + 1);
        //第一个responseData
        TurtleProtoBuf.ResponseData.Builder builder = TurtleProtoBuf.ResponseData.newBuilder();

        resList.add(buildFirst(builder, stringCollection.size()).build());


        //其他的res
        stringCollection.forEach(stringValue -> {
            builder.clear();
            builder.setStringValue(stringValue);
            builder.setEndAble(false);
            resList.add(builder.build());
        });

        //即使返回为空置也可将第一个res设置为end
        TurtleProtoBuf.ResponseData last = resList.get(stringsSize).toBuilder().setEndAble(true).build();
        resList.set(stringsSize, last);

        return resList;
    }

    private static TurtleProtoBuf.ResponseData.Builder buildFirst(TurtleProtoBuf.ResponseData.Builder builder, long size) {
        builder.setCollectionSize(size);
        builder.setSuccess(true);
        builder.setCollectionAble(true);
        builder.setEndAble(false);
        return builder;
    }

    public static Collection<TurtleProtoBuf.ResponseData> turtleResponse(Collection<TurtleValue> collection) {

        ArrayList<TurtleProtoBuf.ResponseData> resList = new ArrayList<>(collection.size() + 1);
        TurtleProtoBuf.ResponseData.Builder builder = TurtleProtoBuf.ResponseData.newBuilder();
        builder.setCollectionSize(collection.size());
        builder.setSuccess(true);
        builder.setCollectionAble(true);
        builder.setEndAble(false);
        resList.add(builder.build());

        collection.forEach(turtleValue -> {
            builder.clear();
            builder.setTurtleValue(ProtoTurtleHelper.convertToProtoTurtleValue(turtleValue));
            builder.setEndAble(false);
            resList.add(builder.build());
        });


        TurtleProtoBuf.ResponseData last = resList.get(collection.size()).toBuilder().setEndAble(true).build();
        resList.set(collection.size(), last);
        return resList;
    }

}

package com.github.entropyfeng.mydb.common.protobuf;

import com.github.entropyfeng.mydb.core.TurtleValue;

import java.util.*;

/**
 * @author entropyfeng
 */
public class CollectionResponseDataHelper {


    public static Collection<TurtleProtoBuf.ResponseData> nullResponse(){

        TurtleProtoBuf.ResponseData.Builder builder = TurtleProtoBuf.ResponseData.newBuilder();
        builder.setCollectionAble(true);
        builder.setSuccess(true);
        builder.setNullable(true);
        ArrayList<TurtleProtoBuf.ResponseData> arrayList=new ArrayList<>(1);
        arrayList.add(builder.build());
        return arrayList;
    }

    public static Collection<TurtleProtoBuf.ResponseData> stringTurtleResponse(Set<Map.Entry<String, TurtleValue>> entrySet){
        final int entrySize = entrySet.size();
        ArrayList<TurtleProtoBuf.ResponseData> resList = new ArrayList<>(entrySize+1);
        //第一个responseData
        TurtleProtoBuf.ResponseData.Builder builder = TurtleProtoBuf.ResponseData.newBuilder();
        builder.setCollectionSize(entrySize);
        builder.setSuccess(true);
        builder.setCollectionAble(true);
        builder.setEndAble(false);
        resList.add(builder.build());


        TurtleProtoBuf.StringTurtleValueEntry.Builder entryBuilder = TurtleProtoBuf.StringTurtleValueEntry.newBuilder();

        entrySet.forEach(stringTurtleValueEntry -> {
            builder.clear();
            entryBuilder.clear();

            entryBuilder.setKey(stringTurtleValueEntry.getKey());
            entryBuilder.setTurtleValue(ProtoTurtleHelper.convertToProtoTurtleValue(stringTurtleValueEntry.getValue()));

            builder.setStringTurtleValueEntry(entryBuilder.build());
            builder.setEndAble(false);
            resList.add(builder.build());
        });

        TurtleProtoBuf.ResponseData last = resList.get(resList.size() - 1).toBuilder().setEndAble(true).build();
        resList.set(resList.size() - 1, last);
        return resList;

    }
    public static Collection<TurtleProtoBuf.ResponseData> turtleResponse(Collection<TurtleValue> collection){



        ArrayList<TurtleProtoBuf.ResponseData> resList=new ArrayList<>(collection.size()+1);
        TurtleProtoBuf.ResponseData.Builder builder=TurtleProtoBuf.ResponseData.newBuilder();
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


        TurtleProtoBuf.ResponseData last = resList.get(resList.size() - 1).toBuilder().setEndAble(true).build();
        resList.set(resList.size() - 1, last);
        return resList;
    }

}

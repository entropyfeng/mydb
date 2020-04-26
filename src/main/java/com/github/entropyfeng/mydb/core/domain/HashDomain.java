package com.github.entropyfeng.mydb.core.domain;

import com.github.entropyfeng.mydb.common.exception.DumpFileException;
import com.github.entropyfeng.mydb.common.ops.IHashOperations;
import com.github.entropyfeng.mydb.common.protobuf.CollectionResHelper;
import com.github.entropyfeng.mydb.common.protobuf.SingleResHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.config.Constant;
import com.github.entropyfeng.mydb.core.TurtleValue;
import com.github.entropyfeng.mydb.core.dict.ElasticMap;
import org.jetbrains.annotations.NotNull;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author entropyfeng
 */
public class HashDomain implements IHashOperations, Serializable {

    public HashDomain() {
        this.hashMap = new HashMap<>();
    }

    public HashDomain(HashMap<String, ElasticMap<TurtleValue, TurtleValue>> hashMap){
        this.hashMap=hashMap;
    }
    private HashMap<String, ElasticMap<TurtleValue, TurtleValue>> hashMap;

    @Override
    public @NotNull TurtleProtoBuf.ResponseData get(@NotNull String key, @NotNull TurtleValue tKey) {

        ElasticMap<TurtleValue, TurtleValue> map = hashMap.get(key);
        TurtleValue res = null;
        if (map != null) {
            res = map.get(tKey);
        }

        if (res == null) {
            return SingleResHelper.nullResponse();
        } else {
            return SingleResHelper.turtleValueResponse(res);
        }
    }

    @Override
    public @NotNull Collection<TurtleProtoBuf.ResponseData> get(@NotNull String key) {

        ElasticMap<TurtleValue, TurtleValue> res = hashMap.get(key);

        if (res == null) {
            return CollectionResHelper.emptyResponse();
        } else {
            return CollectionResHelper.turtleTurtleResponse(res.entrySet());
        }
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData exists(@NotNull String key, @NotNull TurtleValue tKey, TurtleValue tValue) {
        ElasticMap<TurtleValue, TurtleValue> map = hashMap.get(key);

        boolean res = false;
        if (map != null) {
            res = map.containsKey(tKey);
        }
        return SingleResHelper.boolResponse(res);
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData exists(@NotNull String key) {

        return SingleResHelper.boolResponse(hashMap.containsKey(key));
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData put(@NotNull String key, @NotNull TurtleValue tKey, @NotNull TurtleValue tValue) {

        createIfNotExist(key);
        hashMap.get(key).put(tKey,tValue);
        return SingleResHelper.voidResponse();
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData delete(@NotNull String key) {
        hashMap.remove(key);
        return SingleResHelper.voidResponse();
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData delete(@NotNull String key, @NotNull TurtleValue tKey) {
       ElasticMap<TurtleValue,TurtleValue> map= hashMap.get(key);
       if (map!=null){
           map.remove(tKey);
       }
        return SingleResHelper.voidResponse();
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData sizeOf(@NotNull String key) {
        ElasticMap<TurtleValue,TurtleValue> map= hashMap.get(key);
        if (map!=null){
            return SingleResHelper.integerResponse(map.size());
        }else {
            return SingleResHelper.integerResponse(0);
        }
    }



    private void createIfNotExist(String key){
        if (!hashMap.containsKey(key)){
            hashMap.put(key,new ElasticMap<>());
        }
    }
    public static void write(HashDomain hashDomain,DataOutputStream outputStream)throws IOException {
        outputStream.write(Constant.MAGIC_NUMBER);

        HashMap<String, ElasticMap<TurtleValue, TurtleValue>> hashMap=  hashDomain.hashMap;
        outputStream.writeInt(hashMap.size());
        for (Map.Entry<String, ElasticMap<TurtleValue, TurtleValue>> entry : hashMap.entrySet()) {

            String s = entry.getKey();
            byte[] stringBytes=s.getBytes();
            ElasticMap<TurtleValue, TurtleValue> elasticMap = entry.getValue();
            outputStream.writeInt(elasticMap.size());
            outputStream.writeInt(stringBytes.length);
            outputStream.write(stringBytes);

            for (Map.Entry<TurtleValue, TurtleValue> e : elasticMap.entrySet()) {
                TurtleValue key = e.getKey();
                TurtleValue value = e.getValue();
                TurtleValue.write(key,outputStream);
                TurtleValue.write(value,outputStream);
            }
        }
    }
    public static HashDomain read(DataInputStream inputStream) throws IOException {
        byte[] magicNumber=new byte[Constant.MAGIC_NUMBER.length];
        inputStream.readFully(magicNumber);
        if (!Arrays.equals(Constant.MAGIC_NUMBER,magicNumber)){
            throw new DumpFileException("error hash dump file.");
        }

        HashMap<String, ElasticMap<TurtleValue, TurtleValue>> map=new HashMap<>();
        HashDomain hashDomain=new HashDomain(map);
        int hashSize=inputStream.readInt();
        for (int i = 0; i <hashSize ; i++) {
            int elasticSize=inputStream.readInt();
            int stringSize=inputStream.readInt();
            byte []stringBytes=new byte[stringSize];
            inputStream.readFully(stringBytes);
            String string=new String(stringBytes);
            ElasticMap<TurtleValue,TurtleValue> elasticMap=new ElasticMap<>();
            map.put(string,elasticMap);
            for (int j = 0; j < elasticSize; j++) {
                elasticMap.put(TurtleValue.read(inputStream),TurtleValue.read(inputStream));
            }
        }
        return hashDomain;
    }
    //---------------getter---------------

    public HashMap<String, ElasticMap<TurtleValue, TurtleValue>> getHashMap() {
        return hashMap;
    }
}

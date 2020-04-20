package com.github.entropyfeng.mydb.core.domain;

import com.github.entropyfeng.mydb.common.ops.IHashOperations;
import com.github.entropyfeng.mydb.common.protobuf.CollectionResHelper;
import com.github.entropyfeng.mydb.common.protobuf.SingleResHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;
import com.github.entropyfeng.mydb.core.dict.ElasticMap;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;

/**
 * @author entropyfeng
 */
public class HashDomain implements IHashOperations {

    public HashDomain() {
        this.hashMap = new HashMap<>();
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

}

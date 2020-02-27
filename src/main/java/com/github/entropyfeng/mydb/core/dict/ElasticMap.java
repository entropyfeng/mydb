package com.github.entropyfeng.mydb.core.dict;

import com.google.common.base.Charsets;
import com.google.common.hash.Funnel;
import com.google.common.hash.Hashing;
import com.google.common.hash.PrimitiveSink;

import java.nio.charset.Charset;
import java.util.HashMap;

/**
 * @author entropyfeng
 * @date 2020/2/20 17:57
 */
public class ElasticMap<K, V>  {

    /**
     * 默认初始大小16
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    /**
     * 最大容量2的30次方
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    /**
     * 默认负载因子
     * load_factor=哈希表已保存节点数量/哈希表大小
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;


    static int hashing(int key){
        return Hashing.murmur3_32().hashInt(key).asInt();
    }
    static int hashing(String key){
        return Hashing.murmur3_32().hashString(key,Charsets.UTF_8).asInt();
    }
    static int hashing(long key){
        return Hashing.murmur3_32().hashLong(key).asInt();
    }
    static int hashing(float key){
        return Hashing.murmur3_32().newHasher().putFloat(key).hash().asInt();
    }
    static int hashing(double key){
        return Hashing.murmur3_32().newHasher().putDouble(key).hash().asInt();
    }

    MapObject<K, V> first;

    MapObject<K, V> second;


    int rehashIndex;









    public static void main(String[] args) {


        System.out.println("hello");
    }

}

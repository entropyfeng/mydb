package com.github.entropyfeng.mydb.core.dict;

/**
 * @author entropyfeng
 * @date 2020/2/20 17:57
 */
public class ElasticMap<K,V> {

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
    static final double DEFAULT_LOAD_FACTOR=0.75;


    MapObject<K,V> first;

    MapObject<K,V> second;



    int rehashIndex;

    /**
     * 返回大于等于capacity的最小2的整数次幂
     */
    static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }

   void test(){


    }






}

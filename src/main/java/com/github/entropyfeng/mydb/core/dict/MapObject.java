package com.github.entropyfeng.mydb.core.dict;

import java.util.HashMap;

/**
 * @author entropyfeng
 * @date 2020/2/23 14:41
 */
public class MapObject<K,V> {

    MapObject(){

    }
    MapObject(int capacity){}

    Node<K,V>[]table;
    /**
     *    哈希表大小
     */
    int size;

    /**
     * 哈希表大小掩码,用于计算索引值
     * 总等于size-1
     */
    int sizeMask;

    /**
     * 已用结点数量
     */
    int used;

    public static void main(String[] args) {
        System.out.println(Float.isNaN(0.0f/0.0f));
    }
}

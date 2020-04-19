package com.github.entropyfeng.mydb.core.helper;

import afu.org.checkerframework.checker.oigj.qual.O;

import java.util.Map;

/**
 * @author entropyfeng
 * @date 2020/3/6 17:02
 */
public class Pair<K,V>implements Map.Entry<K,V> {

    private K key;
    private V value;
    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V old=this.value;
        this.value = value;
        return old;
    }

    @Override
    public String toString() {
        return "Pair{" + "key=" + key + ", value=" + value + '}';
    }
}

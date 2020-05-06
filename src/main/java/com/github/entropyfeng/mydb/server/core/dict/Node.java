package com.github.entropyfeng.mydb.server.core.dict;


import java.util.Map;

/**
 * @author entropyfeng
 * @date 2020/2/23 14:37
 */
class Node<K, V> implements Map.Entry<K, V> {

    K key;

    V value;

    Node<K, V> next;

    public Node(K key, V value, Node<K, V> next) {
        this.key = key;
        this.value = value;
        this.next = next;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    @Override
    public V setValue(V value) {
        V old = this.value;
        this.value = value;
        return old;
    }
}

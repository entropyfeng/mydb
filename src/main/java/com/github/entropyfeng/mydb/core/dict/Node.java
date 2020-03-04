package com.github.entropyfeng.mydb.core.dict;


/**
 * @author entropyfeng
 * @date 2020/2/23 14:37
 */
class Node<K,V> {

    K key;

    V value;

    Node<K,V> next;

    Node(K key,V value,Node<K,V> next){
        this.key=key;
        this.value=value;
        this.next=next;
    }
}

package com.github.entropyfeng.mydb.core.dict;

import com.github.entropyfeng.mydb.core.helper.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static com.github.entropyfeng.mydb.core.dict.ElasticMap.*;

/**
 * @author entropyfeng
 * @date 2020/2/27 15:27
 */
class MapObject<K, V> {


    /**
     * 返回大于等于capacity的最小2的整数次幂
     * 仅在{@link MapObject}初始化中使用
     */
    private static int tableSizeFor(int cap) {
        int n = cap - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        return (n < 0) ? 1 : (n >= MAXIMUM_CAPACITY) ? MAXIMUM_CAPACITY : n + 1;
    }


    MapObject() {
        this(DEFAULT_INITIAL_CAPACITY, DEFAULT_LOAD_FACTOR);
    }

    MapObject(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * 按照给定初始容量、负载因子创建{@link MapObject}
     *
     * @param initialCapacity 初始容量
     * @param loadFactor      负载因子
     */
    @SuppressWarnings("unchecked")
    MapObject(int initialCapacity, float loadFactor) {

        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal initial capacity: " + initialCapacity);
        }

        if (initialCapacity > MAXIMUM_CAPACITY) {
            initialCapacity = MAXIMUM_CAPACITY;
        }
        if (loadFactor <= 0 || Float.isNaN(loadFactor)) {
            throw new IllegalArgumentException("Illegal load factor: " + loadFactor);
        }
        final int tempThreshold = tableSizeFor(initialCapacity);
        this.loadFactor = loadFactor;
        table = new Node[tempThreshold];
        size = tempThreshold;
        sizeMask = size - 1;
    }

    private Node<K, V>[] table;
    /**
     * 哈希表大小
     */
    private final int size;

    /**
     * 哈希表大小掩码,用于计算索引值
     * 总等于size-1
     */
    private transient final int sizeMask;

    /**
     * 在resize过程中辅助变量
     */
    private transient int movePos = 0;
    /**
     * 实际已用结点数量
     */
    int used = 0;

    private final float loadFactor;


    /**
     * 如果表中不存在该key则将value插入,否则用新value替换旧value
     *
     * @param key   键
     * @param value 值
     */
    V put(@NotNull K key, @NotNull V value) {

        final int pos = hashing(key) & sizeMask;
        assert pos >= 0 && pos < size;
        //tempNode
        Node<K, V> tempNode = table[pos];

        V resValue = null;
        //如果当前槽为空,则创建新节点
        if (tempNode == null) {
            table[pos] = new Node<K, V>(key, value, null);
            used++;
        } else {

             /*
              在相同hash值链表中找到相应key所对应结点
              如果为null 则不存在该key
             */
            while (tempNode.next != null && !Objects.equals(key, tempNode.next.value)) {
                tempNode = tempNode.next;
            }
            //即尾结点
            if (tempNode.next == null) {
                tempNode.next = new Node<K, V>(key, value, null);
                used++;
            } else {
                resValue = tempNode.value;
                tempNode.value = value;
            }
        }

        return resValue;
    }


    /**
     * 根据key(notNull)获取 value
     * 如果为空则不存在该key 否则返回该value
     *
     * @param key 键
     * @return null ->不存在该key
     * notNull->value
     */
    V getValue(Object key) {
        final Node<K, V> tempNode = getNode(key);
        return tempNode != null ? tempNode.value : null;
    }

    private Node<K, V> getNode(Object key) {
        assert key != null;
        final int pos = hashing(key) & sizeMask;
        //tempNode
        Node<K, V> tempNode = table[pos];
        while (tempNode != null && !tempNode.key.equals(key)) {
            tempNode = tempNode.next;
        }
        return tempNode;
    }

    /**
     * 查询指定的键是否存在
     *
     * @param key key notNull
     * @return true->存在
     * false->不存在
     */
    boolean isExist(K key) {
        return getNode(key) != null;
    }

    /**
     * return the map whether or not null
     *
     * @return true-> the map is null
     * false-> the map is notNull
     */
    boolean isEmpty() {
        return used == 0;
    }


    Set<Pair<K, V>> allEntries() {
        Set<Pair<K, V>> set = new HashSet<>(used);
        Node<K, V> tempNode;
        for (int i = 0; i < size; i++) {
            tempNode = table[i];
            while (tempNode != null) {
                set.add(new Pair<K, V>(tempNode.key, tempNode.value));
                tempNode = tempNode.next;
            }
        }
        return set;
    }

    /**
     * 删除指定的key对应的node
     *
     * @param key key notNull
     * @return null-> no key exists before call this method
     */
    V deleteKey(@NotNull Object key) {

        final int pos = hashing(key) & sizeMask;
        V resValue = null;

        //tempNode
        Node<K, V> tempNode = table[pos];

        if (tempNode != null) {
            //第一个结点的key匹配
            if (tempNode.key.equals(key)) {
                resValue = tempNode.value;
                table[pos] = tempNode.next;
                used--;
            } else {
                while (tempNode.next != null && !Objects.equals(tempNode.next.key,key)) {
                    tempNode = tempNode.next;
                }
                if (tempNode.next != null) {
                    resValue = tempNode.next.value;
                    tempNode.next = tempNode.next.next;
                    used--;
                }
            }
        }

        return resValue;
    }


    boolean isCorrespondingEnlargeSize() {
        return (used > size * loadFactor);
    }

    boolean isCorrespondingNarrowSize() {
        return used < size * 0.1;
    }


    /**
     * 找到并返回下一个不为空的头结点,若为空则当前节点已经为最后节点
     *
     * @return 头结点 {@link Node}
     */
    Node<K, V> getMoveEntry() {

        int tempMovePos = movePos;

        //找到第一个不为null的节点
        while (tempMovePos < size && table[tempMovePos] == null) {
            tempMovePos++;
        }

        //已经移动结束
        if (tempMovePos == size) {
            return null;
        } else {
            movePos = tempMovePos;
            Node<K, V> tempNode = table[tempMovePos];
            //清空槽
            table[tempMovePos] = null;
            return tempNode;
        }
    }

    private int hashing(@NotNull Object o) {
        return o.hashCode();
    }
    //-----------get and set-----------

}

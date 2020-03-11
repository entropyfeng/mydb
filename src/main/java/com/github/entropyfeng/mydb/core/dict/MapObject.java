package com.github.entropyfeng.mydb.core.dict;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;

import static com.github.entropyfeng.mydb.core.dict.ElasticMap.DEFAULT_INITIAL_CAPACITY;
import static com.github.entropyfeng.mydb.core.dict.ElasticMap.DEFAULT_LOAD_FACTOR;
import static com.github.entropyfeng.mydb.core.dict.ElasticMap.MAXIMUM_CAPACITY;

/**
 * @author entropyfeng
 * @date 2020/2/27 15:27
 */
class MapObject<K, V> {


    /**
     * 返回大于等于capacity的最小2的整数次幂
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
        int tempThreshold = tableSizeFor(initialCapacity);
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
    private transient int sizeMask;

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
     * 使用murmur3 hash
     *
     * @param key 任意对象 isNotNull
     * @return hashCode
     */
    private int hashing(final K key) {
        assert key != null;
        if (key instanceof String) {
            return Hashing.murmur3_32().hashString((String) key, Charsets.UTF_8).asInt();
        } else if (key instanceof Integer) {
            return Hashing.murmur3_32().hashInt((Integer) key).asInt();
        } else if (key instanceof Long) {
            return Hashing.murmur3_32().hashLong((Long) key).asInt();
        } else if (key instanceof Double) {
            return Hashing.murmur3_32().newHasher().putDouble((Double) key).hash().asInt();
        } else if (key instanceof Float) {
            return Hashing.murmur3_32().newHasher().putFloat((Float) key).hash().asInt();
        }
        //you are not except access this region
        throw new Error("UnSupport Hashing Type");
    }

    /**
     * 如果表中不存在该key则将value插入,否则用新value替换旧value
     *
     * @param key   键
     * @param value 值
     */
    void replaceVal(K key, V value) {
        assert key != null && value != null;
        final int pos = hashing(key) & sizeMask;
        assert pos >= 0 && pos < size;
        //tempNode
        Node<K, V> tempNode = table[pos];

        //如果当前槽为空,则创建新节点
        if (tempNode == null) {
            table[pos] = new Node<K, V>(key, value, null);
            used++;
        } else {

             /*
              在相同hash值链表中找到相应key所对应结点
              如果为null 则不存在该key
             */
            while (tempNode.next != null && !key.equals(tempNode.next.value)) {
                tempNode = tempNode.next;
            }
            if (tempNode.next == null) {
                tempNode.next = new Node<K, V>(key, value, null);
                used++;
            } else {
                tempNode.value = value;
            }
        }

    }

    /**
     * 如果表中不存在该key则将value插入,否则不插入
     *
     * @param key   键 notNull
     * @param value 值 notNull
     */
    boolean addVal(K key, V value) {

        boolean res = true;
        assert key != null && value != null;
        final int pos = hashing(key) & sizeMask;
        assert pos >= 0 && pos < size;
        //tempNode
        Node<K, V> tempNode = table[pos];

        //如果当前槽为空,则创建新节点
        if (tempNode == null) {
            table[pos] = new Node<K, V>(key, value, null);
            used++;
        } else {

             /*
              在相同hash值链表中找到相应key所对应结点
              如果为null 则不存在该key
             */
            while (tempNode.next != null && !key.equals(tempNode.next.value)) {
                tempNode = tempNode.next;
            }
            if (tempNode.next == null) {
                tempNode.next = new Node<K, V>(key, value, null);
                used++;
            } else {
                res = false;
            }
        }
        return res;
    }

    /**
     * 根据key获取 value notNull
     * 如果为空则不存在该key 否则返回该value
     *
     * @param key 键
     * @return null ->不存在该key
     * notNull->value
     */
    V getValue(K key) {
        final Node<K, V> tempNode = getNode(key);

        return tempNode != null ? tempNode.value : null;
    }

    private Node<K, V> getNode(K key) {
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

    /**
     * 删除指定的key对应的node
     *
     * @param key key notNull
     * @return true->删除成功
     * false->不存在键或删除失败
     */
    boolean deleteKey(K key) {
        assert key != null;
        final int pos = hashing(key) & sizeMask;
        boolean res = false;
        //tempNode
        Node<K, V> tempNode = table[pos];

        if (tempNode != null) {
            //第一个结点的key匹配
            if (tempNode.key.equals(key)) {
                table[pos] = tempNode.next;
                res = true;
                used--;
            } else {
                while (tempNode.next != null && !tempNode.next.key.equals(key)) {
                    tempNode = tempNode.next;
                }
                if (tempNode.next != null) {
                    tempNode.next = tempNode.next.next;
                    res = true;
                    used--;
                }
            }
        }

        return res;
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

        while (tempMovePos < size && table[tempMovePos] == null) {
            tempMovePos++;
        }

        //已经移动结束
        if (tempMovePos == size) {
            return null;
        } else {
            movePos = tempMovePos;
            Node<K, V> tempNode = table[tempMovePos];
            table[tempMovePos] = null;
            return tempNode;
        }
    }

    //-----------get and set-----------

}

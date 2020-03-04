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
    int size;

    /**
     * 哈希表大小掩码,用于计算索引值
     * 总等于size-1
     */
    private transient int sizeMask;

    /**
     * 在resize过程中辅助变量
     */
    private transient int movePos=0;
    /**
     * 已用结点数量
     */
    int used = 0;

    private final float loadFactor;

    /**
     * 使用murmur3 hash
     * @param key 任意对象
     * @return hashCode
     */
    private int hashing(K key) {

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
     * 如果表中存在key则用value 替换oldValue
     *
     * @param key   键
     * @param value 值
     */
    void putVal(K key, V value) {

        int pos = hashing(key) & sizeMask;
        assert pos >= 0 && pos < size;


        //tempNode
        Node tempNode = table[pos];


        //如果当前槽为空,则创建新节点
        if (tempNode == null) {
            table[pos] = new Node<K, V>(key, value, null);
            used++;
        } else {
            Node tailNode = null;

            /*
              在相同hash值链表中找到相应key所对应结点
              如果为null 则不存在该key
             */
            while (tempNode != null && !tempNode.key.equals(key)) {
                tailNode = tempNode;
                tempNode = tempNode.next;
            }

            if (tempNode == null) {
                tailNode.next = new Node<K, V>(key, value, null);
                used++;
            } else {
                tempNode.value = value;
            }
        }

    }

    /**
     * 根据key获取 value
     *
     * @param key 键
     * @return null ->不存在该key或该key对应的value为空
     * notNull->value
     */
    V getValue(K key) {
        int pos = hashing(key) & sizeMask;

        //tempNode
        Node<K, V> tempNode = table[pos];
        while (tempNode != null && !tempNode.key.equals(key)) {
            tempNode = tempNode.next;
        }
        if (tempNode != null) {
            return tempNode.value;
        } else {
            return null;
        }

    }

    /**
     * 查询指定的键是否存在
     *
     * @param key key
     * @return true->存在
     * false->不存在
     */
    boolean isExist(K key) {
        int pos = hashing(key) & sizeMask;

        //tempNode
        Node<K, V> tempNode = table[pos];
        while (tempNode != null && !tempNode.key.equals(key)) {
            tempNode = tempNode.next;
        }
        return tempNode != null;
    }

    boolean isEmpty() {
        return used == 0;
    }

    /**
     * 获取当前存在的结点数量
     *
     * @return 结点数量
     */
    int getUsed() {
        return used;
    }

    /**
     * 删除指定的key对应的node
     *
     * @param key key
     * @return true->删除成功
     * false->不存在键或删除失败
     */
    boolean deleteKey(K key) {
        int pos = hashing(key) & sizeMask;

        boolean res = false;
        //tempNode
        Node<K, V> tempNode = table[pos];

        if (tempNode != null) {
            //第一个结点的key匹配
            if (tempNode.key.equals(key)) {
                table[pos] = tempNode.next;
                res = true;
            } else {
                while (tempNode.next != null && !tempNode.next.key.equals(key)) {
                    tempNode = tempNode.next;
                }
                if(tempNode.next!=null){
                    tempNode.next = tempNode.next.next;
                    res=true;
                }
            }
        }
        if (res){
            used--;
        }
        return res;
    }

    boolean isCorrespondingEnlargeSize(){
        return (used>size*loadFactor);
    }
    boolean isCorrespondingNarrowSize(){
        return used<size*0.1;
    }

    private int getLength(Node<K,V> node){
        int length=0;
        while (node.next!=null){
            node=node.next;
            length++;
        }
        return length;
    }

    /**
     * 找到并返回下一个不为空的头结点
     * @return 头结点 {@link Node}
     */
    Node<K,V> getMoveEntry(){


        while (movePos<size&&table[movePos]==null){
            movePos++;
        }

        if (movePos==size){
            return null;
        }else {
            Node<K,V> tempNode=table[movePos];
            table[movePos]=null;
            return tempNode;
        }


    }

    //-----------get and set-----------

    public static void main(String[] args) {
        MapObject<String,String> mapObject=new MapObject<>();
        int pos=30000;
        for (int i=0;i<pos;i++){
            mapObject.putVal("key"+i,"val"+i);
        }
        System.out.println(mapObject.used);
        for (int i=0;i<pos;i++){
            mapObject.deleteKey("key"+i);
        }
        System.out.println(mapObject.used);
    }

}

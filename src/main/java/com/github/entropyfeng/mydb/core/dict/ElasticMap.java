package com.github.entropyfeng.mydb.core.dict;

import com.github.entropyfeng.mydb.expection.OutOfBoundException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author entropyfeng
 * @date 2020/2/20 17:57
 * 最大容量为2的30次方
 */
public class ElasticMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {

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


    private MapObject<K, V> first;

    private MapObject<K, V> second;


    private boolean isRehashing = false;


    public ElasticMap(int initialCapacity, float loadFactor) {
        first = new MapObject<>(initialCapacity, loadFactor);
    }

    public ElasticMap(int initialCapacity) {
        first = new MapObject<>(initialCapacity);
    }

    public ElasticMap() {
        first = new MapObject<>();
    }

    @NotNull
    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> entries = new HashSet<>(size());
        entries.addAll(first.allEntries());
        if (isRehashing) {
            entries.addAll(second.allEntries());
        }
        return entries;
    }

    @Override
    public int size() {
        if (isRehashing) {
            moveEntry();
            return first.used + second.used;
        } else {
            return first.used;
        }
    }

    @Override
    public boolean isEmpty() {
        if (isRehashing) {
            moveEntry();
        }
        return this.size() == 0;
    }


    @Override
    public V put(K key, V value) throws OutOfBoundException {
        assert key != null && value != null;
        if (size() == MAXIMUM_CAPACITY) {
            throw new OutOfBoundException();
        }


        V resValue = null;
        if (isRehashing) {
            resValue = first.deleteKey(key);
            V resValue1 = second.put(key, value);
            if (resValue == null) {
                resValue = resValue1;
            }
            moveEntry();
        } else if (first.isCorrespondingEnlargeSize()) {
            isRehashing = true;
            second = new MapObject<>(first.used);
            //resValue always null
            second.put(key, value);
            moveEntry();
        } else {
            resValue = first.put(key, value);
        }
        return resValue;
    }


    @Nullable
    @Override
    public V putIfAbsent(K key, V value) throws IllegalArgumentException {
       return null;
    }

    public V putIfPresent(K key, V value) {
        return null;
    }

    @Override
    public V get(Object key) {

        V res = first.getValue((K)key);
        if (isRehashing) {
            if (res == null) {
                res = second.getValue(key);
            }
            moveEntry();
        }
        return res;
    }


    /**
     * 根据key获取 value
     *
     * @param key 键 notNull
     * @return null ->不存在该key或该key对应的value为空
     * notNull->value
     */
    public V dictFetchValue(K key) {
        V res = first.getValue(key);
        if (isRehashing) {
            if (res == null) {
                res = second.getValue(key);
            }
            moveEntry();
        }
        return res;
    }


    /**
     * 每次移动一个槽中的所有节点
     */
    private void moveEntry() {

        Node<K, V> node = first.getMoveEntry();
        //如果不为空则没有resize完毕
        if (node != null) {
            while (node != null) {
                first.used--;
                second.addIfAbsent(node.key, node.value);
                node = node.next;
            }
        } else if (isRehashing) {
            isRehashing = false;
            first = second;
            second = null;
        }
    }


    /**
     * forbid call this method
     *
     * @param key   key
     * @param value value
     * @return always false
     */
    @Deprecated
    @Override
    public boolean remove(Object key, Object value) throws UnsupportedOperationException {
        throw new UnsupportedOperationException();
    }



    @Override
    public V remove(Object key) {

/*        assert key != null;
        boolean res;
        if (isRehashing) {
            if (first.deleteKey(key) != null) {
                res = true;
            } else {
                res = second.deleteKey(key);
            }
            moveEntry();
        } else if (first.isCorrespondingNarrowSize()) {
            isRehashing = true;
            second = new MapObject<>(first.used);
            res = first.deleteKey(key);
            moveEntry();
        } else {
            res = first.deleteKey(key);
        }
        return res;*/

return null;
    }


    public static void main(String[] args) {
        ElasticMap<String, String> elasticMap = new ElasticMap<>();
        HashMap<String, String> hashMap = new HashMap<>();
        final int pos = 20000000;
        long first = System.currentTimeMillis();
        for (int i = 0; i < pos; i++) {
            hashMap.put(ThreadLocalRandom.current().nextInt() + "", i + "");
        }
        hashMap = null;
        long second = System.currentTimeMillis();

        for (int i = 0; i < pos; i++) {

            elasticMap.putIfAbsent(ThreadLocalRandom.current().nextInt() + "", i + "");
        }
        long third = System.currentTimeMillis();
        System.out.println(second - first);
        System.out.println(third - second);
    }
}

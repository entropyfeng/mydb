package com.github.entropyfeng.mydb.core.dict;

import com.github.entropyfeng.mydb.common.exception.ElementOutOfBoundException;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author entropyfeng
 * @date 2020/2/20 17:57
 * 最大容量为2的30次方
 */
public class ElasticMap<K, V> extends AbstractMap<K, V> implements Map<K, V> {

    /**
     * {@link ElasticMap}默认初始大小16
     */
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    /**
     * {@link ElasticMap}最大容量2的30次方
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


    @SuppressWarnings("unused")
    public ElasticMap(int initialCapacity, float loadFactor) {
        first = new MapObject<>(initialCapacity, loadFactor);
    }
@SuppressWarnings("unused")
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
            int res=first.used+second.used;
            moveEntry();
            return res;
        } else {
            return first.used;
        }
    }

    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }


    /**
     * @param key   key
     * @param value value
     * @return null->之前不存在当前key对应的值
     * @throws ElementOutOfBoundException 当当前hashMap元素个数超过{@value MAXIMUM_CAPACITY}时抛出
     */
    @Override
    public V put(@NotNull K key, @NotNull V value) throws ElementOutOfBoundException {

        if (size() == MAXIMUM_CAPACITY) {
            throw new ElementOutOfBoundException();
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
        } else {
            resValue = first.put(key, value);
        }
        return resValue;
    }


    /**
     * 根据key获取 value
     * @param key 键 notNull
     * @return null ->不存在该key或该key对应的value为空
     * notNull->value
     */
    @Override
    public V get(Object key) {

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
     * 只有在isRehashing情况下才可移动
     * 每次移动一个槽中的所有节点
     */
    private void moveEntry() {

        Node<K, V> node = first.getMoveEntry();
        //如果不为空则没有resize完毕
        if (node != null) {
            while (node != null) {
                first.used--;
                second.put(node.key, node.value);
                node = node.next;
            }
            /*
               如果 改成else{xxx}那么 可能会多次调用该函数
               会导致first=null second=null
             */
        } else if (isRehashing) {
            isRehashing = false;
            first = second;
            second = null;
        }
    }

    @Override
    public V remove(Object key) {

        V resValue=null;
        if (isRehashing){
           resValue= first.deleteKey(key);
           if (resValue==null){
               resValue=second.deleteKey(key);
           }
           moveEntry();
        }else {
            first.deleteKey(key);
        }
        return resValue;
    }

}

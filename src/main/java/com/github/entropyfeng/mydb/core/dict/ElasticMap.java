package com.github.entropyfeng.mydb.core.dict;

import io.netty.handler.codec.http.HttpServerKeepAliveHandler;
import org.jetbrains.annotations.NotNull;

import java.util.AbstractMap;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author entropyfeng
 * @date 2020/2/20 17:57
 * 最大容量为2的30次方
 */
public class ElasticMap<K, V> extends AbstractMap<K,V> {

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
        return null;
    }

    /**
     * 添加给定的键值对，若之前不存在则插入，否则不插入
     * @param key 键
     * @param value 值
     * @return true->add成功
     *         false->add失败
     */
    public boolean dictAdd(K key,V value){
        assert key!=null&&value!=null;
        if (first.used==MAXIMUM_CAPACITY){
            return false;
        }

        if (isRehashing){
            if (first.isExist(key)||second.isExist(key)){
                return false;
            }else {
                second.addVal(key, value);
                moveEntry();
                return true;
            }
        }else if(first.isCorrespondingEnlargeSize()){

            if(first.isExist(key)){
                return false;
            }else {
                isRehashing = true;
                second = new MapObject<>(first.used);
                second.addVal(key, value);
                moveEntry();
                return true;
            }

        }else {
            return first.addVal(key,value);
        }

    }

    /**
     * 插入给定的键值对,若存在则用新值替代已有值
     * @param key 键
     * @param value 值
     */
    public void dictReplace(K key, V value) {
        assert key!=null&&value!=null;

        if (isRehashing) {
            first.deleteKey(key);
            second.replaceVal(key, value);
            moveEntry();
        } else if (first.isCorrespondingEnlargeSize()) {
            isRehashing = true;
            second = new MapObject<>(first.used);
            second.replaceVal(key, value);
            moveEntry();
        } else {
            first.replaceVal(key, value);
        }

    }


    /**
     * 从字典中删除所对应的键值对
     * @param key 键
     * @return true->删除成功
     *         false->不存在该键值对
     */
    public boolean dictDelete(K key) {

        assert key != null;
        boolean res;
        if (isRehashing) {
            if (first.deleteKey(key)) {
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
     * 获取当前节点数量
     * @return 当前节点数量
     */
    public int dictGetUsed() {
        if (isRehashing) {
            moveEntry();
            return first.used + second.used;
        } else {
            return first.used;
        }
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
                second.addVal(node.key, node.value);
                node = node.next;
            }
        } else {
            isRehashing = false;
            first = second;
            second = null;
        }
    }


    public static void main(String[] args) {
        ElasticMap<String,String>  elasticMap=new ElasticMap<>();
        HashMap<String,String> hashMap=new HashMap<>();
        final  int pos=20000000;
        long first=System.currentTimeMillis();
        for (int i = 0; i <pos ; i++) {
            hashMap.put(ThreadLocalRandom.current().nextInt()+"",i+"");
        }
        hashMap=null;
        long second=System.currentTimeMillis();

        for (int i = 0; i < pos; i++) {

            elasticMap.dictAdd(ThreadLocalRandom.current().nextInt()+"",i+"");
        }
        long third=System.currentTimeMillis();
        System.out.println(second-first);
        System.out.println(third-second);
    }
}

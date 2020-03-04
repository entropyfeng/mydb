package com.github.entropyfeng.mydb.core.dict;

/**
 * @author entropyfeng
 * @date 2020/2/20 17:57
 */
public class ElasticMap<K, V> {

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


    MapObject<K, V> first;

    MapObject<K, V> second;


    boolean isRehashing = false;


    ElasticMap(int initialCapacity, float loadFactor) {
        first = new MapObject<>(initialCapacity, loadFactor);
    }

    ElasticMap(int initialCapacity) {
        first = new MapObject<>(initialCapacity);
    }

    ElasticMap() {
        first = new MapObject<>();
    }


    public void putVal(K key, V value) {
        if (isRehashing) {
            if(first.isExist(key)){
                first.deleteKey(key);
            }
            second.putVal(key, value);
            moveEntry();
        } else if (first.isCorrespondingEnlargeSize()) {
            isRehashing=true;
            second = new MapObject<>(first.used);
            second.putVal(key, value);
            moveEntry();
        } else {
            first.putVal(key, value);
        }

    }


    public boolean deleteKey(K key){

        boolean res=false;
        if(isRehashing){
            if(first.deleteKey(key)){
                res=true;
            }else {
               res= second.deleteKey(key);
            }
            moveEntry();
        }else if (first.isCorrespondingNarrowSize()){
            isRehashing=true;
            second=new MapObject<>(first.used);
            res= first.deleteKey(key);
            moveEntry();
        }else {
            res=first.deleteKey(key);
        }
        return res;
    }

    /**
     * 根据key获取 value
     *
     * @param key 键
     * @return null ->不存在该key或该key对应的value为空
     * notNull->value
     */
    public V getValue(K key) {
        V res = first.getValue(key);
        if (isRehashing) {
            if (res == null) {
                res = second.getValue(key);
            }
            moveEntry();
        }
        return res;
    }

    public int getUsed(){
        if (isRehashing){
            moveEntry();
            return first.used+second.used;
        }else {
            return first.used;
        }
    }

    private void moveEntry() {

        Node<K, V> node = first.getMoveEntry();
        //如果不为空则没有resize完毕
        if (node != null) {
            while (node != null) {
                first.used--;
                second.putVal(node.key, node.value);
                node = node.next;
            }
        } else {
            isRehashing = false;
            first = second;
            second = null;
        }
    }



    public static void main(String[] args) {

        ElasticMap<String,String> elasticMap=new ElasticMap<>();
        int pos=3000;
        for (int i=0;i<pos;i++){
            elasticMap.putVal("key"+i,"val"+i);
        }

        for (int i=0;i<pos;i++){
            elasticMap.getValue("");
        }

        for (int i=0;i<pos;i++){
            elasticMap.deleteKey("key"+i);
        }

        System.out.println(elasticMap.first.size);
        System.out.println(elasticMap.getUsed());
    }

}

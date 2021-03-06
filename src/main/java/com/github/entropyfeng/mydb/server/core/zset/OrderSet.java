package com.github.entropyfeng.mydb.server.core.zset;


import com.google.common.base.Objects;

import java.util.HashMap;
import java.util.List;

/**
 * @author entropyfeng
 */
public class OrderSet<T extends Comparable<T>> {


    private SkipList<T> skipList;
    private HashMap<T, Double> hashMap;


    public OrderSet() {
        skipList = new SkipList<>();
        hashMap = new HashMap<>();
    }


    public boolean add(T value, double score) {
        if (hashMap.containsKey(value)) {
            return false;
        } else {
            hashMap.put(value, score);
            skipList.insertNode(value, score);
            return true;
        }
    }

    public boolean delete(T value, double score) {
        if (exists(value, score)) {
            hashMap.remove(value);
            skipList.deleteValue(value, score);
            return true;
        }
        return false;
    }

    public boolean delete(T value) {

        if (exists(value)) {
            return delete(value, hashMap.get(value));
        } else {
            return false;
        }
    }

    public int delete(double begin, double end) {

        List<T> res = range(begin, end);

        res.forEach(v -> delete(v, hashMap.get(v)));
        return res.size();
    }

    public boolean exists(T value, double score) {
        Double val = hashMap.get(value);
        if (val == null) {
            return false;
        } else {
            return Double.compare(val, score) == 0;
        }
    }

    public boolean exists(T value) {
        return hashMap.containsKey(value);
    }

    /**
     * [begin,end]闭区间
     *
     * @param begin 开始分值
     * @param end   结束分值
     * @return {@link List}
     */
    public List<T> range(double begin, double end) {
        return skipList.range(begin, end);
    }


    public int count(double begin, double end) {
        return skipList.rangeSize(begin, end);
    }


    public int size() {
        return hashMap.size();
    }



    public void clear() {
        skipList.clear();
        hashMap.clear();
    }



    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof OrderSet)){
            return false;
        }
        OrderSet<?> orderSet = (OrderSet<?>) o;
        return Objects.equal(hashMap, orderSet.hashMap);
    }

    public HashMap<T, Double> getHashMap() {
        return hashMap;
    }
}

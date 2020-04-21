package com.github.entropyfeng.mydb.core.zset;


import java.util.HashMap;
import java.util.List;

/**
 * @author entropyfeng
 */
public class OrderSet<T extends Comparable<T>> {


    private SkipList<T> skipList;
    private HashMap<T,Double> hashMap;


    public OrderSet(){
        skipList=new SkipList<T>();
        hashMap=new HashMap<>();
    }


    public boolean add(T value,double score){
        if (hashMap.containsKey(value)){
            return false;
        }else {
            hashMap.put(value,score);
            skipList.insertNode(value, score);
            return true;
        }
    }
    public boolean delete(T value,double score){
        if (exists(value, score)){
            hashMap.remove(value);
            skipList.deleteValue(value, score);
            return true;
        }
        return false;
    }
    public boolean exists(T value,double score){
      Double val=  hashMap.get(value);
      if (val==null){
          return false;
      }else {
          return Double.compare(val, score) == 0;
      }
    }
    public boolean exists(T value){
        return hashMap.containsKey(value);
    }

    /**
     * [begin,end]闭区间
     * @param begin 开始分值
     * @param end 结束分值
     * @return {@link List}
     */
    public List<T> range(double begin ,double end){
        return skipList.range(begin, end);
    }

    public int deleteRange(double begin ,double end){

        return 0;
    }

    public int size(){
        return skipList.size();
    }

}

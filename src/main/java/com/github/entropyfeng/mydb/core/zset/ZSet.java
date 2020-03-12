package com.github.entropyfeng.mydb.core.zset;

import com.github.entropyfeng.mydb.core.dict.ElasticMap;

import java.util.HashMap;

public class ZSet<T extends Comparable<T>> {


    private SkipList<T> skipList;
    private HashMap<T,Double> hashMap;

    public ZSet(){
        skipList=new SkipList<T>();
        hashMap=new HashMap<>();
    }

}

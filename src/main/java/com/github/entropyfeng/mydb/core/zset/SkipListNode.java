package com.github.entropyfeng.mydb.core.zset;


/**
 * @author entropyfeng
 * @date 2020/3/4 14:07
 */

class SkipListNode<T extends Comparable<T>>  {

    T value;
    double score;

    /**
     * @param value 值 当且仅当为头结点时 value为null 此时对应的 height 为默认最大高度
     * @param height 数组最大高度
     */
    @SuppressWarnings("unchecked")
    SkipListNode(T value,int height){
        this.value=value;
        level=new SkipListNode[height];
    }
    @SuppressWarnings("unchecked")
    SkipListNode(T value,double score,int height){
        this.value=value;
        this.score=score;
        level=new SkipListNode[height];
    }
    SkipListNode<T> []level;
}

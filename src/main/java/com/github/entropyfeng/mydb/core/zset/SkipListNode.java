package com.github.entropyfeng.mydb.core.zset;


/**
 * @author entropyfeng
 * @date 2020/3/4 14:07
 */

class SkipListNode<T extends Comparable<T>>  {

    T value;
    double score;
    SkipListNode<T> []level;
    SkipListNode<T> back;
    /**
     * @param value 值 当且仅当为头结点时 value为null 此时对应的 height 为默认最大高度
     * @param height 数组最大高度
     * @param score 分值
     */
    @SuppressWarnings("unchecked")
    SkipListNode(T value,double score,int height){
        this.value=value;
        this.score=score;
        this.level=new SkipListNode[height];
    }

}

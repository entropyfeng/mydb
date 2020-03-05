package com.github.entropyfeng.mydb.core.list;

/**
 * @author entropyfeng
 * @date 2020/3/4 15:09
 */
class SkipListLevel<T extends Comparable> {
    int span;
    SkipListNode<T> forward;

    SkipListLevel(){
    }
}

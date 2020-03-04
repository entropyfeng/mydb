package com.github.entropyfeng.mydb.core.list;

/**
 * @author entropyfeng
 * @date 2020/3/4 14:07
 */
class SkipListNode<T extends Comparable>  {

    SkipListNode backward;
    T value;
    int count;
    SkipListNode(T value,int height){
        count=1;
        this.value=value;
        level=new SkipListLevel[height];
    }

    SkipListLevel<T> []level;

    public static void main(String[] args) {
      SkipListNode skipListNode=  new SkipListNode<String>("haha",32);

        System.out.println();
    }

}

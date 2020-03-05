package com.github.entropyfeng.mydb.core.list;


import java.util.ArrayList;

/**
 * @author entropyfeng
 * @date 2020/3/4 14:07
 */
class SkipListNode<T extends Comparable> implements Comparable<SkipListNode<T>>  {

    SkipListNode backward;
    T value;
    int count;
    SkipListNode(T value,int height){
        count=1;
        this.value=value;
        level=new SkipListLevel[height];
        for (int i=0;i<level.length;i++){

            level[i]=new SkipListLevel<>();
        }

    }

    ArrayList<SkipListLevel<T>> levels;
    SkipListLevel<T> []level;

    public static void main(String[] args) {
      SkipListNode skipListNode=  new SkipListNode<String>("haha",32);

        ArrayList<String> arrayList=new ArrayList<>();
        System.out.println();
    }




    @Override
    public int compareTo(SkipListNode<T> obj) {

        if (value==null){
            return -1;
        }else {
            return value.compareTo(obj.value);
        }

    }
}

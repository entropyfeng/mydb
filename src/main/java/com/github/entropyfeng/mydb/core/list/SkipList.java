package com.github.entropyfeng.mydb.core.list;

import com.github.entropyfeng.mydb.core.Pair;
import com.github.entropyfeng.mydb.util.CommonUtil;
import com.sun.istack.internal.NotNull;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Comparator;


/**
 * @author entropyfeng
 * @date 2019/12/27 20:34
 */
public class SkipList<T extends Comparable> {

    private SkipListNode<T> header;
  //  private SkipListNode<T> tail;
    /**
     * 链表长度
     */
    private long length;
    /**
     * 元素个数
     */
    private long counts;

    /**
     * 当前跳表最大高度
     */
    private int maxLevel;

    public SkipList() {
        header = new SkipListNode<T>(null, 32);
       // tail = header;
        length = 0;
        maxLevel = 0;
    }

    /**
     * @param value 需要查找的值 notNull
     * @return 该value存在的个数
     */
    public int findValue(T value) {
        assert value != null;

        SkipListNode<T> tempNode = findNode(value);
        if (tempNode == null) {
            return 0;
        } else {
            return tempNode.count;
        }
    }

    /**
     * 查询是否存在该value
     *
     * @param value 需要查找的值 notNull
     * @return true->存在
     * false->不存在
     */
    public boolean isExist(T value) {
        assert value != null;
        return findValue(value) > 0;
    }

    /**
     * 查找value对应结点
     *
     * @param value notNull
     * @return {@link SkipListNode}
     */
    private SkipListNode<T> findNode(T value) {
        assert value != null;

      /*  if (counts == 0) {
            return null;
        }*/
        SkipListNode<T> tempNode = header;

        for (int i = maxLevel - 1; i >= 0; i--) {
            while (tempNode.level[i]!= null &&compare(tempNode.level[i].value,value)<0) {
                tempNode = tempNode.level[i];
            }

        }
        tempNode = tempNode.level[0];

        if(tempNode==null){
            return null;
        }else if(compare(tempNode.value,value)==0) {
            return tempNode;
        }else {
            return null;
        }


    }


    @SuppressWarnings("unchecked")
    private int compare(T first,T second){
        if(first==null){
            return -1;
        }else if(second==null){
            return 1;
        }else {
            return first.compareTo(second);
        }
    }

    public void insertNode( T value) {

        SkipListNode<T> newNode = findNode(value);
        if (newNode != null) {
            newNode.count++;
            this.counts++;
        } else {
            SkipListNode<T> tempNode = header;
            final int currentLevel = CommonUtil.getLevel();
            newNode = new SkipListNode<T>(value, currentLevel);
            maxLevel = maxLevel > currentLevel ? maxLevel : currentLevel;
            length++;
            counts++;


            for (int i = maxLevel - 1; i >= 0; i--) {
                while (tempNode.level[i] != null
                        && compare(tempNode.level[i].value,value)<0) {
                    tempNode = tempNode.level[i];
                }

                if (i < currentLevel) {
                    newNode.level[i] = tempNode.level[i];
                    tempNode.level[i] = newNode;
                }

            }
        }


    }

    public ArrayList<Pair<T,Integer>> allValues(){
        ArrayList<Pair<T,Integer>> res=new ArrayList<Pair<T,Integer>>();

        SkipListNode<T> tempNode=header.level[0];
        while (tempNode!=null){
            res.add(new Pair<>(tempNode.value,tempNode.count));
            tempNode=tempNode.level[0];
        }
        if (res.size()==0){
            return null;
        }else {
            return res;
        }
    }
    public static void main(String[] args) {

        SkipList<BigInteger> bigIntegerSkipList = new SkipList<>();

        for (int i=0;i<100;i++){
            bigIntegerSkipList.insertNode(new BigInteger(i%3+""));
        }

        bigIntegerSkipList.insertNode(null);
        bigIntegerSkipList.allValues().forEach(System.out::println);

    }
}


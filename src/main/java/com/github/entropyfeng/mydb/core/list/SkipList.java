package com.github.entropyfeng.mydb.core.list;

import com.github.entropyfeng.mydb.util.CommonUtil;

import java.math.BigInteger;
import java.util.Comparator;


/**
 * @author entropyfeng
 * @date 2019/12/27 20:34
 */
public class SkipList<T extends Comparable> {

    private SkipListNode<T> header;
    private SkipListNode<T> tail;
    /**
     * 链表长度
     */
    private long length;
    /**
     * 元素个数
     */
    private long counts;

    private int maxLevel;

    public SkipList() {
        header = new SkipListNode<T>(null, 32);
        tail = header;
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
            while (tempNode.level[i].forward != null &&tempNode.compareTo(tempNode.level[i].forward)<0) {
                tempNode = tempNode.level[i].forward;
            }

        }
        tempNode = tempNode.level[0].forward;

        if(tempNode==null){
            return null;
        }else if(tempNode.value.compareTo(value)==0) {
            return tempNode;
        }else {
            return null;
        }


    }

    private int compare(T first,T second){
        if(first==null){
            return -1;
        }else {
            return first.compareTo(second);
        }
    }
    public void insertNode(T value) {

        assert value != null;

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
                        && tempNode.level[i].forward != null
                        && tempNode.compareTo(tempNode.level[i].forward)<0) {
                    tempNode = tempNode.level[i].forward;
                }

                if (i < currentLevel) {
                    newNode.level[i].forward = tempNode.level[i].forward;
                    tempNode.level[i].forward = newNode;
                }

            }
        }


    }

    public static void main(String[] args) {

        SkipList<BigInteger> bigIntegerSkipList = new SkipList<>();
        bigIntegerSkipList.insertNode(new BigInteger("1"));
        bigIntegerSkipList.findNode(new BigInteger("1"));
        System.out.println();
    }
}


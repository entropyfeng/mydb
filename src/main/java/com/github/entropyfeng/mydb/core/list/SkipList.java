package com.github.entropyfeng.mydb.core.list;

import com.github.entropyfeng.mydb.util.CommonUtil;

import java.math.BigInteger;
import java.util.LinkedList;

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
     * @param value 需要查找的值
     * @return 该value存在的个数
     */
    public int findValue(T value) {
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
     * @param value 需要查找的值
     * @return true->存在
     * false->不存在
     */
    public boolean isExist(T value) {
        return findValue(value) > 0;
    }

    private SkipListNode<T> findNode(T value) {
        SkipListNode<T> tempNode = header;

        for (int i = maxLevel - 1; i >= 0; i--) {
            while (tempNode.level[i].forward != null && tempNode.value.compareTo(tempNode.level[i].forward.value) < 0) {
                tempNode = tempNode.level[i].forward;
            }

        }
        tempNode = tempNode.level[0].forward;
        if (tempNode.value.compareTo(value) == 0) {
            return tempNode;
        } else {
            return null;
        }
    }

    public void insertNode(T value) {

        SkipListNode<T> newNode = findNode(value);
        if (newNode != null) {
            newNode.count++;
            this.counts++;
        } else {
            SkipListNode<T> tempNode = header;
            newNode = new SkipListNode<T>(value, CommonUtil.getLevel());
            maxLevel = maxLevel > newNode.level.length ? maxLevel : newNode.level.length;
            length++;
            counts++;

            for (int i = maxLevel - 1; i >= 0; i--) {
                while (tempNode.level[i].forward != null && tempNode.value.compareTo(tempNode.level[i].forward.value) < 0) {
                    tempNode = tempNode.level[i].forward;
                }

                if (i < newNode.level.length) {
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


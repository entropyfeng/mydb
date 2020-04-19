package com.github.entropyfeng.mydb.core.zset;

import com.github.entropyfeng.mydb.core.helper.Pair;
import com.github.entropyfeng.mydb.util.CommonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author entropyfeng
 * @date 2019/12/27 20:34
 */
public class SkipList<T extends Comparable<T>> {

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

    /**
     * 当前跳表最大高度
     */
    private int maxLevel;

    public SkipList() {
        header = new SkipListNode<T>(null, Double.NEGATIVE_INFINITY, 32);
        length = 0;
        maxLevel = 0;
    }


    /**
     * 查找value对应结点
     *
     * @param value notNull
     * @return {@link SkipListNode}
     */
    private SkipListNode<T> findNode(T value) {
        assert value != null;
        SkipListNode<T> tempNode = header;
        for (int i = maxLevel - 1; i >= 0; i--) {
            while (tempNode.level[i] != null && compare(tempNode.level[i].value, value) < 0) {
                tempNode = tempNode.level[i];
            }
        }
        tempNode = tempNode.level[0];
        if (tempNode == null) {
            return null;
        } else if (compare(tempNode.value, value) == 0) {
            return tempNode;
        } else {
            return null;
        }
    }

    /**
     * 前提一定存在该节点
     * 获取value 与 score 所对应的节点
     * @param value 值
     * @param score 分值
     * @return {@link SkipListNode}
     */
    private SkipListNode<T> getNode(T value, double score) {
        SkipListNode<T> tempNode = header;
        for (int i = maxLevel - 1; i >= 0; i--) {
            while (tempNode.level[i] != null && IsFirstLessThanSecond(tempNode.level[i].score, score, tempNode.level[i].value, value)) {
                tempNode = tempNode.level[i];
            }
        }
        return tempNode.level[0];
    }

    /**
     * 比较大小
     *
     * @param first  {@link Comparable}
     * @param second {@link Comparable}
     * @return 0 ->equal
     * -1-> first less than second
     * 1 -> first greater than second
     */
    private int compare(T first, T second) {
        if (first != null && second != null) {
            return first.compareTo(second);
        } else if (first == null && second != null) {
            return -1;
        } else if (first != null) {
            return 1;
        } else {
            return 0;
        }
    }


    /**
     * 如果分值不同，比较分值大小
     * 如果分值相同，比较对象value
     *
     * @param first  {@link SkipListNode}
     * @param second {@link SkipListNode}
     * @return true->first less than second strictly
     * false->first more than second strictly
     */
    private boolean IsFirstLessThanSecond(SkipListNode<T> first, SkipListNode<T> second) {
        if (first.score != second.score) {
            return first.score < second.score;
        } else {
            return compare(first.value, second.value) < 0;
        }
    }

    private boolean IsFirstLessThanSecond(double firstScore, double secondScore, T firstValue, T secondValue) {
        if (firstScore != secondScore) {
            return firstScore < secondScore;
        } else {
            return compare(firstValue, secondValue) < 0;
        }
    }

    /**
     * 将包含给定成员和分值的新节点添加到跳表中
     * 假设无重复value在跳表中
     *
     * @param value 值
     * @param score 分值
     */
    public void zslInsert(T value, double score) {

        assert value != null;

        SkipListNode<T> tempNode = header;
        final int currentLevel = CommonUtil.getLevel();
        SkipListNode<T> newNode = new SkipListNode<T>(value, score, currentLevel);
        maxLevel = Math.max(maxLevel, currentLevel);
        length++;

        for (int i = maxLevel - 1; i >= 0; i--) {
            while (tempNode.level[i] != null && IsFirstLessThanSecond(tempNode.level[i].score, score, tempNode.level[i].value, value)) {
                tempNode = tempNode.level[i];
            }
            if (i < currentLevel) {
                newNode.level[i] = tempNode.level[i];
                tempNode.level[i] = newNode;
            }
        }
    }

    public List<T> getValues(double score){
        SkipListNode<T> tempNode=header;
        for (int i=maxLevel-1;i>=0;i--){
            while (tempNode.level[i]!=null&&tempNode.level[i].score<score){
                tempNode=tempNode.level[i];
            }
        }
        tempNode=tempNode.level[0];
        List<T> resList=new ArrayList<>();
        while (tempNode!=null&&tempNode.score==score){
            resList.add(tempNode.value);
            tempNode=tempNode.level[0];
        }
        return resList;
    }



    /**
     * 前提一定存在该value和其对应的score
     * @param value 值
     * @param score 分值
     */
    public void zslDelete(T value, double score) {
        final int currentLevel = getNode(value, score).level.length;
        SkipListNode<T> tempNode = header;
        for (int i = maxLevel - 1; i >= 0; i--) {
            while (tempNode.level[i] != null && IsFirstLessThanSecond(tempNode.level[i].score, score, tempNode.value, value)) {
                tempNode = tempNode.level[i];
            }
            if (i < currentLevel) {
                tempNode.level[i] = tempNode.level[i].level[i];
            }
        }
    }


    public int deleteValue(T value) {
        assert value != null;
        SkipListNode<T> node = findNode(value);
        if (node == null) {
            return 0;
        } else {
            int currentLevel = node.level.length;
            SkipListNode<T> tempNode = header;
            for (int i = maxLevel - 1; i >= 0; i--) {
                while (tempNode.level[i] != null && compare(tempNode.level[i].value, value) < 0) {
                    tempNode = tempNode.level[i];
                }
                if (i < currentLevel) {
                    tempNode.level[i] = tempNode.level[i].level[i];
                }
            }
        }
        return 0;
    }


    public ArrayList<Pair<T, Double>> allValues() {
        ArrayList<Pair<T, Double>> res = new ArrayList<Pair<T, Double>>();

        SkipListNode<T> tempNode = header.level[0];
        while (tempNode != null) {
            res.add(new Pair<>(tempNode.value, tempNode.score));
            tempNode = tempNode.level[0];
        }
        return  res;
    }

    public static void main(String[] args) {



    }
}


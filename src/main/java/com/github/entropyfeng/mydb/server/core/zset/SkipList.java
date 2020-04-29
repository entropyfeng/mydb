package com.github.entropyfeng.mydb.server.core.zset;

import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.util.CommonUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @author entropyfeng
 * @date 2019/12/27 20:34
 * a score-> multi value
 * an value ->score
 * not exists multi equals value
 */
public class SkipList<T extends Comparable<T>> {

    private static final int MAX_LEVEL = 32;
    private SkipListNode<T> header;

    private SkipListNode<T> tail;
    /**
     * 链表长度
     */
    private int size;

    /**
     * 当前跳表最大高度
     * restrict max height is 32
     */
    private int maxLevel;

    private void init(){
        //头结点默认值最小
        header = new SkipListNode<>(null, Double.NEGATIVE_INFINITY, 32);
        //尾结点默认最大
        tail = new SkipListNode<>(null, Double.MAX_VALUE, 32);
        size = 0;
        maxLevel = 0;

        for (int i = 0; i < MAX_LEVEL; i++) {
            header.level[i] = tail;
        }

        tail.back = header;
    }
    public SkipList() {

        init();
    }

    /**
     * 前提一定存在该节点
     * 获取value 与 score 所对应的节点
     *
     * @param value 值
     * @param score 分值
     * @return {@link SkipListNode}
     */
    private @NotNull SkipListNode<T> getNode(@NotNull T value, double score) {
        SkipListNode<T> tempNode = header;
        for (int i = maxLevel - 1; i >= 0; i--) {
            while (tempNode.level[i] != tail && isFirstLessThanSecond(tempNode.level[i].score, score, tempNode.level[i].value, value)) {
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
    private int compareValue(T first, T second) {
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
     * @param firstScore  第一个分值
     * @param secondScore 第二个分值
     * @param firstValue  第一个值
     * @param secondValue 第二个值
     * @return true->first less than second strictly
     * false->first more than second strictly
     */
    private boolean isFirstLessThanSecond(double firstScore, double secondScore, T firstValue, T secondValue) {
        int res = Double.compare(firstScore, secondScore);
        if (res == 0) {
            return compareValue(firstValue, secondValue) < 0;
        } else {
            return res < 0;
        }
    }

    /**
     * 将包含给定成员和分值的新节点添加到跳表中
     * 假设无重复value在跳表中
     *
     * @param value 值
     * @param score 分值
     */
    public void insertNode(@NotNull T value, double score) {

        SkipListNode<T> tempNode = header;
        final int currentLevel = CommonUtil.getLevel();
        SkipListNode<T> newNode = new SkipListNode<>(value, score, currentLevel);
        maxLevel = Math.max(maxLevel, currentLevel);
        size++;
        for (int i = maxLevel - 1; i >= 0; i--) {
            while (tempNode.level[i] != tail && isFirstLessThanSecond(tempNode.level[i].score, score, tempNode.level[i].value, value)) {
                tempNode = tempNode.level[i];
            }
            if (i < currentLevel) {
                newNode.level[i] = tempNode.level[i];
                tempNode.level[i] = newNode;
            }
        }
        newNode.level[0].back = newNode;
        newNode.back = tempNode;
    }


    /**
     * @param begin score
     * @param end   score
     * @return {@link List} 即使没有与score 对应的值，将返回空列表
     */
    public @NotNull List<T> range(double begin, double end) {

        SkipListNode<T> tempNode = header;

        for (int i = maxLevel - 1; i >= 0; i--) {
            while (tempNode.level[i] != tail && tempNode.level[i].score < begin) {
                tempNode = tempNode.level[i];
            }
        }
        tempNode = tempNode.level[0];
        List<T> resList = new ArrayList<>();
        while (tempNode != tail && tempNode.score <= end) {
            resList.add(tempNode.value);
            tempNode = tempNode.level[0];
        }
        return resList;
    }

    public boolean inRange(double begin, double end) {
        return header.level[0].score<=begin&&tail.back.score>=end;
    }
    public int rangeSize(double begin,double end){

        SkipListNode<T> tempNode = header;

        for (int i = maxLevel - 1; i >= 0; i--) {

            while (tempNode.level[i] != tail && tempNode.level[i].score < begin) {
                tempNode = tempNode.level[i];
            }
        }
        tempNode = tempNode.level[0];
        int resSize=0;
        while (tempNode != tail && tempNode.score <= end) {
            resSize++;
            tempNode = tempNode.level[0];
        }
        return resSize;
    }

    /**
     * 该list一定存在 value
     *
     * @param value 值
     */
    public void deleteValue(@NotNull T value, double score) {

        int currentLevel = getNode(value, score).level.length;
        SkipListNode<T> tempNode = header;
        for (int i = maxLevel - 1; i >= 0; i--) {
            while (tempNode.level[i] != tail && compareValue(tempNode.level[i].value, value) < 0) {
                tempNode = tempNode.level[i];
            }
            if (i < currentLevel) {
                tempNode.level[i] = tempNode.level[i].level[i];
            }
        }
        size--;
        tempNode.level[0].back = tempNode;
    }


    public int size() {
        return size;
    }

    public ArrayList<Pair<T, Double>> allValues() {
        ArrayList<Pair<T, Double>> res = new ArrayList<>();
        SkipListNode<T> tempNode = header.level[0];
        while (tempNode != tail) {
            res.add(new Pair<>(tempNode.value, tempNode.score));
            tempNode = tempNode.level[0];
        }
        return res;
    }

    public void  clear(){
        init();
    }
}


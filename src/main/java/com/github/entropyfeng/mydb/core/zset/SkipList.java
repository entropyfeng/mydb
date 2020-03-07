package com.github.entropyfeng.mydb.core.zset;

import com.github.entropyfeng.mydb.core.Pair;
import com.github.entropyfeng.mydb.util.CommonUtil;
import com.google.common.hash.Hashing;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;


/**https://blog.csdn.net/u013366617/article/details/83618361?depth_1-utm_source=distribute.pc_relevant.none-task&utm_source=distribute.pc_relevant.none-task
 * https://zhuanlan.zhihu.com/p/98803430
 * @author entropyfeng
 * @date 2019/12/27 20:34
 */
public class SkipList<T extends Comparable> {

    private SkipListNode<T> header;


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
    private SkipListNode<T> findNode(@NotNull T value) {
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
     * 比较大小
     * @param first {@link Comparable}
     * @param second {@link Comparable}
     * @return 0 ->equal
     *         -1-> first less than second
     *         1 -> first greater than second
     */
    @SuppressWarnings("unchecked")
    private int compare(T first, T second) {
        if (first == null) {
            return -1;
        } else if (second == null) {
            return 1;
        } else {
            return first.compareTo(second);
        }
    }

    public void insertNode(@NotNull T value) {

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
                while (tempNode.level[i] != null && compare(tempNode.level[i].value, value) < 0) {
                    tempNode = tempNode.level[i];
                }

                if (i < currentLevel) {
                    newNode.level[i] = tempNode.level[i];
                    tempNode.level[i] = newNode;
                }
            }
        }

    }
    public int deleteValue(T value){

        assert value!=null;

      SkipListNode<T> node= findNode(value);

      if (node==null){
          return 0;
      }else {

          int currentLevel=node.level.length;
          SkipListNode<T> tempNode=header;
          for (int i=maxLevel-1;i>=0;i--){
              while (tempNode.level[i] != null && compare(tempNode.level[i].value, value) < 0) {
                  tempNode = tempNode.level[i];
              }
              if (i<currentLevel){
                  tempNode.level[i]=tempNode.level[i].level[i];
              }

          }
      }

      return 0;
    }

    public ArrayList<Pair<T, Integer>> allValues() {
        ArrayList<Pair<T, Integer>> res = new ArrayList<Pair<T, Integer>>();

        SkipListNode<T> tempNode = header.level[0];
        while (tempNode != null) {
            res.add(new Pair<>(tempNode.value, tempNode.count));
            tempNode = tempNode.level[0];
        }
        if (res.size() == 0) {
            return null;
        } else {
            return res;
        }
    }

    public static void main(String[] args) {


        SkipList<Integer>integerSkipList=new SkipList<>();


        int max=100000;
        int pow=100;
       long beginInsert=  System.currentTimeMillis();
        for (int i = 0; i < max; i++) {
            integerSkipList.insertNode(Hashing.murmur3_32().hashInt(i).asInt());
        }
        long endInsert=System.currentTimeMillis();
        System.out.println(endInsert-beginInsert);
        for (int i = 0; i < max*pow; i++) {
            integerSkipList.findNode(Hashing.murmur3_32().hashInt(i).asInt());
        }
        long endQuery=System.currentTimeMillis();
        System.out.println(endQuery-endInsert);


        SortedSet<Integer> sortedSet=new TreeSet<>();
        long beginInsert1=System.currentTimeMillis();

        for (int i = 0; i < max; i++) {
           sortedSet.add(Hashing.murmur3_32().hashInt(i).asInt());
        }
        long endInsert1=System.currentTimeMillis();
        System.out.println(endInsert1-beginInsert1);
        for (int i = 0; i < max*pow; i++) {
            sortedSet.contains(Hashing.murmur3_32().hashInt(i).asInt());
        }
        long endQuery1=System.currentTimeMillis();
        System.out.println(endQuery1-endInsert1);
        //integerSkipList.allValues().forEach(System.out::println);
    }
}


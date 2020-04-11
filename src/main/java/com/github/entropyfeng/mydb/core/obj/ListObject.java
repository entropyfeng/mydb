package com.github.entropyfeng.mydb.core.obj;

import com.github.entropyfeng.mydb.common.ops.ListOperations;
import com.github.entropyfeng.mydb.util.TimeUtil;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author entropyfeng
 */
public class ListObject extends BaseObject implements ListOperations {

    private final HashMap<String, LinkedList<TurtleValue>> listMap;

    public ListObject() {
        super();
        this.listMap = new HashMap<>();
    }


    @Override
    public Integer size() {

        return listMap.size();
    }

    @Override
    public Integer sizeOf(String string) {
        List<TurtleValue> res = listMap.get(string);
        return res == null ? 0 : res.size();
    }

    @Override
    public void leftPush(String key, TurtleValue value) {
        createIfNotExist(key);
        listMap.get(key).addFirst(value);
    }

    @Override
    public void leftPush(String key, TurtleValue value, long time) {
        if (!TimeUtil.isExpire(time)) {

        }
    }

    @Override
    public void leftPushAll(String key, Collection<TurtleValue> values) {
        createIfNotExist(key);
        listMap.get(key).addAll(0, values);
    }

    @Override
    public Boolean leftPushIfPresent(String key, TurtleValue value) {
        if (listMap.containsKey(key)) {
            listMap.get(key).addFirst(value);

            return true;
        }
        return false;
    }

    @Override
    public Boolean leftPushIfAbsent(String key, TurtleValue value) {
        if (!listMap.containsKey(key)) {
            listMap.get(key).addFirst(value);
            return true;
        }
        return false;
    }

    @Override
    public void rightPush(String key, TurtleValue value) {
        createIfNotExist(key);
        listMap.get(key).addLast(value);
    }

    @Override
    public void rightPushAll(String key, Collection<TurtleValue> values) {
        createIfNotExist(key);
        listMap.get(key).addAll(values);
    }


    @Override
    public Boolean rightPushIfPresent(String key, TurtleValue value) {
        if (listMap.containsKey(key)) {
            listMap.get(key).addLast(value);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Boolean rightPushIfAbsent(String key, TurtleValue value) {
        if (!listMap.containsKey(key)) {
            listMap.get(key).addLast(value);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public TurtleValue leftPop(String key) {
        if (listMap.containsKey(key)) {
            return listMap.get(key).pollFirst();
        }
        return null;
    }

    @Override
    public TurtleValue left(String key) {
        if (listMap.containsKey(key)) {
            return listMap.get(key).peekFirst();
        }
        return null;
    }

    @Override
    public TurtleValue rightPop(String key) {
        if (listMap.containsKey(key)) {
            return listMap.get(key).pollLast();
        }
        return null;
    }


    @Override
    public TurtleValue right(String key) {
        if (listMap.containsKey(key)) {
            return listMap.get(key).peekLast();
        }
        return null;
    }

    @Override
    public void clear(String key) {
        listMap.remove(key);
    }

    @Override
    public void clearAll() {
        listMap.clear();
    }

    @Override
    public Boolean exist(String key) {
        return listMap.get(key) != null;
    }

    @Override
    public Boolean exist(String key, TurtleValue value) {
        List<TurtleValue> list = listMap.get(key);
        if (list != null) {
            return list.contains(value);
        }
        return false;
    }


    private void createIfNotExist(String key) {
        if (!listMap.containsKey(key)) {
            listMap.put(key, new LinkedList<>());
        }
    }

}

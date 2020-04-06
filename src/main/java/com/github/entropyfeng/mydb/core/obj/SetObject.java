package com.github.entropyfeng.mydb.core.obj;

import com.github.entropyfeng.mydb.common.ops.SetOperations;
import jdk.nashorn.internal.ir.CallNode;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author entropyfeng
 */
public class SetObject  extends BaseObject implements SetOperations {
    private HashMap<String, HashSet<TurtleValue>> setHashMap;

    public SetObject(HashMap<String, HashSet<TurtleValue>> setHashMap) {
        this.setHashMap = setHashMap;
    }

    @Override
    public Boolean exist(String key) {
        return setHashMap.containsKey(key);
    }

    @Override
    public Boolean exist(String key, TurtleValue value) {

        HashSet<TurtleValue> set=setHashMap.get(key);
        return set != null && set.contains(value);

    }

    @Override
    public void add(String key, TurtleValue value) {
      createIfNotExists(key);
      Set<TurtleValue> set=setHashMap.get(key);
      set.remove(value);
      set.add(value);
    }

    @Override
    public Collection<TurtleValue> union(String key, String otherKey) {

        createIfNotExists(key);
        createIfNotExists(otherKey);
        setHashMap.get(key).addAll(setHashMap.get(otherKey));
        return setHashMap.get(key);
    }

    @Override
    public Collection<TurtleValue> union(String key, Collection<TurtleValue> turtleValues) {
        createIfNotExists(key);
        setHashMap.get(key).addAll(turtleValues);
        return setHashMap.get(key);
    }

    @Override
    public Collection<TurtleValue> intersect(String key, String otherKey) {
        createIfNotExists(key);
        createIfNotExists(otherKey);
        setHashMap.get(key).retainAll(setHashMap.get(key));
        return setHashMap.get(key);
    }

    @Override
    public Collection<TurtleValue> intersect(String key, Collection<TurtleValue> turtleValues) {
        createIfNotExists(key);
        setHashMap.get(key).retainAll(turtleValues);
        return setHashMap.get(key);
    }

    @Override
    public Collection<TurtleValue> difference(String key, String otherKey) {
        createIfNotExists(key);
        createIfNotExists(otherKey);
        setHashMap.get(key).removeAll(setHashMap.get(otherKey));
        return setHashMap.get(key);
    }

    @Override
    public Collection<TurtleValue> difference(String key, Collection<TurtleValue> turtleValues) {
        createIfNotExists(key);
        setHashMap.get(key).removeAll(turtleValues);
        return setHashMap.get(key);
    }

    @Override
    public Collection<TurtleValue> entries(String key) {
        return setHashMap.get(key);
    }

    private void createIfNotExists(String key){
        setHashMap.computeIfAbsent(key, k -> new HashSet<>());
    }
}

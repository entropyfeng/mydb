package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.core.StringLongPair;
import com.github.entropyfeng.mydb.util.TimeUtil;

import java.util.*;

public class ExpireHandler {

    private Map<String, Long> expireMap;
    private PriorityQueue<StringLongPair> expireQueue;

    private Map<String,Long> accessTimesMap;
    public ExpireHandler() {
        expireMap = new HashMap<>();
        expireQueue = new PriorityQueue<>();
    }

    protected boolean putExpire(String key, long time) {
        if (!expireMap.containsKey(key)) {
            expireMap.put(key, time);
            expireQueue.add(new StringLongPair(key, time));
            return true;
        }
        return false;

    }

    public boolean isExpire(String key) {
        return TimeUtil.isExpire(expireMap.get(key));
    }

    public boolean deleteExpire(String key) {

        if (expireMap.containsKey(key)) {
            expireMap.remove(key);
            expireQueue.removeIf(stringLongPair -> stringLongPair.getKey().equals(key));
            return true;
        }
        return false;
    }

    public boolean resetExpire(String key, long time) {
        if (expireMap.containsKey(key)) {
            expireMap.remove(key);
            expireQueue.stream().filter(stringLongPair -> stringLongPair.getKey().equals(key)).findFirst().ifPresent(stringLongPair -> stringLongPair.setValue(time));
            return true;
        }
       return false;
    }

    protected void clearExpire(Map<String, Object> map) {
        final long currentTime = TimeUtil.currentTime();
        String peek;
        while (expireQueue.peek() != null && expireQueue.peek().getValue() <= currentTime) {
            StringLongPair stringLongPair = expireQueue.poll();
            assert stringLongPair != null;
            peek = stringLongPair.getKey();
            expireMap.remove(peek);
            map.remove(peek);
        }
    }

}

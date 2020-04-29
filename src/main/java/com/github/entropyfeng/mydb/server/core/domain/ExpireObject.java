package com.github.entropyfeng.mydb.server.core.domain;

import com.github.entropyfeng.mydb.util.TimeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;

/**
 * @author entropyfeng
 */
public class ExpireObject {
    /**
     * 过期字典，用于查看该String 是否设定过期
     */
    private Map<String, Long> expireMap;
    /**
     * 过期队列，按照过期时间排序
     */
    private PriorityQueue<StringLongPair> expireQueue;

    public ExpireObject() {
        expireMap = new HashMap<>();
        expireQueue = new PriorityQueue<>();
    }

    public ExpireObject(Map<String, Long> expireMap) {
        this.expireMap = expireMap;
        expireQueue = new PriorityQueue<>();
    }

    /**
     * 设置该键值对将在 time 时过期
     *
     * @param key  {@link String}
     * @param time 毫秒时间
     */
    protected void putExpireTime(String key, long time) {
        if (!expireMap.containsKey(key)) {
            expireMap.put(key, time);
            expireQueue.add(new StringLongPair(key, time));
        }
    }

    /**
     * make the compare between the key's mapping time and current time.
     * if the key-->time < current time,the key is expire;
     * otherwise the key is not expire.
     * if the key is not existing,the key is not expire too.
     * @param key {@link String} is not null
     * @return true->expire ;false->not expire;
     */
    public boolean isExpire(@NotNull String key) {
        Long res = expireMap.get(key);
        if (res == null) {
            return false;
        } else {
            return TimeUtil.isExpire(res);
        }
    }

    /**
     * 删除key对应的过期时间
     *
     * @param key {@link String}
     */
    public void deleteExpireTime(String key) {
        if (expireMap.containsKey(key)) {
            expireMap.remove(key);
            expireQueue.removeIf(stringLongPair -> stringLongPair.getKey().equals(key));
        }
    }

    /**
     * 若过期字典中存在，则重新设置，否则不予设置
     *
     * @param key  {@link String}
     * @param time 毫秒时间戳
     */
    public void resetExpireTime(String key, long time) {
        if (expireMap.containsKey(key)) {
            expireMap.remove(key);
            expireQueue.stream().filter(stringLongPair -> stringLongPair.getKey().equals(key)).findFirst().ifPresent(stringLongPair -> stringLongPair.setValue(time));
        }
    }

    protected void clearExpireTime(Map<String, ?> map) {
        final long currentTime = TimeUtil.currentTime();
        String peek;
        while (expireQueue.peek() != null && expireQueue.peek().getValue() <= currentTime) {
            //弹出队列头结点，若弹出前队列为空，则返回null;但此时stringLongPair一定不为空
            StringLongPair stringLongPair = expireQueue.poll();
            peek = Objects.requireNonNull(stringLongPair).getKey();
            expireMap.remove(peek);
            map.remove(peek);
        }
    }

    public void clearExpireObject(){
        expireMap.clear();
        expireQueue.clear();
    }
    public Map<String, Long> getExpireMap() {
        return expireMap;
    }
}

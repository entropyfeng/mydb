package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.core.Pair;
import com.github.entropyfeng.mydb.core.StringLongPair;
import com.github.entropyfeng.mydb.core.dict.ElasticMap;
import com.github.entropyfeng.mydb.util.TimeUtil;

import java.util.*;

public class ExpireHandler {

    private Map<String, Long> expireMap;
    private PriorityQueue<StringLongPair> expireQueue;
    public ExpireHandler(){
        expireMap=new HashMap<>();
        expireQueue=new PriorityQueue<>();
    }

    public boolean putExpire(String key,long time){
        if(!expireMap.containsKey(key)){
            expireMap.put(key,time);
            expireQueue.add(new StringLongPair(key,time));
            return true;
        }
        return false;

    }
    public boolean isExpire(String key){
      return TimeUtil.isExpire(expireMap.get(key));
    }
    public boolean deleteExpire(String key){

        if(expireMap.containsKey(key)){
            expireMap.remove(key);
            expireQueue.remove(key);
        }

    }
    public void resetExpire(String key,long time){

    }

    public void clearExpire(Map<String,Object> map) {
        final long currentTime=TimeUtil.currentTime();
        String peek;
        while (expireQueue.peek()!=null&&expireQueue.peek().getValue()<=currentTime){
            StringLongPair  stringLongPair=expireQueue.poll();
            assert stringLongPair != null;
            peek= stringLongPair.getKey();
            expireMap.remove(peek);
            map.remove(peek);
        }

    }

}

package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.util.TimeUtil;

import java.util.HashMap;

public class ValueObject {


    HashMap<String,Object> objectMap=new HashMap<>();

    HashMap<String,Long> expireMap=new HashMap<>();

   long preRequestTime;

    public Object get(String key){

        if(TimeUtil.isExpire(expireMap.get(key))){
            expireMap.remove(key);
            objectMap.remove(key);
        }
        preRequestTime=TimeUtil.currentTime();
        return objectMap.get(key);
    }

    public boolean remove(String key){
        expireMap.remove(key);
        return objectMap.remove(key) != null;
    }

    public boolean expireAt(String key,long time){
        if(objectMap.get(key)!=null){

        }
    }

    public void set(String key,Object value,long time){
        objectMap.put(key,value);
        if(time!=0){
            expireMap.put(key,time);
        }
        preRequestTime=TimeUtil.currentTime();
    }

    public  void setIfPresent(String key,Object value,long time){

    }

    public void setIfAbsent(String key,Object value,long time){


    }

    public static void main(String[] args) {
        ValueObject valueObject=new ValueObject();
        valueObject.objectMap=new HashMap<>();


    }
}

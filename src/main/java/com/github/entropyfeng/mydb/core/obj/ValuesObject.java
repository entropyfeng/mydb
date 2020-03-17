package com.github.entropyfeng.mydb.core.obj;
import com.github.entropyfeng.mydb.server.ExpireHandler;
import com.github.entropyfeng.mydb.util.TimeUtil;
import java.util.HashMap;

public class ValuesObject extends ExpireHandler {
    private HashMap<String,ValueObject> valueMap;
    public ValueObject get(String key){
        if(isExpire(key)){
            deleteExpire(key);
            valueMap.remove(key);
            return null;
        }
        return valueMap.get(key);
    }

    public boolean remove(String key){
        deleteExpire(key);
        return valueMap.remove(key) != null;
    }


    public void set(String key,ValueObject value,long time){
        valueMap.put(key,value);
        if(!TimeUtil.isExpire(time)){
            putExpire(key, time);
        }
    }

    public  void setIfPresent(String key,ValueObject value,long time){
        if(isExpire(key)){
            deleteExpire(key);
            valueMap.remove(key);
        }
        if (valueMap.containsKey(key)){
            set(key, value, time);
        }
    }

    public void setIfAbsent(String key,ValueObject value,long time){
        if(isExpire(key)){
            deleteExpire(key);
            valueMap.remove(key);
        }
        if(!valueMap.containsKey(key)){
            set(key, value, time);
        }
    }

}

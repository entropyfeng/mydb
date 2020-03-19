package com.github.entropyfeng.mydb.core.obj;
import com.github.entropyfeng.mydb.server.ExpireHandler;
import com.github.entropyfeng.mydb.util.TimeUtil;
import java.util.HashMap;

public class ValuesObject extends ExpireHandler {
    private HashMap<String,ValueObject> valueMap;
    public ValueObject get(String key){
        removeExpireKey(key);
        return valueMap.get(key);
    }

    public boolean remove(String key){
        deleteExpire(key);
        return valueMap.remove(key) != null;
    }

    public boolean isExist(String key){
        removeExpireKey(key);
        return valueMap.containsKey(key);
    }

    public void set(String key,ValueObject value,long time){
        valueMap.put(key,value);
        if(!TimeUtil.isExpire(time)){
            putExpire(key, time);
        }
    }

    public  void setIfPresent(String key,ValueObject value,long time){
        removeExpireKey(key);
        if (valueMap.containsKey(key)){
            set(key, value, time);
        }
    }

    public void setIfAbsent(String key,ValueObject value,long time){
        removeExpireKey(key);
        if(!valueMap.containsKey(key)){
            set(key, value, time);
        }
    }

    private boolean removeExpireKey(String key){
        if(isExpire(key)){
            deleteExpire(key);
            valueMap.remove(key);

            return true;
        }
        return false;
    }

    public void append(String key,String value)throws UnsupportedOperationException{
        removeExpireKey(key);
        ValueObject valueObject=valueMap.get(key);
        valueMap.replace(key,valueObject.append(value));
    }
    public boolean increment(String key,long longValue)throws UnsupportedOperationException{
        removeExpireKey(key);
        ValueObject valueObject=valueMap.get(key);
        if(valueObject!=null){
            valueObject.increment(longValue);
            return true;
        }
        return false;
    }
    public boolean increment(String key,double doubleValue){
        return false;
    }
}

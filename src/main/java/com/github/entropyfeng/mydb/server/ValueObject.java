package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.util.TimeUtil;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.Time;
import java.util.HashMap;
import java.util.Optional;

public class ValueObject extends ExpireHandler {


    HashMap<String,Object> valueMap =new HashMap<>();

    public Object get(String key){
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


    public void set(String key,Object value,long time){
        valueMap.put(key,value);
        if(!TimeUtil.isExpire(time)){
            putExpire(key, time);
        }
    }

    public  void setIfPresent(String key,Object value,long time){
        if (valueMap.containsKey(key)){
            set(key, value, time);
        }

    }

    public void setIfAbsent(String key,Object value,long time){
        if(!valueMap.containsKey(key)){
            set(key, value, time);
        }
    }

    public void append(String key,String value)throws UnsupportedOperationException{
       Object object= get(key);
       if (object instanceof String){
           valueMap.replace(key,object+value);
       }
    }
    public boolean increment(String key,double value)throws UnsupportedOperationException{
        Object object=get(key);
        if (object instanceof Double){
            valueMap.replace(key,value+(Double) object);
        }else if(object instanceof Float){
            valueMap.replace(key,value+(Float)object);
        }else if(object instanceof BigDecimal){
            valueMap.replace(key, ((BigDecimal) object).add(BigDecimal.valueOf(value)));
        }
        return false;
    }
    public boolean increment(String key,long value){

        return false;
    }
    public boolean increment(String key,String value){

        return false;
    }

}

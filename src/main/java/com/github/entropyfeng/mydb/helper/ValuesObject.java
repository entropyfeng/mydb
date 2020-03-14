package com.github.entropyfeng.mydb.helper;

import com.google.common.hash.BloomFilter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;

/**
 * @author entropyfeng
 * @date 2020/3/5 20:22
 */
public class ValuesObject {

    HashMap<String,StringBuilder> stringMap;

    HashMap<String,Integer> integerMap;

    HashMap<String,Double> doubleMap;

    HashMap<String,Float> floatMap;

    HashMap<String,Long> longMap;

    HashMap<String,Long> expireMap;


    void  xxx(){
        StringBuffer stringBuffer;
        BigInteger bigInteger= new BigInteger("fsdf");
    }

    public static void main(String[] args) {
        new ValuesObject().xxx();
    }


}

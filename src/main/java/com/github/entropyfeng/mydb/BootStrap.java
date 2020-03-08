package com.github.entropyfeng.mydb;

import com.github.entropyfeng.mydb.core.dict.ElasticMap;
import com.google.common.hash.BloomFilter;
import io.netty.bootstrap.Bootstrap;
import io.netty.handler.codec.http.HttpServerKeepAliveHandler;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;

/**
 * @author entropyfeng
 * @date 2019/12/27 13:34
 */
public class BootStrap {

    public void opsValue(){

    }
    public void opsHash(){

        HashMap<String,ElasticMap<String,String>> stringStringMap;
        HashMap<String,ElasticMap<String,Integer>> stringIntegerMap;
        HashMap<String,ElasticMap<String,Double>> stringDoubleMap;
        HashMap<String,ElasticMap<String,Float>> stringFloatMap;
        HashMap<String,ElasticMap<String,BigInteger>> stringBigIntegerMap;
        HashMap<String,ElasticMap<String,BigDecimal>> stringBigDecimalMap;



    }
    public static void constructMemory(){






    }

    public static void main(String[] args) {

        HashMap<String,Long> expireDic=new HashMap<>();

    }
}

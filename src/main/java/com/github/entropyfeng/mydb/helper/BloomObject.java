package com.github.entropyfeng.mydb.helper;

import com.google.common.hash.BloomFilter;

import java.util.HashMap;

/**
 * @author entropyfeng
 * @date 2020/3/5 20:31
 */
public class BloomObject {
    HashMap<String,BloomFilter<String>> stringBloomFilterHashMap;

    HashMap<String,BloomFilter<Integer>> integerBloomFilterHashMap;

    HashMap<String,BloomFilter<Double>>  doubleBloomFilterHashMap;

    HashMap<String,BloomFilter<Float>>   floatBloomFilterHashMap;
}

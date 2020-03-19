package com.github.entropyfeng.mydb.config;

/**
 * 占4位
 * @author entropyfeng
 */
public enum SupportObject {
    /**
     * 值操作
     * 编码0000
     */
    VALUE,
    /**
     * Hash操作
     * 编码0001
     */
    HASH,
    /**
     * 集合操作
     * 编码0010
     */
    SET,
    /**
     * 有序集合操作
     * 编码0011
     */
    ZSET,
    /**
     * 列表操作
     * 编码0100
     */
    LIST
}

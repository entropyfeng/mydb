package com.github.entropyfeng.mydb.config;

import com.github.entropyfeng.mydb.core.SupportValue;

/**
 * 占4位
 * @author entropyfeng
 */
public enum SupportObject {
    /**
     * 值操作
     * 编码0000
     */
    VALUE((byte) 0),
    /**
     * Hash操作
     * 编码0001
     */
    HASH((byte) 1),
    /**
     * 集合操作
     * 编码0010
     */
    SET((byte) 2),
    /**
     * 有序集合操作
     * 编码0011
     */
    ZSET((byte) 3),
    /**
     * 列表操作
     * 编码0100
     */
    LIST((byte) 4);

    private byte type;
    private SupportObject(byte type){
        this.type=type;
    }
    public byte toType(){
        return type;
    }
    public static SupportObject getSupportObjectByType(byte type)throws IllegalArgumentException{
        for (SupportObject o : SupportObject.values()) {
            if (o.type == type) {
                return o;
            }
        }
        throw new IllegalArgumentException("not exists type ->"+type);
    }
}

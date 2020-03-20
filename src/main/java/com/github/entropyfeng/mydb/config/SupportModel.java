package com.github.entropyfeng.mydb.config;

/**
 * 占4位
 * @author entropyfeng
 */
public enum SupportModel {
    /**
     * 通用模式
     */
    COMMON((byte)0),
    /**
     * 具体模式
     */
    CONCRETE((byte)1),
    /*
     * 管理模式
     */
    ADMIN((byte)2);
    private byte type;
    private SupportModel(byte type){
        this.type=type;
    }
    public byte toType(){
        return type;
    }
    public static SupportModel getSupportModelByType(byte type)throws IllegalArgumentException{
        for (SupportModel o : SupportModel.values()) {
            if (o.type == type) {
                return o;
            }
        }
        throw new IllegalArgumentException("not exists type ->"+type);
    }
}

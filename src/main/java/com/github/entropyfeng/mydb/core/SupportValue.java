package com.github.entropyfeng.mydb.core;

/**
 * @author entropyfeng
 */
public enum  SupportValue {
    /**
     * {@link String}
     */
    STRING((byte) 0),
    /**
     * {@link Integer}
     */
    INTEGER((byte)1),
    /**
     * {@link Double}
     */
    DOUBLE((byte)2),
    /**
     * {@link Long}
     */
    LONG((byte)3),
    /**
     * {@link java.math.BigInteger}
     */
    BIG_INTEGER((byte)4),
    /**
     * {@link java.math.BigDecimal}
     */
    BIG_DECIMAL((byte)5);

    private byte type;
    private SupportValue(byte type){
        this.type = type;
    }
    public static SupportValue getSupportValueByType(byte type)throws IllegalArgumentException {
        for (SupportValue c : SupportValue.values()) {
            if (c.type == type) {
                return c;
            }
        }
        throw new IllegalArgumentException("not exists type ->"+type);
    }
    public byte toType(){
        return type;
    }

}

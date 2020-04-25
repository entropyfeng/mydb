package com.github.entropyfeng.mydb.common;

import com.github.entropyfeng.mydb.core.TurtleValue;

/**
 * @see TurtleValue 仅支持以下6种类型
 * @author entropyfeng
 */
public enum TurtleValueType {

    /**
     * {@link byte[]}
     */
    BYTES((byte) 0),
    /**
     * {@link Integer}
     */
    INTEGER((byte)1),

    /**
     * {@link Long}
     */
    LONG((byte)2),

    /**
     * {@link Double}
     */
    DOUBLE((byte)3),

    /**
     * {@link java.math.BigInteger}
     */
    NUMBER_INTEGER((byte)4),

    /**
     * {@link java.math.BigDecimal}
     */
    NUMBER_DECIMAL((byte)5);

   TurtleValueType(byte value){
        this.value=value;
    }
    private byte value;

    public byte getValue() {
        return value;
    }
    public static TurtleValueType construct(byte value){
        switch (value){
            case 0:return BYTES;
            case 1:return INTEGER;
            case 2:return LONG;
            case 3:return DOUBLE;
            case 4:return NUMBER_INTEGER;
            default:return NUMBER_DECIMAL;
        }
    }
}

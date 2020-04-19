package com.github.entropyfeng.mydb.common;

import com.github.entropyfeng.mydb.core.TurtleValue;

/**
 * @see TurtleValue 仅支持以下6种类型
 * @author entropyfeng
 */
public enum TurtleValueType {

    /**
     * {@link String}
     */
    STRING,
    /**
     * {@link Integer}
     */
    INTEGER,

    /**
     * {@link Long}
     */
    LONG,

    /**
     * {@link Double}
     */
    DOUBLE,

    /**
     * {@link java.math.BigInteger}
     */
    NUMBER_INTEGER,

    /**
     * {@link java.math.BigDecimal}
     */
    NUMBER_DECIMAL

}

package com.github.entropyfeng.mydb.core;

import com.github.entropyfeng.mydb.util.BytesUtil;
import io.netty.buffer.ByteBuf;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;

/**
 * 同时支持
 * {@link String}0
 * {@link Integer}1
 * {@link Long}2
 * {@link Double}3
 * {@link BigInteger}4
 * {@link BigDecimal}5
 * {@link Byte[]}6
 * 7种编码
 *
 * @author entropyfeng
 */
public class CompressValue {

    byte[] values;

    public CompressValue() {
        values = new byte[1];
    }

    public CompressValue(String value) {
        values = BytesUtil.rightMove1(value.getBytes());
        stringType();
    }

    public CompressValue(Integer integer) {
        values = new byte[5];
        BytesUtil.extraIntToBytes(integer, values);
        integerType();
    }

    public CompressValue(Long longValue) {
        values = new byte[9];
        BytesUtil.extraLongToBytes(longValue, values);
        longType();
    }

    public CompressValue(Double doubleValue) {
        values = new byte[9];
        BytesUtil.extraLongToBytes(Double.doubleToLongBits(doubleValue), values);
        doubleType();
    }

    public CompressValue(BigInteger bigInteger) {
        byte[] bytes = bigInteger.toByteArray();
        values = BytesUtil.rightMove1(bytes);
        numberIntegerType();
    }

    public CompressValue(BigDecimal bigDecimal) {
        byte[] bytes = bigDecimal.toPlainString().getBytes();
        values = BytesUtil.rightMove1(bytes);
        numberDecimalType();
    }

    public void append(String value)throws UnsupportedOperationException {
        if (isStringType()){
           this.values= BytesUtil.concat(values,value.getBytes());
        }else {
            throw new UnsupportedOperationException("unSupport append operation");
        }
    }

    public void increment(Integer integer){
        if (!isStringType()){

        }
    }

    private void stringType() {
        values[0] = 0;
    }

    private boolean isStringType() {
        return values[0] == 0;
    }

    private void integerType() {
        values[0] = 1;
    }

    private boolean isIntegerType() {
        return values[0] == 1;
    }

    private void longType() {
        values[0] = 2;
    }

    private boolean isLongType() {
        return values[0] == 3;
    }

    private void doubleType() {
        values[0] = 3;
    }

    private boolean isDoubleType() {
        return values[0] == 4;
    }

    private void numberIntegerType() {
        values[0] = 4;
    }

    private boolean isNumberDecimalType() {
        return values[0] == 5;
    }

    private void numberDecimalType() {
        values[0] = 5;
    }

    public static enum Type {
        /**
         * 0
         */
        STING,
        /**
         * 1
         */
        INTEGER,
        /**
         * 2
         */
        LONG,
        /**
         * 3
         */
        DOUBLE,
        /**
         * 4
         */
        NUMBER_INTEGER,
        /**
         * 5
         */
        NUMBER_DECIMAL;
    }
}

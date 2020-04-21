package com.github.entropyfeng.mydb.core;

import com.github.entropyfeng.mydb.common.CommonConstant;
import com.github.entropyfeng.mydb.common.TurtleValueType;
import com.github.entropyfeng.mydb.common.exception.TurtleDesignError;
import com.github.entropyfeng.mydb.common.exception.TurtleValueElementOutBoundsException;
import com.github.entropyfeng.mydb.util.BytesUtil;
import com.github.entropyfeng.mydb.util.CommonUtil;
import com.google.common.hash.Hashing;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.Objects;


import static com.github.entropyfeng.mydb.util.BytesUtil.*;
import static com.github.entropyfeng.mydb.util.BytesUtil.bytesToInt;


/**
 * 同时支持
 * {@link String}
 * {@link Integer}
 * {@link Long}
 * {@link Double}
 * {@link BigInteger}
 * {@link BigDecimal}
 * 6种编码
 * @author entropyfeng
 */
public class TurtleValue implements Comparable<TurtleValue>{
    private TurtleValueType type;
    private byte[] values;

    public TurtleValueType getType() {
        return type;
    }

    public byte[] getValues() {
        return values;
    }

    public TurtleValue(byte[] bytes, TurtleValueType type) {
        this.type = type;
        this.values = bytes;
    }

    public TurtleValue(String value){
        Objects.requireNonNull(value);
        if(value.length()> CommonConstant.MAX_STRING_LENGTH){
            throw new TurtleValueElementOutBoundsException();
        }
        this.type=TurtleValueType.STRING;
        this.values=value.getBytes();
    }
    public TurtleValue(int value) {
        this(BytesUtil.allocate4(value), TurtleValueType.INTEGER);
    }

    public TurtleValue(double value) {
        this(BytesUtil.allocate8(value), TurtleValueType.DOUBLE);
    }


    public TurtleValue(long value) {
        this(BytesUtil.allocate8(value), TurtleValueType.LONG);
    }

    public TurtleValue(BigInteger value) {
        this(value.toByteArray(), TurtleValueType.NUMBER_INTEGER);
    }

    public TurtleValue(BigDecimal value) {
        this(value.toPlainString().getBytes(), TurtleValueType.NUMBER_DECIMAL);
    }

    public void append(String value) throws UnsupportedOperationException {
        if (this.type == TurtleValueType.STRING) {
            this.values = CommonUtil.mergeBytes(this.values, value.getBytes());
        } else {
            throw new UnsupportedOperationException("unSupport append operation on" + type.toString());
        }
    }

    public void increment(long longValue) throws UnsupportedOperationException {

        switch (type) {
            case LONG:
                longAdd(this.values, longValue);
                return;
            case INTEGER:
                handleLong(longValue+bytesToInt(this.values));
                return;
            case NUMBER_INTEGER:
                handleBigInteger(toBigInteger(this).add(BigInteger.valueOf(longValue)));
                return;
            case NUMBER_DECIMAL:
                handleBigDecimal(toBigDecimal(this).add(BigDecimal.valueOf(longValue)));
                return;
            default:
                throw new UnsupportedOperationException("unSupport append operation on" + type.toString());
        }
    }

    public void increment(int intValue) throws UnsupportedOperationException {
        switch (type) {
            case INTEGER:
                intAdd(this.values, intValue);
               return;
            case LONG:
                longAdd(this.values, intValue);
                return;
            case NUMBER_INTEGER:
                handleBigInteger(toBigInteger(this).add(BigInteger.valueOf(intValue)));
               return;
            case DOUBLE:
            case NUMBER_DECIMAL:
                handleBigDecimal(toBigDecimal(this).add(BigDecimal.valueOf(intValue)));
               return;
            default:
                throw new UnsupportedOperationException("unSupport append operation on" + type.toString());
        }
    }

    public void increment(BigInteger bigInteger) throws UnsupportedOperationException {

        switch (type) {
            case LONG:
            case INTEGER:
            case NUMBER_INTEGER:
                handleBigInteger(bigInteger.add(toBigInteger(this)));
               return;
            case NUMBER_DECIMAL:
            case DOUBLE:
                handleBigDecimal(toBigDecimal(this).add(new BigDecimal(bigInteger)));
               return;
            default:
                throw new UnsupportedOperationException("unSupport append operation on" + type.toString());
        }
    }


    public void increment(double doubleValue) throws UnsupportedOperationException {
        switch (type) {
            case DOUBLE:
                doubleAdd(this.values, doubleValue);
               return;
            case LONG:
            case INTEGER:
            case NUMBER_INTEGER:
            case NUMBER_DECIMAL:
                handleBigDecimal(toBigDecimal(this).add(BigDecimal.valueOf(doubleValue)));
               return;
            default:
                throw new UnsupportedOperationException("unSupport append operation on" + type.toString());
        }
    }

    public void increment(BigDecimal bigDecimal){
        switch (type){
            case DOUBLE:
            case INTEGER:
            case LONG:
            case NUMBER_DECIMAL:
            case NUMBER_INTEGER:handleBigDecimal(toBigDecimal(this).add(bigDecimal));return;
            default:throw  new UnsupportedOperationException("unSupport append operation on" + type.toString());
        }
    }

    public Object toObject(){
        switch (type){
            case NUMBER_INTEGER:return new BigInteger(values);
            case LONG:return BytesUtil.bytesToLong(values);
            case INTEGER:return BytesUtil.bytesToInt(values);
            case DOUBLE:return BytesUtil.bytesToDouble(values);
            case NUMBER_DECIMAL:return new BigDecimal(new String(values));
            case STRING:return new String(values);
            default:throw new TurtleDesignError("design error");
        }
    }
    private void handleBigDecimal(BigDecimal bigDecimal) {
        this.values = bigDecimal.toPlainString().getBytes();
        this.type = TurtleValueType.NUMBER_DECIMAL;
    }

    private void handleBigInteger(BigInteger bigInteger) {
        this.values = bigInteger.toByteArray();
        this.type = TurtleValueType.NUMBER_INTEGER;
    }

    private void handleLong(long longValue) {
        this.values = BytesUtil.allocate8(longValue);
        this.type = TurtleValueType.LONG;
    }


    private static BigDecimal toBigDecimal(TurtleValue turtleValue) throws UnsupportedOperationException {
        switch (turtleValue.type) {
            case DOUBLE:
                return BigDecimal.valueOf(ByteBuffer.wrap(turtleValue.values).getDouble());
            case NUMBER_INTEGER:
                return new BigDecimal(new BigInteger(turtleValue.values));
            case INTEGER:
                return new BigDecimal(ByteBuffer.wrap(turtleValue.values).getInt());
            case LONG:
                return new BigDecimal(ByteBuffer.wrap(turtleValue.values).getLong());
            case NUMBER_DECIMAL:
                return new BigDecimal(new String(turtleValue.values));
            case STRING:
            default:
                throw new UnsupportedOperationException("unSupport operation " + turtleValue.type.toString());
        }
    }


    private static BigInteger toBigInteger(TurtleValue turtleValue) throws UnsupportedOperationException {
        switch (turtleValue.type) {
            case INTEGER:
                return BigInteger.valueOf(ByteBuffer.wrap(turtleValue.values).getInt());
            case LONG:
                return BigInteger.valueOf(ByteBuffer.wrap(turtleValue.values).getLong());
            case NUMBER_INTEGER:
                return new BigInteger(turtleValue.values);
            default:
                throw new UnsupportedOperationException("unSupport operation " + turtleValue.type.toString());
        }
    }
    private static void doubleAdd(byte[] bytes, double doubleValue) {
        doubleToBytes(bytes, bytesToDouble(bytes) + doubleValue);
    }

    /**
     * @param bytes long type
     * @param longValue long value
     */
    private static void longAdd(byte[] bytes, long longValue) {
        longToBytes(bytesToLong(bytes) + longValue,bytes);
    }

    /**
     * @param bytes int type
     * @param intValue int value
     */
    private static void intAdd(byte[] bytes, int intValue) {
        intToBytes(bytesToInt(bytes) + intValue,bytes);
    }

    @Override
    public boolean equals(Object o) {

        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TurtleValue that = (TurtleValue) o;
        return type == that.type &&
                Arrays.equals(values, that.values);
    }

    @Override
    @SuppressWarnings("all")
    public int hashCode() {
      return   Hashing.murmur3_32().hashBytes(values).asInt();
    }

    @Override
    public int compareTo(@NotNull TurtleValue o) {

        if (this.type==o.type){
            switch (this.type){
                case INTEGER:return Integer.compare((Integer) this.toObject(),(Integer) o.toObject());
                case DOUBLE:return Double.compare((Double)this.toObject(),(Double)this.toObject());
                case LONG:return Long.compare((Long)this.toObject(),(Long)this.toObject());
                case NUMBER_INTEGER:return ((BigInteger)this.toObject()).compareTo((BigInteger) o.toObject());
                case NUMBER_DECIMAL:return ((BigDecimal)this.toObject()).compareTo((BigDecimal)o.toObject());
                case STRING:return ((String)o.toObject()).compareTo((String)o.toObject());
                default:return 0;
            }
        }
        return 0;

    }
}

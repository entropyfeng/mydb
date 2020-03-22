package com.github.entropyfeng.mydb.core.obj;

import com.github.entropyfeng.mydb.common.CommonConstant;
import com.github.entropyfeng.mydb.config.Constant;
import com.github.entropyfeng.mydb.core.SupportValue;
import com.github.entropyfeng.mydb.expection.OutOfBoundException;
import com.github.entropyfeng.mydb.helper.BloomObject;
import com.github.entropyfeng.mydb.util.BytesUtil;
import com.github.entropyfeng.mydb.util.CommonUtil;
import com.google.common.base.Objects;
import com.google.common.hash.BloomFilter;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;

import static com.github.entropyfeng.mydb.core.SupportValue.LONG;
import static com.github.entropyfeng.mydb.util.BytesUtil.*;

/**
 * @author entropyfeng
 */
public class TurtleObject implements Serializable, Cloneable, Comparable<TurtleObject> {
    private byte[] values;
    private SupportValue type;

    private TurtleObject(String value) throws OutOfBoundException {
        if (value.length() * 2 >= CommonConstant.MAX_STRING_LENGTH) {
            throw new OutOfBoundException();
        }
        this.type = SupportValue.STRING;
        this.values = value.getBytes();
    }

    private TurtleObject(byte[] values, SupportValue type) {
        this.type = type;
        this.values = values;
    }


    public TurtleObject(int value) {
        this(BytesUtil.allocate4(value), SupportValue.INTEGER);
    }

    public TurtleObject(double value) {
        this(BytesUtil.allocate8(value), SupportValue.DOUBLE);
    }


    public TurtleObject(long value) {
        this(BytesUtil.allocate8(value), LONG);
    }

    public TurtleObject(BigInteger value) {
        this(value.toByteArray(), SupportValue.BIG_INTEGER);
    }

    public TurtleObject(BigDecimal value) {
        this(value.toPlainString().getBytes(), SupportValue.BIG_DECIMAL);
    }

    public void append(String value) throws UnsupportedOperationException {
        if (this.type == SupportValue.STRING) {
            this.values = CommonUtil.mergeBytes(this.values, value.getBytes());
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public void increment(double doubleValue) throws UnsupportedOperationException {
        switch (type) {
            case DOUBLE:
                doubleAdd(this.values, doubleValue);
                break;
            case LONG:
            case INTEGER:
            case BIG_INTEGER:
            case BIG_DECIMAL:
                handleBigDecimal(toBigDecimal(this).add(BigDecimal.valueOf(doubleValue)));
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    private void handleBigDecimal(BigDecimal bigDecimal) {
        this.values = bigDecimal.toPlainString().getBytes();
        this.type = SupportValue.BIG_DECIMAL;
    }

    private void handleBigInteger(BigInteger bigInteger) {
        this.values = bigInteger.toByteArray();
        this.type = SupportValue.BIG_INTEGER;
    }

    private void handleLong(long longValue) {
        this.values = BytesUtil.allocate8(BytesUtil.bytesToInt(this.values) + longValue);
        this.type = SupportValue.LONG;
    }

    public void increment(long longValue) throws UnsupportedOperationException {

        switch (type) {
            case LONG:
                longAdd(this.values, longValue);
                break;
            case INTEGER:
                handleLong(longValue);
                break;
            case BIG_INTEGER:
                handleBigInteger(toBigInteger(this).add(BigInteger.valueOf(longValue)));
                break;
            case BIG_DECIMAL:
                handleBigDecimal(toBigDecimal(this).add(BigDecimal.valueOf(longValue)));
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public void increment(int intValue) throws UnsupportedOperationException {
        switch (type) {
            case INTEGER:
                intAdd(this.values, intValue);
                break;
            case LONG:
                longAdd(this.values, intValue);
                break;
            case BIG_INTEGER:
                handleBigInteger(toBigInteger(this).add(BigInteger.valueOf(intValue)));
                break;
            case DOUBLE:
            case BIG_DECIMAL:
                handleBigDecimal(toBigDecimal(this).add(BigDecimal.valueOf(intValue)));
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public void increment(BigInteger bigInteger) throws UnsupportedOperationException {

        switch (type) {
            case LONG:
            case INTEGER:
            case BIG_INTEGER:
                handleBigInteger(bigInteger.add(toBigInteger(this)));
                break;
            case BIG_DECIMAL:
            case DOUBLE:
                handleBigDecimal(toBigDecimal(this).add(new BigDecimal(bigInteger)));
                break;
            default:
                throw new UnsupportedOperationException();
        }
    }

    public void increment(BigDecimal bigDecimal) {
        handleBigDecimal(toBigDecimal(this).add(bigDecimal));
    }

    private static BigDecimal toBigDecimal(TurtleObject turtleObject) throws UnsupportedOperationException {
        switch (turtleObject.type) {
            case DOUBLE:
                return BigDecimal.valueOf(ByteBuffer.wrap(turtleObject.values).getDouble());
            case BIG_INTEGER:
                return new BigDecimal(new BigInteger(turtleObject.values));
            case INTEGER:
                return new BigDecimal(ByteBuffer.wrap(turtleObject.values).getInt());
            case LONG:
                return new BigDecimal(ByteBuffer.wrap(turtleObject.values).getLong());
            case BIG_DECIMAL:
                return new BigDecimal(new String(turtleObject.values));
            case STRING:
            default:
                throw new UnsupportedOperationException();
        }
    }


    private static BigInteger toBigInteger(TurtleObject turtleObject) throws UnsupportedOperationException {
        switch (turtleObject.type) {
            case INTEGER:
                return BigInteger.valueOf(ByteBuffer.wrap(turtleObject.values).getInt());
            case LONG:
                return BigInteger.valueOf(ByteBuffer.wrap(turtleObject.values).getLong());
            case BIG_INTEGER:
                return new BigInteger(turtleObject.values);
            default:
                throw new UnsupportedOperationException();
        }
    }

    public Object toObject() {
        switch (type) {
            case DOUBLE:
                return ByteBuffer.wrap(values).getDouble();
            case LONG:
                return ByteBuffer.wrap(values).getLong();
            case INTEGER:
                return ByteBuffer.wrap(values).getInt();
            case BIG_DECIMAL:
                return new BigDecimal(new String(values));
            case BIG_INTEGER:
                return new BigInteger(values);
            default:
                return new String(values);
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TurtleObject that = (TurtleObject) o;
        return Objects.equal(values, that.values) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(values, type);
    }

    @Override
    protected TurtleObject clone() throws CloneNotSupportedException {
        super.clone();
        return new TurtleObject(Arrays.copyOf(this.values, this.values.length), SupportValue.valueOf(type.name()));
    }

    @Override
    public int compareTo(@NotNull TurtleObject o) {
        return 0;
    }


    public static void main(String[] args) {


        TurtleObject turtleObject = new TurtleObject("hello world");
        byte[] bytes = turtleObject.toByte();
        turtleObject = TurtleObject.toValueObject(bytes);
        System.out.println(turtleObject.type);
    }

    public byte[] toByte() {
        return ByteBuffer.allocate(5 + values.length).put((byte) (type.ordinal())).putInt(values.length).put(values).array();
    }


    public static TurtleObject toValueObject(byte[] bytes) throws IllegalArgumentException {
        if (bytes.length >= 5) {
            byte typeByte = bytes[0];
            int valueSize = BytesUtil.bytesToInt(bytes[1], bytes[2], bytes[3], bytes[4]);
            if (valueSize + 5 == bytes.length) {
                final byte[] temp = Arrays.copyOfRange(bytes, 5, bytes.length);
                return new TurtleObject(temp, SupportValue.getSupportValueByType(typeByte));
            }
        }
        throw new IllegalArgumentException("byte array  length is" + bytes.length);
    }

    private static void doubleAdd(byte[] bytes, double doubleValue) {
        doubleToBytes(bytes, bytesToDouble(bytes) + doubleValue);
    }

    private static void floatAdd(byte[] bytes, float floatValue) {
        floatToBytes(bytes, bytesToFloat(bytes) + floatValue);
    }

    private static void longAdd(byte[] bytes, long longValue) {
        longToBytes(bytes, bytesToLong(bytes) + longValue);
    }

    private static void intAdd(byte[] bytes, int intValue) {
        intToBytes(bytes, bytesToInt(bytes) + intValue);
    }


}

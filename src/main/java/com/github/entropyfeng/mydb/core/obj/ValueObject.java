package com.github.entropyfeng.mydb.core.obj;

import com.github.entropyfeng.mydb.config.Constant;
import com.github.entropyfeng.mydb.core.SupportValue;
import com.github.entropyfeng.mydb.expection.OutOfBoundException;
import com.google.common.base.Objects;
import org.jetbrains.annotations.NotNull;


import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;

public final class ValueObject implements Serializable,Cloneable,Comparable<ValueObject> {
    private final byte[] values;
    private final SupportValue type;


    private ValueObject(byte[] values, SupportValue type, long length) throws OutOfBoundException {
        if (length >= Constant.MAX_STRING_LENGTH) {
            throw new OutOfBoundException();
        }
        this.type = type;
        this.values = values;
    }

    private ValueObject(byte[] values, SupportValue type) {
        this.type = type;
        this.values = values;
    }

    public ValueObject(String value) {
        this(value.getBytes(), SupportValue.STRING, value.length() * 2);
    }

    public ValueObject(int value) {
        this(ByteBuffer.allocate(4).putInt(value).array(), SupportValue.INTEGER);
    }

    public ValueObject(float value) {
        this(ByteBuffer.allocate(4).putFloat(value).array(), SupportValue.FLOAT);
    }

    public ValueObject(double value) {
        this(ByteBuffer.allocate(8).putDouble(value).array(), SupportValue.DOUBLE);
    }

    public ValueObject(long value) {
        this(ByteBuffer.allocate(8).putLong(value).array(), SupportValue.LONG);
    }

    public ValueObject(BigInteger value) {
        this(value.toByteArray(), SupportValue.BIG_INTEGER);
    }

    public ValueObject(BigDecimal value) {
        this(value.toPlainString().getBytes(), SupportValue.BIG_DECIMAL);
    }

    public ValueObject append(String value)throws UnsupportedOperationException{
        if(this.type==SupportValue.STRING){
            return new ValueObject(value+new String(this.values));
        }else {
            throw new UnsupportedOperationException();
        }
    }

    public ValueObject increment(double doubleValue)throws UnsupportedOperationException{

        switch (type) {
            case DOUBLE:
                return new ValueObject(ByteBuffer.wrap(values).getDouble()+doubleValue);
            case FLOAT:
                return new ValueObject(add(ByteBuffer.wrap(this.values).getFloat(),doubleValue));
            case LONG :
              return new ValueObject(add(ByteBuffer.wrap(this.values).getLong(),doubleValue));
            case INTEGER:
              return new ValueObject(add(ByteBuffer.wrap(this.values).getInt(),doubleValue));
            case BIG_INTEGER:
                return new ValueObject(add(new BigInteger(this.values),doubleValue));
            case BIG_DECIMAL:
                return new ValueObject(add(new BigDecimal(new String(this.values)),doubleValue));
            default:throw new UnsupportedOperationException();
        }

    }

    public ValueObject increment(long longValue)throws UnsupportedOperationException {

        switch (type){
            case LONG: return new ValueObject(ByteBuffer.wrap(values).getLong()+longValue);
            case INTEGER:return new ValueObject(ByteBuffer.wrap(values).getInt()+longValue);
            case BIG_INTEGER:return new ValueObject(new BigInteger(this.values).add(BigInteger.valueOf(longValue)));
            case DOUBLE:return new ValueObject(add(longValue,ByteBuffer.wrap(this.values).getDouble()));
            case BIG_DECIMAL:return new ValueObject(add(new BigDecimal(new String(this.values)),longValue));
            case FLOAT:return new ValueObject(add(longValue,ByteBuffer.wrap(this.values).getFloat()));
            default:throw new UnsupportedOperationException();
        }
    }

    public ValueObject increment(BigInteger bigInteger){

        switch ()
    }

    private static BigDecimal add(float floatValue,double doubleValue){
       return new BigDecimal(String.valueOf(floatValue)).add(BigDecimal.valueOf(doubleValue));
    }
    private static BigDecimal add(long longValue,double doubleValue){
        return new BigDecimal(longValue).add(BigDecimal.valueOf(doubleValue));
    }

    private static BigDecimal add(long longValue,float floatValue){
        return new BigDecimal(longValue).add(new BigDecimal(String.valueOf(floatValue)));
    }

    private static BigDecimal add(BigInteger bigInteger,double doubleValue){
        return new BigDecimal(doubleValue).add(new BigDecimal(bigInteger));
    }

    private static BigDecimal add(BigDecimal bigDecimal,double doubleValue){
        return    bigDecimal.add(new BigDecimal(doubleValue));
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValueObject that = (ValueObject) o;
        return Objects.equal(values, that.values) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(values, type);
    }

    @Override
    protected ValueObject clone() throws CloneNotSupportedException {
        super.clone();
        return new ValueObject(Arrays.copyOf(this.values,this.values.length),SupportValue.valueOf(type.name()));
    }

    @Override
    public int compareTo(@NotNull ValueObject o) {
        return this.values;
    }
}

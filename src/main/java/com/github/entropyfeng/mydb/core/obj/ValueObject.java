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

import static com.github.entropyfeng.mydb.core.SupportValue.LONG;

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
        this(ByteBuffer.allocate(8).putLong(value).array(), LONG);
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
            case LONG :
            case INTEGER:
            case BIG_INTEGER:
            case BIG_DECIMAL:return new ValueObject(toBigDecimal(this).add(BigDecimal.valueOf(doubleValue)));
            default:throw new UnsupportedOperationException();
        }

    }

    public ValueObject increment(long longValue)throws UnsupportedOperationException {

        switch (type){
            case LONG: return new ValueObject(ByteBuffer.wrap(values).getLong()+longValue);
            case INTEGER:return new ValueObject(ByteBuffer.wrap(values).getInt()+longValue);
            case BIG_INTEGER:return new ValueObject(toBigInteger(this).add(BigInteger.valueOf(longValue)));
            case DOUBLE:
            case BIG_DECIMAL:
            case FLOAT:return new ValueObject(toBigDecimal(this).add(BigDecimal.valueOf(longValue)));
            default:throw new UnsupportedOperationException();
        }
    }
    public ValueObject increment(BigInteger bigInteger)throws UnsupportedOperationException{

        switch (type){
            case LONG:
            case INTEGER:
            case BIG_INTEGER:return new ValueObject(bigInteger.add(toBigInteger(this)));
            case BIG_DECIMAL:
            case FLOAT:
            case DOUBLE:return new ValueObject(toBigDecimal(this).add(new BigDecimal(bigInteger)));
            default:throw new UnsupportedOperationException();
        }
    }
    public ValueObject increment(BigDecimal bigDecimal){
        return new ValueObject(toBigDecimal(this).add(bigDecimal));
    }
    private static BigDecimal toBigDecimal(ValueObject valueObject)throws UnsupportedOperationException{
        switch (valueObject.type){
            case DOUBLE:return BigDecimal.valueOf(ByteBuffer.wrap(valueObject.values).getDouble());
            case FLOAT:return new BigDecimal(String.valueOf(ByteBuffer.wrap(valueObject.values).getFloat()));
            case BIG_INTEGER:new BigDecimal(new BigInteger(valueObject.values));
            case INTEGER:return new BigDecimal(ByteBuffer.wrap(valueObject.values).getInt());
            case LONG:return new BigDecimal(ByteBuffer.wrap(valueObject.values).getLong());
            case BIG_DECIMAL:return new BigDecimal(new String(valueObject.values));
            default:throw new UnsupportedOperationException();
        }
    }

    private static BigInteger toBigInteger(ValueObject valueObject)throws UnsupportedOperationException{
        switch (valueObject.type){
            case INTEGER:return BigInteger.valueOf(ByteBuffer.wrap(valueObject.values).getInt());
            case LONG:return BigInteger.valueOf(ByteBuffer.wrap(valueObject.values).getLong());
            case BIG_INTEGER:return new BigInteger(valueObject.values);
            default:throw  new UnsupportedOperationException();
        }
    }
    public Object toObject(){
        switch (type){
            case DOUBLE:return ByteBuffer.wrap(values).getDouble();
            case FLOAT:return ByteBuffer.wrap(values).getFloat();
            case LONG:return ByteBuffer.wrap(values).getLong();
            case INTEGER:return ByteBuffer.wrap(values).getInt();
            case BIG_DECIMAL:return new BigDecimal(new String(values));
            case BIG_INTEGER:return new BigInteger(values);
            default: return new String(values);

        }
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
        return 0;
    }


    public static void main(String[] args) {
        ValueObject longObject=new ValueObject(10086L);
        System.out.println(longObject.type);
        ValueObject bigDecimal=new ValueObject(new BigDecimal(Long.MAX_VALUE));
        System.out.println(bigDecimal.type);
    }

}

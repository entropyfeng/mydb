package com.github.entropyfeng.mydb.core.obj;

import com.github.entropyfeng.mydb.config.Constant;
import com.github.entropyfeng.mydb.core.SupportValue;
import com.github.entropyfeng.mydb.expection.OutOfBoundException;
import com.github.entropyfeng.mydb.util.CommonUtil;
import com.google.common.base.Objects;
import com.google.common.io.ByteSource;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;

import static com.github.entropyfeng.mydb.core.SupportValue.LONG;

/**
 * @author entropyfeng
 */
public  class ImValueObject implements Serializable,Cloneable,Comparable<ImValueObject> {
    private  byte[] values;
    private  SupportValue type;


    private ImValueObject(String value) throws OutOfBoundException {
        if (value.length()*2 >= Constant.MAX_STRING_LENGTH) {
            throw new OutOfBoundException();
        }
        this.type = SupportValue.STRING;
        this.values = value.getBytes();
    }

    private void handleObject(String value) throws OutOfBoundException {
        if (value.length()*2 >= Constant.MAX_STRING_LENGTH) {
            throw new OutOfBoundException();
        }
        this.type = SupportValue.STRING;
        this.values = value.getBytes();
    }
    private ImValueObject(byte[] values, SupportValue type) {
        this.type = type;
        this.values = values;
    }
    private void handleObject(byte[]values ,SupportValue type){
        this.type = type;
        this.values = values;
    }


    public ImValueObject(int value) {
        this(ByteBuffer.allocate(4).putInt(value).array(), SupportValue.INTEGER);
    }
    private void handleObject(int value) {
        handleObject(ByteBuffer.allocate(4).putInt(value).array(), SupportValue.INTEGER);
    }

    public ImValueObject(float value) {
        this(ByteBuffer.allocate(4).putFloat(value).array(), SupportValue.FLOAT);
    }

    private void handleObject(float value) {
        handleObject(ByteBuffer.allocate(4).putFloat(value).array(), SupportValue.FLOAT);
    }

    public ImValueObject(double value) {
        this(ByteBuffer.allocate(8).putDouble(value).array(), SupportValue.DOUBLE);
    }
    private void handleObject(double value) {
        handleObject(ByteBuffer.allocate(8).putDouble(value).array(), SupportValue.DOUBLE);
    }

    public ImValueObject(long value) {
        this(ByteBuffer.allocate(8).putLong(value).array(), LONG);
    }
    private void handleObject(long value) {
        handleObject(ByteBuffer.allocate(8).putDouble(value).array(), SupportValue.LONG);
    }

    public ImValueObject(BigInteger value) {
        this(value.toByteArray(), SupportValue.BIG_INTEGER);
    }

    public ImValueObject(BigDecimal value) {
        this(value.toPlainString().getBytes(), SupportValue.BIG_DECIMAL);
    }

    public void append(String value)throws UnsupportedOperationException{
        if(this.type==SupportValue.STRING){
         this.values=CommonUtil.mergeBytes(this.values,value.getBytes());
        }else {
            throw new UnsupportedOperationException();
        }
    }

    public ImValueObject increment(double doubleValue)throws UnsupportedOperationException{
        switch (type) {
            case DOUBLE:
                return new ImValueObject(ByteBuffer.wrap(values).getDouble()+doubleValue);
            case FLOAT:
            case LONG :
            case INTEGER:
            case BIG_INTEGER:
            case BIG_DECIMAL:return new ImValueObject(toBigDecimal(this).add(BigDecimal.valueOf(doubleValue)));
            default:throw new UnsupportedOperationException();
        }
    }


    public ImValueObject increment(long longValue)throws UnsupportedOperationException {

        switch (type){
            case LONG: return new ImValueObject(ByteBuffer.wrap(values).getLong()+longValue);
            case INTEGER:return new ImValueObject(ByteBuffer.wrap(values).getInt()+longValue);
            case BIG_INTEGER:return new ImValueObject(toBigInteger(this).add(BigInteger.valueOf(longValue)));
            case DOUBLE:
            case BIG_DECIMAL:
            case FLOAT:return new ImValueObject(toBigDecimal(this).add(BigDecimal.valueOf(longValue)));
            default:throw new UnsupportedOperationException();
        }
    }
    public ImValueObject increment(BigInteger bigInteger)throws UnsupportedOperationException{

        switch (type){
            case LONG:
            case INTEGER:
            case BIG_INTEGER:return new ImValueObject(bigInteger.add(toBigInteger(this)));
            case BIG_DECIMAL:
            case FLOAT:
            case DOUBLE:return new ImValueObject(toBigDecimal(this).add(new BigDecimal(bigInteger)));
            default:throw new UnsupportedOperationException();
        }
    }
    public ImValueObject increment(BigDecimal bigDecimal){
        return new ImValueObject(toBigDecimal(this).add(bigDecimal));
    }
    private static BigDecimal toBigDecimal(ImValueObject valueObject)throws UnsupportedOperationException{
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

    private static BigInteger toBigInteger(ImValueObject valueObject)throws UnsupportedOperationException{
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
        if (this == o){
            return true;
        }
        if (o == null || getClass() != o.getClass()){
            return false;
        }
        ImValueObject that = (ImValueObject) o;
        return Objects.equal(values, that.values) &&
                type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(values, type);
    }

    @Override
    protected ImValueObject clone() throws CloneNotSupportedException {
        super.clone();
        return new ImValueObject(Arrays.copyOf(this.values,this.values.length),SupportValue.valueOf(type.name()));
    }

    @Override
    public int compareTo(@NotNull ImValueObject o) {
        return 0;
    }

    private void intAdd(int intValue){

    }

    public static void main(String[] args) {

       byte[]res= ByteBuffer.allocate(4).array();

       int hah=ByteBuffer.wrap(res).asIntBuffer().put(7).get();
        System.out.println(hah);
    }

}

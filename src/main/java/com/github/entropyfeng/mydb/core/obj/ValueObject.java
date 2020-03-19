package com.github.entropyfeng.mydb.core.obj;

import com.github.entropyfeng.mydb.config.Constant;
import com.github.entropyfeng.mydb.core.SupportValue;
import com.github.entropyfeng.mydb.expection.OutOfBoundException;
import com.github.entropyfeng.mydb.util.BytesUtil;
import com.github.entropyfeng.mydb.util.CommonUtil;
import com.google.common.base.Objects;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.Arrays;

import static com.github.entropyfeng.mydb.core.SupportValue.LONG;

/**
 * @author entropyfeng
 */
public  class ValueObject implements Serializable,Cloneable, Externalizable,Comparable<ValueObject> {
    private  byte[] values;
    private  SupportValue type;

    private ValueObject(String value) throws OutOfBoundException {
        if (value.length()* 2 >= Constant.MAX_STRING_LENGTH) {
            throw new OutOfBoundException();
        }
        this.type = SupportValue.STRING;
        this.values = value.getBytes();
    }

    private ValueObject(byte[] values, SupportValue type) {
        this.type = type;
        this.values = values;
    }


    public ValueObject(int value) {
        this(BytesUtil.allocate4(value), SupportValue.INTEGER);
    }

    public ValueObject(double value) {
        this(BytesUtil.allocate8(value), SupportValue.DOUBLE);
    }


    public ValueObject(long value) {
        this(BytesUtil.allocate8(value), LONG);
    }

    public ValueObject(BigInteger value) {
        this(value.toByteArray(), SupportValue.BIG_INTEGER);
    }

    public ValueObject(BigDecimal value) {
        this(value.toPlainString().getBytes(), SupportValue.BIG_DECIMAL);
    }

    public void append(String value)throws UnsupportedOperationException{
        if(this.type==SupportValue.STRING){
         this.values=CommonUtil.mergeBytes(this.values,value.getBytes());
        }else {
            throw new UnsupportedOperationException();
        }
    }

    public void increment(double doubleValue)throws UnsupportedOperationException{
        switch (type) {
            case DOUBLE: BytesUtil.doubleAdd(this.values,doubleValue);break;
            case LONG :
            case INTEGER:
            case BIG_INTEGER:
            case BIG_DECIMAL:handleBigDecimal( toBigDecimal(this).add(BigDecimal.valueOf(doubleValue)));break;
            default:throw new UnsupportedOperationException();
        }
    }

    private void handleBigDecimal(BigDecimal bigDecimal){
        this.values=bigDecimal.toPlainString().getBytes();
        this.type=SupportValue.BIG_DECIMAL;
    }
    private void handleBigInteger(BigInteger bigInteger){
        this.values=bigInteger.toByteArray();
        this.type=SupportValue.BIG_INTEGER;
    }
    private void handleLong(long longValue){
        this.values=BytesUtil.allocate8(BytesUtil.bytesToInt(this.values)+longValue);
        this.type= SupportValue.LONG;
    }

    public void increment(long longValue)throws UnsupportedOperationException {

        switch (type){
            case LONG: BytesUtil.longAdd(this.values,longValue);break;
            case INTEGER:handleLong(longValue);break;
            case BIG_INTEGER:handleBigInteger(toBigInteger(this).add(BigInteger.valueOf(longValue)));break;
            case BIG_DECIMAL:handleBigDecimal(toBigDecimal(this).add(BigDecimal.valueOf(longValue)));break;
            default:throw new UnsupportedOperationException();
        }
    }

    public void increment(int intValue)throws UnsupportedOperationException{
        switch (type){
            case INTEGER:BytesUtil.intAdd(this.values,intValue);break;
            case LONG:BytesUtil.longAdd(this.values,intValue);break;
            case BIG_INTEGER:handleBigInteger(toBigInteger(this).add(BigInteger.valueOf(intValue)));break;
            case DOUBLE:
            case BIG_DECIMAL:handleBigDecimal(toBigDecimal(this).add(BigDecimal.valueOf(intValue)));break;
            default:throw new UnsupportedOperationException();
        }
    }
    public void increment(BigInteger bigInteger)throws UnsupportedOperationException{

        switch (type){
            case LONG:
            case INTEGER:
            case BIG_INTEGER:handleBigInteger(bigInteger.add(toBigInteger(this)));break;
            case BIG_DECIMAL:
            case DOUBLE:handleBigDecimal(toBigDecimal(this).add(new BigDecimal(bigInteger)));break;
            default:throw new UnsupportedOperationException();
        }
    }
    public void increment(BigDecimal bigDecimal){
        handleBigDecimal(toBigDecimal(this).add(bigDecimal));
    }
    private static BigDecimal toBigDecimal(ValueObject valueObject)throws UnsupportedOperationException{
        switch (valueObject.type){
            case DOUBLE:return BigDecimal.valueOf(ByteBuffer.wrap(valueObject.values).getDouble());
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

    private void intAdd(int intValue){

    }

    public static void main(String[] args) {

       byte[]res= ByteBuffer.allocate(4).array();

       int hah=ByteBuffer.wrap(res).asIntBuffer().put(7).get();
        System.out.println(hah);
    }

    public byte[]toByte(){
        final int length=values.length+1;
        return
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {


    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

    }
}
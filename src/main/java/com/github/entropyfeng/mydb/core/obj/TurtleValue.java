package com.github.entropyfeng.mydb.core.obj;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.util.BytesUtil;
import com.github.entropyfeng.mydb.util.CommonUtil;
import com.google.protobuf.ByteString;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.ByteBuffer;

import static com.github.entropyfeng.mydb.util.BytesUtil.*;
import static com.github.entropyfeng.mydb.util.BytesUtil.bytesToInt;


/**
 * @author entropyfeng
 */
public class TurtleValue {
    private TurtleProtoBuf.TurtleParaType type;
    private byte[] values;

    public TurtleValue(TurtleProtoBuf.TurtleValue value) {

        final TurtleProtoBuf.TurtleParaType paraType = value.getTurtleParaType();
        final ByteString byteString = value.getValues();
        switch (paraType) {
            case STRING: {
                this.type = TurtleProtoBuf.TurtleParaType.STRING;
                this.values = byteString.toByteArray();
                return;
            }
            case LONG: {
                this.type = TurtleProtoBuf.TurtleParaType.LONG;
                this.values = byteString.toByteArray();
                return;
            }
            case INTEGER: {
                this.type = TurtleProtoBuf.TurtleParaType.INTEGER;
                this.values = byteString.toByteArray();
                return;
            }
            case DOUBLE: {
                this.type = TurtleProtoBuf.TurtleParaType.DOUBLE;
                this.values = byteString.toByteArray();
                return;
            }
            case NUMBER_INTEGER: {
                this.type = TurtleProtoBuf.TurtleParaType.NUMBER_INTEGER;
                this.values = byteString.toByteArray();
                return;
            }
            case NUMBER_DECIMAL: {
                this.type = TurtleProtoBuf.TurtleParaType.NUMBER_DECIMAL;
                this.values = byteString.toByteArray();
                return;
            }
            default:
                throw new UnsupportedOperationException("unSupport paraType" + paraType.toString());
        }

    }

    private TurtleValue(byte[] bytes, TurtleProtoBuf.TurtleParaType type) {
        this.type = type;
        this.values = bytes;
    }

    public TurtleValue(int value) {
        this(BytesUtil.allocate4(value), TurtleProtoBuf.TurtleParaType.INTEGER);
    }

    public TurtleValue(double value) {
        this(BytesUtil.allocate8(value), TurtleProtoBuf.TurtleParaType.DOUBLE);
    }


    public TurtleValue(long value) {
        this(BytesUtil.allocate8(value), TurtleProtoBuf.TurtleParaType.LONG);
    }

    public TurtleValue(BigInteger value) {
        this(value.toByteArray(), TurtleProtoBuf.TurtleParaType.NUMBER_INTEGER);
    }

    public TurtleValue(BigDecimal value) {
        this(value.toPlainString().getBytes(), TurtleProtoBuf.TurtleParaType.NUMBER_DECIMAL);
    }

    public void append(String value) throws UnsupportedOperationException {
        if (this.type == TurtleProtoBuf.TurtleParaType.STRING) {
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
                break;
            case NUMBER_INTEGER:
                handleBigInteger(toBigInteger(this).add(BigInteger.valueOf(longValue)));
                break;
            case NUMBER_DECIMAL:
                handleBigDecimal(toBigDecimal(this).add(BigDecimal.valueOf(longValue)));
                break;
            default:
                throw new UnsupportedOperationException("unSupport append operation on" + type.toString());
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
            case NUMBER_INTEGER:
                handleBigInteger(toBigInteger(this).add(BigInteger.valueOf(intValue)));
                break;
            case DOUBLE:
            case NUMBER_DECIMAL:
                handleBigDecimal(toBigDecimal(this).add(BigDecimal.valueOf(intValue)));
                break;
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
                break;
            case NUMBER_DECIMAL:
            case DOUBLE:
                handleBigDecimal(toBigDecimal(this).add(new BigDecimal(bigInteger)));
                break;
            default:
                throw new UnsupportedOperationException("unSupport append operation on" + type.toString());
        }
    }


    public void increment(double doubleValue) throws UnsupportedOperationException {
        switch (type) {
            case DOUBLE:
                doubleAdd(this.values, doubleValue);
                break;
            case LONG:
            case INTEGER:
            case NUMBER_INTEGER:
            case NUMBER_DECIMAL:
                handleBigDecimal(toBigDecimal(this).add(BigDecimal.valueOf(doubleValue)));
                break;
            default:
                throw new UnsupportedOperationException("unSupport append operation on" + type.toString());
        }
    }

    private void handleBigDecimal(BigDecimal bigDecimal) {
        this.values = bigDecimal.toPlainString().getBytes();
        this.type = TurtleProtoBuf.TurtleParaType.NUMBER_DECIMAL;
    }

    private void handleBigInteger(BigInteger bigInteger) {
        this.values = bigInteger.toByteArray();
        this.type = TurtleProtoBuf.TurtleParaType.NUMBER_INTEGER;
    }

    private void handleLong(long longValue) {
        this.values = BytesUtil.allocate8(longValue);
        this.type = TurtleProtoBuf.TurtleParaType.LONG;
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
     *
     * @param bytes long type
     * @param longValue long value
     */
    private static void longAdd(byte[] bytes, long longValue) {
        longToBytes(bytes, bytesToLong(bytes) + longValue);
    }

    /**
     * @param bytes int type
     * @param intValue int value
     */
    private static void intAdd(byte[] bytes, int intValue) {
        intToBytes(bytes, bytesToInt(bytes) + intValue);
    }




}

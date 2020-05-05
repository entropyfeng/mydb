package com.github.entropyfeng.mydb.common;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author entropyfeng
 */
public class MyValue implements Comparable<MyValue> {

    /**
     * 8个字节
     */
    private TurtleValueType type;

    /**
     * 8个字节
     */
    private Object value;

    public MyValue(Double doubleValue) {
        this.type = TurtleValueType.DOUBLE;
        this.value = doubleValue;
    }

    public MyValue(Integer integer) {
        this.type = TurtleValueType.INTEGER;
        this.value = integer;
    }

    public MyValue(Long longValue) {
        this.type = TurtleValueType.LONG;
        this.value = longValue;
    }

    public MyValue(BigInteger bigInteger) {
        this.value = TurtleValueType.NUMBER_INTEGER;
        this.value = bigInteger;
    }

    public MyValue(BigDecimal bigDecimal) {
        this.type = TurtleValueType.NUMBER_DECIMAL;
        this.value = bigDecimal;
    }

    public MyValue(@NotNull String string) {
        this.value = TurtleValueType.BYTES;
        this.value = string.getBytes();
    }

    public MyValue(byte[] bytes) {
        this.value = TurtleValueType.BYTES;
        this.value = bytes;
    }

    public void append(String otherKey) throws UnsupportedOperationException {
        if (type == TurtleValueType.BYTES) {
           value= concat((byte[]) value,otherKey.getBytes());
        } else {
            throw new UnsupportedOperationException("currentType:" + this.type);
        }
    }

    public void append(byte[] otherBytes) throws UnsupportedOperationException {
        if (type == TurtleValueType.BYTES) {
            value= concat((byte[]) value,otherBytes);
        } else {
            throw new UnsupportedOperationException("currentType:" + this.type);
        }

    }

    public void increment(Long longValue) throws UnsupportedOperationException {

        switch (type) {

            case LONG:
                this.value = (Long) (value) + longValue;
                return;
            case INTEGER:
                this.value = (Integer) (value) + longValue;
                this.type = TurtleValueType.LONG;
                return;
            case NUMBER_INTEGER:
                this.value = ((BigInteger) value).add(BigInteger.valueOf(longValue));
                this.type = TurtleValueType.NUMBER_INTEGER;
                return;
            case NUMBER_DECIMAL:
                this.value = ((BigDecimal) value).add(BigDecimal.valueOf(longValue));
                return;
            case DOUBLE:
                this.value = BigDecimal.valueOf((Double) value).add(BigDecimal.valueOf(longValue));
                this.type = TurtleValueType.NUMBER_DECIMAL;
                return;
            default:
                throw new UnsupportedOperationException("unSupport append operation on" + type.toString());
        }
    }

    public void increment(Integer intValue) throws UnsupportedOperationException {
        switch (type) {
            case INTEGER:
                this.value = ((Integer) value) + intValue;
                return;
            case LONG:
                this.value = ((Long) value) + intValue;
                return;
            case NUMBER_INTEGER:
                this.value = ((BigInteger) value).add(BigInteger.valueOf(intValue));
                return;
            case DOUBLE:
                this.value = BigDecimal.valueOf((Double) value).add(BigDecimal.valueOf(intValue));
                this.type = TurtleValueType.NUMBER_DECIMAL;
                return;
            case NUMBER_DECIMAL:
                this.value = ((BigDecimal) value).add(BigDecimal.valueOf(intValue));
                return;
            default:
                throw new UnsupportedOperationException("unSupport append operation on" + type.toString());
        }
    }

    public void increment(BigInteger bigInteger) throws UnsupportedOperationException {

        switch (type) {
            case LONG:
                this.type = TurtleValueType.NUMBER_INTEGER;
                this.value = bigInteger.add(BigInteger.valueOf((Long) value));
                return;
            case INTEGER:
                this.type = TurtleValueType.NUMBER_INTEGER;
                this.value = bigInteger.add(BigInteger.valueOf((Integer) value));
                return;
            case NUMBER_INTEGER:
                this.value = bigInteger.add((BigInteger) value);
                return;
            case NUMBER_DECIMAL:

                this.value = ((BigDecimal) value).add(new BigDecimal(bigInteger));
                return;
            case DOUBLE:
                this.type = TurtleValueType.NUMBER_DECIMAL;
                this.value = new BigDecimal(bigInteger).add(BigDecimal.valueOf((Double) value));
                return;
            default:
                throw new UnsupportedOperationException("unSupport append operation on" + type.toString());
        }
    }

    public void increment(Double doubleValue) throws UnsupportedOperationException {
        switch (type) {
            case DOUBLE:
                this.value = ((Double) value) + doubleValue;
                return;
            case LONG:
            case INTEGER:
            case NUMBER_INTEGER:
            case NUMBER_DECIMAL:
                convertToBigDecimal();
                this.value = ((BigDecimal) value).add(BigDecimal.valueOf(doubleValue));
                return;
            default:
                throw new UnsupportedOperationException("unSupport append operation on" + type.toString());
        }
    }

    @NotNull
    private static BigDecimal castToBigDecimal(TurtleValueType type, Object value) {
        switch (type) {
            case INTEGER:
                return BigDecimal.valueOf((Integer) value);
            case DOUBLE:
                return BigDecimal.valueOf((Double) value);
            case LONG:
                return BigDecimal.valueOf((Long) value);
            case NUMBER_INTEGER:
                return new BigDecimal((BigInteger) value);
            case NUMBER_DECIMAL:
                return (BigDecimal) value;
            default:
                throw new UnsupportedOperationException();
        }
    }

    private void convertToBigDecimal() {
        switch (type) {
            case DOUBLE:
                this.type = TurtleValueType.NUMBER_DECIMAL;
                this.value = BigDecimal.valueOf((Double) value);
                return;
            case INTEGER:
                this.type = TurtleValueType.NUMBER_DECIMAL;
                this.value = BigDecimal.valueOf((Integer) value);
                return;
            case LONG:
                this.type = TurtleValueType.NUMBER_DECIMAL;
                this.value = BigDecimal.valueOf((Long) value);
                return;
            case NUMBER_INTEGER:
                this.type = TurtleValueType.NUMBER_DECIMAL;
                this.value = new BigDecimal((BigInteger) value);
                return;
            case NUMBER_DECIMAL:
                return;
            default:
                throw new UnsupportedOperationException("unSupport convert to operation on" + type.toString());

        }
    }

    public void increment(BigDecimal bigDecimal) {
        switch (type) {
            case DOUBLE:
            case INTEGER:
            case LONG:
            case NUMBER_DECIMAL:
            case NUMBER_INTEGER:
                convertToBigDecimal();
                this.value = ((BigDecimal) value).add(bigDecimal);
                return;
            default:
                throw new UnsupportedOperationException("unSupport append operation on" + type.toString());
        }
    }

    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        MyValue myValue = (MyValue) object;
        return compareTo(myValue) == 0;

    }

    @SuppressWarnings("all")
    @Override
    public int hashCode() {
        switch (type) {
            case INTEGER:
                return Hashing.murmur3_32().hashInt((Integer) value).asInt();
            case LONG:
                return Hashing.murmur3_32().hashLong((Long) value).asInt();
            case BYTES:
            case DOUBLE:
            case NUMBER_INTEGER:
                return Hashing.murmur3_32().hashString(value.toString(), Charsets.UTF_8).asInt();
            case NUMBER_DECIMAL:
                return Hashing.murmur3_32().hashString(((BigDecimal) value).toPlainString(), Charsets.UTF_8).asInt();
            default:
                return 0;
        }
    }

    @Override
    public int compareTo(@NotNull MyValue that) {
        if (this.type == that.type) {
            switch (type) {
                case NUMBER_DECIMAL:
                    return ((BigDecimal) value).compareTo((BigDecimal) that.value);
                case NUMBER_INTEGER:
                    return ((BigInteger) value).compareTo((BigInteger) that.value);
                case LONG:
                    return Long.compare((Long) (value), (Long) that.value);
                case DOUBLE:
                    return Double.compare((Double) value, (Double) that.value);
                case INTEGER:
                    return Integer.compare((Integer) value, (Integer) that.value);
                case BYTES:
                    return value.toString().compareTo(that.value.toString());
                default:
                    return 0;
            }
        } else {
            if (this.type != TurtleValueType.BYTES && that.type != TurtleValueType.BYTES) {
                return castToBigDecimal(this.type, this.value).compareTo(castToBigDecimal(that.type, that.value));
            } else {
                return 0;
            }
        }
    }

    private static byte[] concat(byte[] a, byte[] b) {
        byte[] newBytes = new byte[a.length + b.length];
        System.arraycopy(a, 0, newBytes, 0, a.length);
        System.arraycopy(b, 0, newBytes, a.length, b.length);
        return newBytes;
    }
}

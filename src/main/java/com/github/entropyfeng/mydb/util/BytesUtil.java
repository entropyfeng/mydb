package com.github.entropyfeng.mydb.util;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.nio.ByteBuffer;

/**
 * 默认大端法
 *
 * @author entropyfeng
 */
public class BytesUtil {


    public static void intToBytes(byte[] bytes, int intValue) {
        assert bytes.length==4;
        bytes[0] = (byte) (intValue >> 24 & 0xff);
        bytes[1] = (byte) (intValue >> 16 & 0xff);
        bytes[2] = (byte) (intValue >> 8 & 0xff);
        bytes[3] = (byte) (intValue & 0xff);

    }

    public static int bytesToInt(byte byte0,byte byte1,byte byte2,byte byte3){
        return (byte0 & 0xff) << 24
                | (byte1 & 0xff) << 16
                | (byte2 & 0xff) << 8
                | (byte3 & 0xff);
    }
    public static int bytesToInt(byte[] bytes) {
        assert bytes.length==4;
        return (bytes[0] & 0xff) << 24
                | (bytes[1] & 0xff) << 16
                | (bytes[2] & 0xff) << 8
                | (bytes[3] & 0xff);
    }


    public static void longToBytes(byte[] bytes, long longValue) {
        assert bytes.length==8;
        bytes[0] = (byte) (longValue >> 56 & 0xff);
        bytes[1] = (byte) (longValue >> 48 & 0xff);
        bytes[2] = (byte) (longValue >> 40 & 0xff);
        bytes[3] = (byte) (longValue >> 32 & 0xff);

        bytes[4] = (byte) (longValue >> 24 & 0xff);
        bytes[5] = (byte) (longValue >> 16 & 0xff);
        bytes[6] = (byte) (longValue >> 8 & 0xff);
        bytes[7] = (byte) (longValue & 0xff);

    }

    public static long bytesToLong(byte[] bytes) {
        assert bytes.length==8;
        return
                ((long) (bytes[0] & 0xff) << 56) |
                        ((long) (bytes[1] & 0xff) << 48) |
                        ((long) (bytes[2] & 0xff) << 40) |
                        ((long) (bytes[3] & 0xff) << 32) |

                        ((long) (bytes[4] & 0xff) << 24) |
                        ((long) (bytes[5] & 0xff) << 16) |
                        ((long) (bytes[6] & 0xff) << 8) |
                        ((long) bytes[7] & 0xff);
    }


    public static double bytesToDouble(byte[] bytes) {
        assert bytes.length==8;
        return Double.longBitsToDouble(bytesToLong(bytes));
    }

    public static void doubleToBytes(byte[] bytes, double doubleValue) {
        assert bytes.length==8;
        longToBytes(bytes, Double.doubleToLongBits(doubleValue));
    }


    public static void floatToBytes(byte[] bytes, float floatValue) {
        assert bytes.length==4;
        intToBytes(bytes, Float.floatToIntBits(floatValue));
    }

    public static float bytesToFloat(byte[] bytes) {
        assert bytes.length==4;
        return Float.intBitsToFloat(bytesToInt(bytes));
    }


    public static byte[] allocate8(long longValue){
        byte[] bytes=new byte[8];
        longToBytes(bytes,longValue);
        return bytes;
    }
    public static byte[] allocate8(double doubleValue){
        byte[] bytes=new byte[8];
        doubleToBytes(bytes,doubleValue);
        return bytes;
    }
    public static byte[] allocate4(int intValue){
        byte[]bytes=new byte[4];
        intToBytes(bytes,intValue);
        return bytes;
    }
    public static byte[] allocate4(float floatValue){
        byte[]bytes=new byte[4];
        floatToBytes(bytes,floatValue);
        return bytes;
    }


}

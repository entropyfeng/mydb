package com.github.entropyfeng.mydb.util;

import java.nio.ByteBuffer;

/**
 * 默认大端法
 *
 * @author entropyfeng
 */
public class BytesUtil {


    public static void intToBytes(int intValue, byte[] bytes) {
        assert bytes.length == 4;
        bytes[0] = (byte) (intValue >> 24 & 0xff);
        bytes[1] = (byte) (intValue >> 16 & 0xff);
        bytes[2] = (byte) (intValue >> 8 & 0xff);
        bytes[3] = (byte) (intValue & 0xff);

    }

    public static void extraIntToBytes(int intValue, byte[] bytes){

        bytes[1] = (byte) (intValue >> 24 & 0xff);
        bytes[2] = (byte) (intValue >> 16 & 0xff);
        bytes[3] = (byte) (intValue >> 8 & 0xff);
        bytes[4] = (byte) (intValue & 0xff);
    }
    public static int bytesToInt(byte byte0, byte byte1, byte byte2, byte byte3) {
        return (byte0 & 0xff) << 24
                | (byte1 & 0xff) << 16
                | (byte2 & 0xff) << 8
                | (byte3 & 0xff);
    }

    public static int bytesToInt(byte[] bytes) {
        assert bytes.length == 4;
        return (bytes[0] & 0xff) << 24
                | (bytes[1] & 0xff) << 16
                | (bytes[2] & 0xff) << 8
                | (bytes[3] & 0xff);
    }


    public static void longToBytes(long longValue, byte[] bytes) {
        assert bytes.length == 8;
        bytes[0] = (byte) (longValue >> 56 & 0xff);
        bytes[1] = (byte) (longValue >> 48 & 0xff);
        bytes[2] = (byte) (longValue >> 40 & 0xff);
        bytes[3] = (byte) (longValue >> 32 & 0xff);

        bytes[4] = (byte) (longValue >> 24 & 0xff);
        bytes[5] = (byte) (longValue >> 16 & 0xff);
        bytes[6] = (byte) (longValue >> 8 & 0xff);
        bytes[7] = (byte) (longValue & 0xff);

    }

    public static void extraLongToBytes(long longValue,byte[] bytes){
        bytes[1] = (byte) (longValue >> 56 & 0xff);
        bytes[2] = (byte) (longValue >> 48 & 0xff);
        bytes[3] = (byte) (longValue >> 40 & 0xff);
        bytes[4] = (byte) (longValue >> 32 & 0xff);

        bytes[5] = (byte) (longValue >> 24 & 0xff);
        bytes[6] = (byte) (longValue >> 16 & 0xff);
        bytes[7] = (byte) (longValue >> 8 & 0xff);
        bytes[8] = (byte) (longValue & 0xff);
    }
    public static long bytesToLong(byte[] bytes) {
        assert bytes.length == 8;
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
        assert bytes.length == 8;
        return Double.longBitsToDouble(bytesToLong(bytes));
    }

    public static void doubleToBytes(byte[] bytes, double doubleValue) {
        assert bytes.length == 8;
        longToBytes(Double.doubleToLongBits(doubleValue), bytes);
    }

    public static byte[] allocate8(long longValue) {
        byte[] bytes = new byte[8];
        longToBytes(longValue, bytes);
        return bytes;
    }

    public static byte[] allocate8(double doubleValue) {
        byte[] bytes = new byte[8];
        doubleToBytes(bytes, doubleValue);
        return bytes;
    }

    public static byte[] allocate4(int intValue) {
        byte[] bytes = new byte[4];
        intToBytes(intValue, bytes);
        return bytes;
    }

    public static byte[] concat(byte a,byte[] b){
        byte[] newBytes=new byte[b.length+1];
        System.arraycopy(b,0,newBytes,1,b.length);
        newBytes[0]=a;
        return newBytes;
    }
    public static byte[] concat(byte[] a,byte[] b){
        byte[] newBytes=new byte[a.length+b.length];
        System.arraycopy(b,0,newBytes,a.length,b.length);
        return newBytes;
    }

    /**
     * 向右移动1字节
     * @param bytes byte 数组
     * @return 移动后的数组
     */
    public static byte[] rightMove1(byte[] bytes){
        byte[] newBytes=new byte[bytes.length+1];
        System.arraycopy(bytes,0,newBytes,1,bytes.length);
        return newBytes;
    }
}

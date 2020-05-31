package com.github.entropyfeng.mydb.common.util;


import org.jetbrains.annotations.NotNull;

/**
 * 默认大端法
 *
 * @author entropyfeng
 */
public class BytesUtil {


    public static byte[] intToBytes(int intValue) {
        byte[] bytes = new byte[4];
        bytes[0] = (byte) (intValue >> 24 & 0xff);
        bytes[1] = (byte) (intValue >> 16 & 0xff);
        bytes[2] = (byte) (intValue >> 8 & 0xff);
        bytes[3] = (byte) (intValue & 0xff);
        return bytes;
    }


    public static int bytesToInt(byte[] bytes) {

        return (bytes[0] & 0xff) << 24
                | (bytes[1] & 0xff) << 16
                | (bytes[2] & 0xff) << 8
                | (bytes[3] & 0xff);
    }


    public static byte[] longToBytes(long longValue) {
        byte[] bytes = new byte[8];
        bytes[0] = (byte) (longValue >> 56 & 0xff);
        bytes[1] = (byte) (longValue >> 48 & 0xff);
        bytes[2] = (byte) (longValue >> 40 & 0xff);
        bytes[3] = (byte) (longValue >> 32 & 0xff);

        bytes[4] = (byte) (longValue >> 24 & 0xff);
        bytes[5] = (byte) (longValue >> 16 & 0xff);
        bytes[6] = (byte) (longValue >> 8 & 0xff);
        bytes[7] = (byte) (longValue & 0xff);
        return bytes;
    }



    public static long bytesToLong(byte[] bytes) {
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
        return Double.longBitsToDouble(bytesToLong(bytes));
    }

    @NotNull
    public static byte[] doubleToBytes(double doubleValue) {


        return longToBytes(Double.doubleToLongBits(doubleValue));
    }

    public static byte[] concat(byte[] a, byte[] b) {
        byte[] newBytes = new byte[a.length + b.length];
        System.arraycopy(a, 0, newBytes, 0, a.length);
        System.arraycopy(b, 0, newBytes, a.length, b.length);
        return newBytes;
    }

    /**
     * 将字节数组按照字典序比较
     * @param cur this
     * @param that that
     * @return  a negative integer, zero, or a positive integer as this object
     *          is less than, equal to, or greater than the specified object.
     */
    public static int compare(byte[] cur,byte[] that){

        int len1 = cur.length;
        int len2 = that.length;
        int lim = Math.min(len1, len2);

        int k = 0;
        while (k < lim) {
            byte c1 = cur[k];
            byte c2 = that[k];
            if (c1 != c2) {
                return c1 - c2;
            }
            k++;
        }
        return len1 - len2;

    }

}

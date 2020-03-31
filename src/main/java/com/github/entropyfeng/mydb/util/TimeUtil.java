package com.github.entropyfeng.mydb.util;

/**
 * @author entropyfeng
 * 时间工具类
 */
public class TimeUtil {
    /**
     *
     * @param time 时间戳毫秒单位
     * @return true->已过期； false->未过期
     */
    public static boolean isExpire(long time) {
        return time < EfficientSystemClock.now();
    }

    /**
     * 高效率获取当前时间戳,可能会造成一定的精度损失
     * @return timestamp
     */
    public static long currentTime(){
        return EfficientSystemClock.now();
    }

    public static void main(String[] args) {


        int pos = Integer.MAX_VALUE;
        long begin = System.currentTimeMillis();

        for (int i = 0; i < pos; i++) {
            System.currentTimeMillis();
        }
        long end = System.currentTimeMillis();

        for (int i = 0; i < pos; i++) {
            EfficientSystemClock.now();
        }
        long third = System.currentTimeMillis();
        System.out.println(end - begin);
        System.out.println(third - end);
    }
}

package com.github.entropyfeng.mydb.util;

import java.util.concurrent.ThreadPoolExecutor;

public class TimeUtil {
    public static boolean isExpire(Long time) {
        return time != null && time > EfficientSystemClock.now();
    }

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

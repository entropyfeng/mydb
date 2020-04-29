package com.github.entropyfeng.mydb.util;

import org.jetbrains.annotations.NotNull;

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
    public static boolean isExpire(@NotNull Long time) {
        return time < EfficientSystemClock.now();
    }

    /**
     * 高效率获取当前时间戳,可能会造成一定的精度损失
     * @return timestamp
     */
    public static long currentTime(){
        return EfficientSystemClock.now();
    }

}

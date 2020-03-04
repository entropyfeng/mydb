package com.github.entropyfeng.mydb.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author entropyfeng
 * @date 2020/3/4 14:42
 */
public class CommonUtil {

    /**
     * 抛硬币法获取层数
     * @return 1=<int<=32
     */
    public static int getLevel() {

        final int maxLevel=32;
        int res = 1;
        while (ThreadLocalRandom.current().nextBoolean()&&res<=maxLevel) {
            res++;
        }
        return res;
    }

}

package com.github.entropyfeng.mydb.common;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author entropyfeng
 */
public class RequestIdPool {

    private static final AtomicLong ATOMIC_LONG =new AtomicLong(1);

    public static long getAndIncrement(){
        return ATOMIC_LONG .getAndIncrement();
    }
}

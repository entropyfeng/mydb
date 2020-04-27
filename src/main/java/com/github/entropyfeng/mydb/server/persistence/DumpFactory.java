package com.github.entropyfeng.mydb.server.persistence;


import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author entropyfeng
 */
public class DumpFactory implements ThreadFactory {


    private static AtomicInteger atomicInteger=new AtomicInteger(0);

    @Override
    public Thread newThread(@NotNull Runnable r) {

        return new Thread(r,"dump thread ");
    }
}

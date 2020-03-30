package com.github.entropyfeng.mydb.server.factory;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;

/**
 * @author entropyeng
 */
public class ValuesThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(@NotNull Runnable r) {
        Thread res=new Thread(r,"valuesThread");
        res.setDaemon(true);
        return res;
    }
}

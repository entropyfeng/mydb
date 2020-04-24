package com.github.entropyfeng.mydb.server.factory;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;

/**
 * @author entropyfeng
 */
public class OrderSetThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(@NotNull Runnable r) {
        Thread res=new Thread(r,"orderSetThread");
        res.setDaemon(true);
        return res;
    }
}

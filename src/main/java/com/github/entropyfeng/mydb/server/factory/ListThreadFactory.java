package com.github.entropyfeng.mydb.server.factory;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;

/**
 * @author entropyfeng
 */
public class ListThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(@NotNull Runnable r) {
        Thread res=new Thread(r,"listThread");
        res.setDaemon(true);
        return res;
    }
}

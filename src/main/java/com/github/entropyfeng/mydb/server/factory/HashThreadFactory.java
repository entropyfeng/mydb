package com.github.entropyfeng.mydb.server.factory;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;

public class HashThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(@NotNull Runnable r) {
        Thread res=new Thread(r,"hashThread");
        res.setDaemon(true);
        return res;
    }
}

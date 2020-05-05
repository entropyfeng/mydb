package com.github.entropyfeng.mydb.client.conn;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;

/**
 * @author entropyfeng
 */
public class ClientThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(@NotNull Runnable r) {
        Thread thread=new Thread(r,"clientThread");
        thread.setDaemon(true);
        return thread;
    }
}

package com.github.entropyfeng.mydb.server.persistence.factory;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;

/**
 * @author entropyfeng
 */
public class LoadThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(@NotNull Runnable r) {
        return new Thread(r, "loadDumpFileThread");
    }
}

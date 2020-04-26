package com.github.entropyfeng.mydb.server.persistence;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;

/**
 * @author entropyfeng
 */
public class OrderSetDumpFactory implements ThreadFactory {
    @Override
    public Thread newThread(@NotNull Runnable r) {
        return new Thread(r,"order set Dump thread");
    }
}

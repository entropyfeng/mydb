package com.github.entropyfeng.mydb.server.ms;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;

/**
 * @author entropyfeng
 */
public class CommandTransFactory implements ThreadFactory {


    @Override
    public Thread newThread(@NotNull Runnable r) {

        Thread thread=new Thread(r,"commandTrans");
        thread.setDaemon(true);
        return thread;
    }
}

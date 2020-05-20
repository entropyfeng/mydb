package com.github.entropyfeng.mydb.client.asy;

import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;

/**
 * @author entropyfeng
 */
public class HandleResThreadFactory implements ThreadFactory {

    @Override
    public Thread newThread(@NotNull Runnable r) {
        return new Thread(r,"handleRes");
    }
}

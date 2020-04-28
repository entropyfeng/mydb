package com.github.entropyfeng.mydb.server.persistence;


import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;

/**
 * @author entropyfeng
 */
public class DumpFactory implements ThreadFactory {


    @Override
    public Thread newThread(@NotNull Runnable r) {

        return new Thread(r, "dump thread ");
    }
}

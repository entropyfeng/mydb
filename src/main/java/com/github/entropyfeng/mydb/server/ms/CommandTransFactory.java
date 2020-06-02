package com.github.entropyfeng.mydb.server.ms;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;

/**
 * 命令传播线程工厂方法
 * @author entropyfeng
 */
public class CommandTransFactory implements ThreadFactory {

    private static final Logger logger= LoggerFactory.getLogger(CommandTransFactory.class);

    @Override
    public Thread newThread(@NotNull Runnable r) {

        Thread thread=new Thread(r,"commandTrans");
        thread.setDaemon(true);
        logger.info("commandTransThread start !!!");
        return thread;
    }
}

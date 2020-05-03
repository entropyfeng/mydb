package com.github.entropyfeng.mydb.server.util;

import com.github.entropyfeng.mydb.server.config.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author entropyfeng
 */
public class EfficientSystemClock {

    private static final Logger logger = LoggerFactory.getLogger(EfficientSystemClock.class);

    private final AtomicLong now;

    private final long precision;

    /**
     * 内部类延迟初始化
     */
    private static class EfficientSystemClockHolder{

        private static final EfficientSystemClock INSTANCE =new EfficientSystemClock(ServerConfig.precision);
    }

    public static long now(){
        return EfficientSystemClockHolder.INSTANCE.now.get();
    }

    private EfficientSystemClock(long precision) {
        this.precision = precision;
        now = new AtomicLong(System.currentTimeMillis());
        schedulerClockUpdate();
    }

    private void schedulerClockUpdate() {
        ScheduledExecutorService scheduled =  new ScheduledThreadPoolExecutor(1, r -> {
            Thread thread=new Thread(r,"update system clock thread");
            thread.setDaemon(true);
            return thread;
        });

        scheduled.scheduleAtFixedRate(this::update, precision, precision, TimeUnit.MILLISECONDS);
        logger.info("start efficient system clock");
    }


    private void update() {
        now.set(System.currentTimeMillis());
    }

}

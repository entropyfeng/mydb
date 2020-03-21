package com.github.entropyfeng.mydb.util;

import com.github.entropyfeng.mydb.config.CommonConfig;
import com.github.entropyfeng.mydb.config.Constant;
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

    private static class EfficientSystemClockHolder{

        final static long CONFIG_PRECISION =ConfigUtil.getIntegerProperty(CommonConfig.getProperties(),Constant.SYSTEM_CLOCK_REFRESH);

        private static final EfficientSystemClock INSTANCE =new EfficientSystemClock(CONFIG_PRECISION);
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

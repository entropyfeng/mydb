package com.github.entropyfeng.mydb.util;

import com.github.entropyfeng.mydb.config.CommonConfig;
import com.github.entropyfeng.mydb.config.Constant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class EfficientSystemClock {

    private static final Logger logger = LoggerFactory.getLogger(EfficientSystemClock.class);

    private final AtomicLong now;

    private final long precision;

    private static class EfficientSystemClockHolder{

        final static long configPrecision=ConfigUtil.getIntegerProperty(CommonConfig.getProperties(),Constant.SYSTEM_CLOCK_REFRESH);

        private static final EfficientSystemClock instance=new EfficientSystemClock(configPrecision);
    }

    public static long now(){
        return EfficientSystemClockHolder.instance.now.get();
    }

    private EfficientSystemClock(long precision) {
        this.precision = precision;
        now = new AtomicLong(System.currentTimeMillis());
        schedulerClockUpdate();
    }

    private void schedulerClockUpdate() {
        ScheduledExecutorService scheduled = Executors.newSingleThreadScheduledExecutor();
        Thread thread=new Thread(this::update);
        thread.setDaemon(true);
        thread.setName("update system clock thread");
        scheduled.scheduleAtFixedRate(thread, precision, precision, TimeUnit.MILLISECONDS);
        logger.info("start efficient system clock");
    }


    private void update() {
        now.set(System.currentTimeMillis());
    }

}

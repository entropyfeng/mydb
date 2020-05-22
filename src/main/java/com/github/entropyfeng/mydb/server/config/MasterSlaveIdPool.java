package com.github.entropyfeng.mydb.server.config;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author entropyfeng
 */
public class MasterSlaveIdPool {
    public AtomicLong requestId=new AtomicLong(1);

}

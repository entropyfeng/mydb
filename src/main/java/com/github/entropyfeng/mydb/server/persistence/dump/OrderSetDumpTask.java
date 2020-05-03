package com.github.entropyfeng.mydb.server.persistence.dump;

import com.github.entropyfeng.mydb.server.config.ServerConfig;
import com.github.entropyfeng.mydb.server.config.Constant;
import com.github.entropyfeng.mydb.server.domain.OrderSetDomain;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author entropyfeng
 */
public class OrderSetDumpTask implements Callable<Boolean> {
    private CountDownLatch countDownLatch;
    private OrderSetDomain orderSetDomain;
    private Long timeStamp;

    public OrderSetDumpTask(CountDownLatch countDownLatch, OrderSetDomain domain, Long timeStamp) {
        this.countDownLatch = countDownLatch;
        this.orderSetDomain = domain;
        this.timeStamp = timeStamp;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(ServerConfig.dumpPath + timeStamp + Constant.ORDER_SET_SUFFIX);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
            OrderSetDomain.write(orderSetDomain, dataOutputStream);
            return true;
        } finally {
            countDownLatch.countDown();
        }
    }
}

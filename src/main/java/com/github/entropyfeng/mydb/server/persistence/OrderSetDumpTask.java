package com.github.entropyfeng.mydb.server.persistence;

import com.github.entropyfeng.mydb.config.Constant;
import com.github.entropyfeng.mydb.core.domain.OrderSetDomain;

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
    private String path;

    public OrderSetDumpTask(CountDownLatch countDownLatch, OrderSetDomain domain, String path) {
        this.countDownLatch = countDownLatch;
        this.orderSetDomain = domain;
        this.path = path;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path + Constant.ORDER_SET_SUFFIX);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
            OrderSetDomain.write(orderSetDomain, dataOutputStream);
            return true;
        } finally {
            countDownLatch.countDown();
        }
    }
}

package com.github.entropyfeng.mydb.server.persistence;

import com.github.entropyfeng.mydb.config.Constant;
import com.github.entropyfeng.mydb.server.core.domain.ValuesDomain;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author entropyfeng
 */
public class ValuesDumpTask implements Callable<Boolean> {

    private String path;
    private CountDownLatch countDownLatch;
    private ValuesDomain valuesDomain;

    public ValuesDumpTask(CountDownLatch countDownLatch, ValuesDomain domain, String path) {
        this.countDownLatch = countDownLatch;
        this.valuesDomain = domain;
        this.path = path;

    }

    @Override
    public Boolean call() throws Exception {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path + Constant.VALUES_SUFFIX);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
            ValuesDomain.write(valuesDomain, dataOutputStream);
            return true;
        } finally {
            countDownLatch.countDown();
        }
    }
}

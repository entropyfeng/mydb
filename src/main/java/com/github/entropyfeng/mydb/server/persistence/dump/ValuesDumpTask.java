package com.github.entropyfeng.mydb.server.persistence.dump;

import com.github.entropyfeng.mydb.server.config.ServerConfig;
import com.github.entropyfeng.mydb.server.config.Constant;
import com.github.entropyfeng.mydb.server.domain.ValuesDomain;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author entropyfeng
 */
public class ValuesDumpTask implements Callable<Boolean> {

    private Long timeStamp;
    private CountDownLatch countDownLatch;
    private ValuesDomain valuesDomain;

    public ValuesDumpTask(CountDownLatch countDownLatch, ValuesDomain domain, Long timeStamp) {
        this.countDownLatch = countDownLatch;
        this.valuesDomain = domain;
        this.timeStamp=timeStamp;

    }

    @Override
    public Boolean call() throws Exception {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(ServerConfig.dumpPath +timeStamp + Constant.VALUES_SUFFIX);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
            ValuesDomain.write(valuesDomain, dataOutputStream);
            return true;
        } finally {
            countDownLatch.countDown();
        }
    }
}

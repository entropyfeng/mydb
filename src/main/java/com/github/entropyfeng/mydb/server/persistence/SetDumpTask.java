package com.github.entropyfeng.mydb.server.persistence;

import com.github.entropyfeng.mydb.server.config.ServerConfig;
import com.github.entropyfeng.mydb.server.config.Constant;
import com.github.entropyfeng.mydb.server.core.domain.SetDomain;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author entropyfeng
 */
public class SetDumpTask implements Callable<Boolean> {
    private CountDownLatch countDownLatch;
    private SetDomain setDomain;
    private Long timeStamp;

    public SetDumpTask(CountDownLatch countDownLatch, SetDomain domain, Long timeStamp) {
        this.countDownLatch = countDownLatch;
        this.setDomain = domain;
        this.timeStamp=timeStamp;

    }

    @Override
    public Boolean call() throws Exception {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(ServerConfig.dumpPath +timeStamp + Constant.SET_SUFFIX);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
            SetDomain.write(setDomain, dataOutputStream);
            return true;
        } finally {
            countDownLatch.countDown();
        }
    }
}

package com.github.entropyfeng.mydb.server.persistence;

import com.github.entropyfeng.mydb.config.Constant;
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
    private String path;

    public SetDumpTask(CountDownLatch countDownLatch, SetDomain domain, String path) {
        this.countDownLatch = countDownLatch;
        this.setDomain = domain;
        this.path = path;

    }

    @Override
    public Boolean call() throws Exception {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path + Constant.SET_SUFFIX);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
            SetDomain.write(setDomain, dataOutputStream);
            return true;
        } finally {
            countDownLatch.countDown();
        }
    }
}

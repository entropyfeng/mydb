package com.github.entropyfeng.mydb.server.persistence;

import com.github.entropyfeng.mydb.config.Constant;
import com.github.entropyfeng.mydb.server.core.domain.HashDomain;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author entropyfeng
 */
public class HashDumpTask implements Callable<Boolean> {
    private CountDownLatch countDownLatch;
    private HashDomain hashDomain;
    private String path;

    public HashDumpTask(CountDownLatch countDownLatch, HashDomain domain, String path) {
        this.countDownLatch = countDownLatch;
        this.hashDomain = domain;
        this.path = path;
    }


    @Override
    public Boolean call() throws Exception {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path + Constant.HASH_SUFFIX);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
            HashDomain.write(hashDomain, dataOutputStream);
            return true;
        } finally {
            countDownLatch.countDown();
        }
    }
}

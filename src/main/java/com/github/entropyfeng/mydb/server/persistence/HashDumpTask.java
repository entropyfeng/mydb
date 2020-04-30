package com.github.entropyfeng.mydb.server.persistence;

import com.github.entropyfeng.mydb.server.config.ServerConfig;
import com.github.entropyfeng.mydb.server.config.Constant;
import com.github.entropyfeng.mydb.server.domain.HashDomain;

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
    private Long timeStamp;

    public HashDumpTask(CountDownLatch countDownLatch, HashDomain domain, Long timeStamp) {
        this.countDownLatch = countDownLatch;
        this.hashDomain = domain;
        this.timeStamp=timeStamp;
    }


    @Override
    public Boolean call() throws Exception {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(ServerConfig.dumpPath+timeStamp+Constant.HASH_SUFFIX);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
            HashDomain.write(hashDomain, dataOutputStream);
            return true;
        } finally {
            countDownLatch.countDown();
        }
    }
}

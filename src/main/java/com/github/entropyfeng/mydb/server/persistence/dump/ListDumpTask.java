package com.github.entropyfeng.mydb.server.persistence.dump;

import com.github.entropyfeng.mydb.server.config.ServerConfig;
import com.github.entropyfeng.mydb.server.config.ServerConstant;
import com.github.entropyfeng.mydb.server.domain.ListDomain;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author entropyfeng
 */
public class ListDumpTask implements Callable<Boolean> {

    private CountDownLatch countDownLatch;
    private ListDomain listDomain;
    private Long timeStamp;

    public ListDumpTask(CountDownLatch countDownLatch, ListDomain domain, Long timeStamp) {
        this.countDownLatch = countDownLatch;
        this.listDomain = domain;
        this.timeStamp = timeStamp;

    }

    @Override
    public Boolean call() throws Exception {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(ServerConfig.dumpPath + timeStamp + ServerConstant.LIST_SUFFIX);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
            ListDomain.write(listDomain, dataOutputStream);
            return true;
        } finally {
            countDownLatch.countDown();
        }
    }
}

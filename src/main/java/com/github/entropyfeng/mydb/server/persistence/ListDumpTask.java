package com.github.entropyfeng.mydb.server.persistence;

import com.github.entropyfeng.mydb.config.Constant;
import com.github.entropyfeng.mydb.server.core.domain.ListDomain;

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
    private String path;

    public ListDumpTask(CountDownLatch countDownLatch, ListDomain domain, String path) {
        this.countDownLatch = countDownLatch;
        this.listDomain = domain;
        this.path = path;

    }

    @Override
    public Boolean call() throws Exception {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path + Constant.LIST_SUFFIX);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
            ListDomain.write(listDomain, dataOutputStream);
            return true;
        } finally {
            countDownLatch.countDown();
        }
    }
}

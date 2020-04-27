package com.github.entropyfeng.mydb.server.persistence;

import com.github.entropyfeng.mydb.core.domain.ValuesDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author entropyfeng
 */
public class TestDumpTask implements Callable<Boolean> {

    private CountDownLatch countDownLatch;
    private ValuesDomain valuesDomain;
    private File file;

    public TestDumpTask(CountDownLatch countDownLatch, ValuesDomain domain, File file) {
        this.countDownLatch = countDownLatch;
        this.valuesDomain = domain;
        this.file = file;
    }

    @Override
    public Boolean call() throws Exception {
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
            ValuesDomain.write(valuesDomain, dataOutputStream);
        } finally {
            countDownLatch.countDown();
        }
        return true;
    }
}

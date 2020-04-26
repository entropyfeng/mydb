package com.github.entropyfeng.mydb.server.persistence;

import com.github.entropyfeng.mydb.core.domain.HashDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.concurrent.CountDownLatch;

/**
 * @author entropyfeng
 */
public class HashDumpTask implements Runnable {
    private static final Logger logger= LoggerFactory.getLogger(HashDumpTask.class);
    private CountDownLatch countDownLatch;
    private HashDomain hashDomain;
    private File file;
    private StringBuilder builder;
    public HashDumpTask(CountDownLatch countDownLatch, HashDomain domain, File file, StringBuilder resBuilder){
        this.countDownLatch=countDownLatch;
        this.hashDomain=domain;
        this.file=file;
        this.builder=resBuilder;
    }

    @Override
    public void run() {
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            DataOutputStream dataOutputStream=new DataOutputStream(fileOutputStream);
            HashDomain.write(hashDomain,dataOutputStream);
        } catch (FileNotFoundException e){
            builder.append("hash dump file not find");
            logger.info("hash dump file not find");
        } catch (IOException e){
            builder.append(e.getMessage());
            logger.info(e.getMessage());
        } finally {
            countDownLatch.countDown();
        }
    }
}

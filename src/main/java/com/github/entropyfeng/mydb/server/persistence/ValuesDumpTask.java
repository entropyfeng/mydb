package com.github.entropyfeng.mydb.server.persistence;

import com.github.entropyfeng.mydb.core.domain.ValuesDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.concurrent.CountDownLatch;

/**
 * @author entropyfeng
 */
public class ValuesDumpTask implements Runnable {
    private static final Logger logger= LoggerFactory.getLogger(ValuesDumpTask.class);
    private CountDownLatch countDownLatch;
    private ValuesDomain valuesDomain;
    private File file;
    private StringBuilder builder;
    public ValuesDumpTask(CountDownLatch countDownLatch, ValuesDomain domain, File file, StringBuilder resBuilder){
        this.countDownLatch=countDownLatch;
        this.valuesDomain=domain;
        this.file=file;
        this.builder=resBuilder;
    }

    @Override
    public void run() {
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            DataOutputStream dataOutputStream=new DataOutputStream(fileOutputStream);
            ValuesDomain.write(valuesDomain,dataOutputStream);
        } catch (FileNotFoundException e){
            builder.append("values dump file not find");
            logger.info("values dump file not find");
        } catch (IOException e){
            builder.append(e.getMessage());
            logger.info(e.getMessage());
        } finally {
            countDownLatch.countDown();
        }
    }
}

package com.github.entropyfeng.mydb.server.persistence;

import com.github.entropyfeng.mydb.core.domain.ListDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.concurrent.CountDownLatch;

/**
 * @author entropyfeng
 */
public class ListDumpTask implements Runnable {
    private static final Logger logger= LoggerFactory.getLogger(ListDumpTask.class);
    private CountDownLatch countDownLatch;
    private ListDomain listDomain;
    private File file;
    private StringBuilder builder;
    public ListDumpTask(CountDownLatch countDownLatch,ListDomain domain, File file, StringBuilder resBuilder){
        this.countDownLatch=countDownLatch;
        this.listDomain=domain;
        this.file=file;
        this.builder=resBuilder;
    }

    @Override
    public void run() {
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            DataOutputStream dataOutputStream=new DataOutputStream(fileOutputStream);
            ListDomain.write(listDomain,dataOutputStream);
        } catch (FileNotFoundException e){
            builder.append("list dump file not find");
            logger.info("list dump file not find");
        } catch (IOException e){
            builder.append(e.getMessage());
            logger.info(e.getMessage());
        } finally {
            countDownLatch.countDown();
        }
    }
}

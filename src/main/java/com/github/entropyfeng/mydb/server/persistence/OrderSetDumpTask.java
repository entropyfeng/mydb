package com.github.entropyfeng.mydb.server.persistence;

import com.github.entropyfeng.mydb.core.domain.OrderSetDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.concurrent.CountDownLatch;

/**
 * @author entropyfeng
 */
public class OrderSetDumpTask implements Runnable {
    private static final Logger logger= LoggerFactory.getLogger(OrderSetDumpTask.class);
    private CountDownLatch countDownLatch;
    private OrderSetDomain orderSetDomain;
    private File file;
    private StringBuilder builder;
    public OrderSetDumpTask(CountDownLatch countDownLatch, OrderSetDomain domain, File file, StringBuilder resBuilder){
        this.countDownLatch=countDownLatch;
        this.orderSetDomain=domain;
        this.file=file;
        this.builder=resBuilder;
    }

    @Override
    public void run() {
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            DataOutputStream dataOutputStream=new DataOutputStream(fileOutputStream);
            OrderSetDomain.write(orderSetDomain,dataOutputStream);
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

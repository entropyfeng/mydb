package com.github.entropyfeng.mydb.server.persistence;

import com.github.entropyfeng.mydb.core.domain.SetDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.concurrent.CountDownLatch;

/**
 * @author entropyfeng
 */
public class SetDumpTask implements Runnable {
    private static final Logger logger= LoggerFactory.getLogger(SetDumpTask.class);
    private CountDownLatch countDownLatch;
    private SetDomain setDomain;
    private File file;
    private StringBuilder builder;
    public SetDumpTask(CountDownLatch countDownLatch, SetDomain domain, File file, StringBuilder resBuilder){
        this.countDownLatch=countDownLatch;
        this.setDomain=domain;
        this.file=file;
        this.builder=resBuilder;
    }

    @Override
    public void run() {
        try {
            FileOutputStream fileOutputStream=new FileOutputStream(file);
            DataOutputStream dataOutputStream=new DataOutputStream(fileOutputStream);
            SetDomain.write(setDomain,dataOutputStream);
        } catch (FileNotFoundException e){
            builder.append("set dump file not find");
            logger.info("set dump file not find");
        } catch (IOException e){
            builder.append(e.getMessage());
            logger.info(e.getMessage());
        } finally {
            countDownLatch.countDown();
        }
    }
}

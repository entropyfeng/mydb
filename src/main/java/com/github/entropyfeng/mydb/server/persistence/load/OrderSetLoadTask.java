package com.github.entropyfeng.mydb.server.persistence.load;

import com.github.entropyfeng.mydb.server.domain.OrderSetDomain;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author entropyfeng
 */
public class OrderSetLoadTask implements Callable<OrderSetDomain> {

    private File file;
    private CountDownLatch countDownLatch;


    public OrderSetLoadTask(File file, CountDownLatch countDownLatch) {
        this.file = file;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public OrderSetDomain call() throws Exception {

        OrderSetDomain orderSetDomain = null;
        try {
            if (file!=null&&file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);
                orderSetDomain = OrderSetDomain.read(dataInputStream);
            }
        } finally {
            countDownLatch.countDown();
        }
        return orderSetDomain;
    }
}

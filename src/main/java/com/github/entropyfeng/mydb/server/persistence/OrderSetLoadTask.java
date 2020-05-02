package com.github.entropyfeng.mydb.server.persistence;

import com.github.entropyfeng.mydb.server.domain.OrderSetDomain;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @author entropyfeng
 */
public class OrderSetLoadTask implements Callable<OrderSetDomain> {


    private String[] fileNames;
    private String path;
    private CountDownLatch countDownLatch;
    private final Pattern orderSetPattern = compile("-orderSet\\.dump$");

    public OrderSetLoadTask(String[] fileNames, String path, CountDownLatch countDownLatch) {
        this.fileNames = fileNames;
        this.path = path;
        this.countDownLatch = countDownLatch;
    }


    @Override
    public OrderSetDomain call() throws Exception {

        Optional<String> orderSetFilename = Arrays.stream(fileNames).filter(s -> orderSetPattern.matcher(s).find()).max(String::compareTo);
        OrderSetDomain orderSetDomain = null;
        try {
            if (orderSetFilename.isPresent()) {
                File valuesDump = new File(path + orderSetFilename.get());

                FileInputStream fileInputStream = new FileInputStream(valuesDump);
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);
                orderSetDomain = OrderSetDomain.read(dataInputStream);
            }
        } finally {
            countDownLatch.countDown();
        }
        return orderSetDomain;
    }
}

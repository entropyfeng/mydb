package com.github.entropyfeng.mydb.server.persistence;

import com.github.entropyfeng.mydb.server.config.RegexConstant;
import com.github.entropyfeng.mydb.server.domain.ListDomain;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;


/**
 * @author entropyfeng
 */
public class ListLoadTask implements Callable<ListDomain> {


    private String[] fileNames;
    private String path;
    private CountDownLatch countDownLatch;


    public ListLoadTask(String[] fileNames, String path, CountDownLatch countDownLatch) {
        this.fileNames = fileNames;
        this.path = path;
        this.countDownLatch = countDownLatch;
    }


    @Override
    public ListDomain call() throws Exception {

        Optional<String> listFilename = Arrays.stream(fileNames).filter(s -> RegexConstant.LIST_PATTERN.matcher(s).find()).max(String::compareTo);
        ListDomain listDomain = null;
        try {
            if (listFilename.isPresent()) {
                File valuesDump = new File(path + listFilename.get());

                FileInputStream fileInputStream = new FileInputStream(valuesDump);
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);
                listDomain = ListDomain.read(dataInputStream);
            }
        } finally {
            countDownLatch.countDown();
        }
        return listDomain;
    }
}

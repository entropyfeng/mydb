package com.github.entropyfeng.mydb.server.persistence;

import com.github.entropyfeng.mydb.server.config.RegexConstant;
import com.github.entropyfeng.mydb.server.domain.ValuesDomain;

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
public class ValuesLoadTask implements Callable<ValuesDomain> {


    private String[] fileNames;
    private String path;
    private CountDownLatch countDownLatch;


    public ValuesLoadTask(String[] fileNames, String path, CountDownLatch countDownLatch) {
        this.fileNames = fileNames;
        this.path = path;
        this.countDownLatch = countDownLatch;
    }


    @Override
    public ValuesDomain call() throws Exception {


        Optional<String> valuesFilename = Arrays.stream(fileNames).filter(s -> RegexConstant.VALUES_PATTERN.matcher(s).find()).max(String::compareTo);
        ValuesDomain valuesDomain = null;
        try {
            if (valuesFilename.isPresent()) {
                File valuesDump = new File(path + valuesFilename.get());
                FileInputStream fileInputStream = new FileInputStream(valuesDump);
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);
                valuesDomain = ValuesDomain.read(dataInputStream);
            }
        } finally {
            countDownLatch.countDown();
        }
        return valuesDomain;
    }
}

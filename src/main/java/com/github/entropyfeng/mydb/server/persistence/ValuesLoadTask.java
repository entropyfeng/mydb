package com.github.entropyfeng.mydb.server.persistence;

import com.github.entropyfeng.mydb.server.core.domain.ValuesDomain;

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
public class ValuesLoadTask implements Callable<ValuesDomain> {


    private String[] fileNames;
    private String path;
    private CountDownLatch countDownLatch;
    private final Pattern valuesPattern = compile("-values.dump$");

    public ValuesLoadTask(String[] fileNames, String path, CountDownLatch countDownLatch) {
        this.fileNames = fileNames;
        this.path = path;
        this.countDownLatch = countDownLatch;
    }


    @Override
    public ValuesDomain call() throws Exception {

        Optional<String> valuesFilename = Arrays.stream(fileNames).filter(s -> !valuesPattern.matcher(s).matches()).max(String::compareTo);
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

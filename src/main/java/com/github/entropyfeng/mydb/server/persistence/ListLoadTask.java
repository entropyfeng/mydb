package com.github.entropyfeng.mydb.server.persistence;

import com.github.entropyfeng.mydb.core.domain.ListDomain;

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
public class ListLoadTask implements Callable<ListDomain> {


    private String[] fileNames;
    private String path;
    private CountDownLatch countDownLatch;
    private final Pattern listPattern = compile("-list.dump$");

    public ListLoadTask(String[] fileNames, String path, CountDownLatch countDownLatch) {
        this.fileNames = fileNames;
        this.path = path;
        this.countDownLatch = countDownLatch;
    }


    @Override
    public ListDomain call() throws Exception {

        Optional<String> listFilename = Arrays.stream(fileNames).filter(s -> !listPattern.matcher(s).matches()).max(String::compareTo);
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

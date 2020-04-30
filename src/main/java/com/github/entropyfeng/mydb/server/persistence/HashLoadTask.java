package com.github.entropyfeng.mydb.server.persistence;

import com.github.entropyfeng.mydb.server.domain.HashDomain;

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
public class HashLoadTask implements Callable<HashDomain> {


    private String[] fileNames;
    private String path;
    private CountDownLatch countDownLatch;
    private final Pattern hashPattern = compile("-hash.dump$");

    public HashLoadTask(String[] fileNames, String path, CountDownLatch countDownLatch) {
        this.fileNames = fileNames;
        this.path = path;
        this.countDownLatch = countDownLatch;
    }


    @Override
    public HashDomain call() throws Exception {

        Optional<String> hashFilename = Arrays.stream(fileNames).filter(s -> !hashPattern.matcher(s).matches()).max(String::compareTo);
        HashDomain hashDomain = null;
        try {
            if (hashFilename.isPresent()) {
                File valuesDump = new File(path + hashFilename.get());

                FileInputStream fileInputStream = new FileInputStream(valuesDump);
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);
                hashDomain = HashDomain.read(dataInputStream);
            }
        } finally {
            countDownLatch.countDown();
        }
        return hashDomain;
    }
}

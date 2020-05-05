package com.github.entropyfeng.mydb.server.persistence.load;

import com.github.entropyfeng.mydb.server.domain.HashDomain;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;


/**
 * @author entropyfeng
 */
public class HashLoadTask implements Callable<HashDomain> {


    private File file;
    private CountDownLatch countDownLatch;

    public HashLoadTask(File file, CountDownLatch countDownLatch) {
        this.file = file;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public HashDomain call() throws Exception {

        HashDomain hashDomain = null;
        try {
            if (file != null && file.exists()) {

                FileInputStream fileInputStream = new FileInputStream(file);
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);
                hashDomain = HashDomain.read(dataInputStream);
            }
        } finally {
            countDownLatch.countDown();
        }
        return hashDomain;
    }
}

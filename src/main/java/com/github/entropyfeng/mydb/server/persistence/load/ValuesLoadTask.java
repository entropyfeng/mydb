package com.github.entropyfeng.mydb.server.persistence.load;

import com.github.entropyfeng.mydb.server.domain.ValuesDomain;
import org.jetbrains.annotations.Nullable;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;


/**
 * @author entropyfeng
 */
public class ValuesLoadTask implements Callable<ValuesDomain> {


    private File file;
    private CountDownLatch countDownLatch;


    public ValuesLoadTask(File file, CountDownLatch countDownLatch) {

        this.file=file;
        this.countDownLatch = countDownLatch;
    }


    @Override
    public @Nullable ValuesDomain call() throws Exception {

       ValuesDomain valuesDomain = null;
        try {
            if (file!=null&&file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);
                valuesDomain = ValuesDomain.read(dataInputStream);
            }
        } finally {
            countDownLatch.countDown();
        }
        return valuesDomain;
    }
}

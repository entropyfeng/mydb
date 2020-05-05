package com.github.entropyfeng.mydb.server.persistence.load;

import com.github.entropyfeng.mydb.server.domain.SetDomain;
import org.jetbrains.annotations.Nullable;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author entropyfeng
 */
public class SetLoadTask implements Callable<SetDomain> {


    private File file;
    private CountDownLatch countDownLatch;


    public SetLoadTask(File file, CountDownLatch countDownLatch) {
        this.file = file;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public @Nullable SetDomain call() throws Exception {


        SetDomain setDomain = null;
        try {
            if (file!=null&&file.exists()) {
                FileInputStream fileInputStream = new FileInputStream(file);
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);
                setDomain = SetDomain.read(dataInputStream);
            }
        } finally {
            countDownLatch.countDown();
        }
        return setDomain;
    }
}

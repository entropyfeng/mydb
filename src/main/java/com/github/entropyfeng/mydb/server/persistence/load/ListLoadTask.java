package com.github.entropyfeng.mydb.server.persistence.load;

import com.github.entropyfeng.mydb.server.domain.ListDomain;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;


/**
 * @author entropyfeng
 */
public class ListLoadTask implements Callable<ListDomain> {


    private File file;
    private CountDownLatch countDownLatch;


    public ListLoadTask(File file, CountDownLatch countDownLatch) {
        this.file = file;
        this.countDownLatch = countDownLatch;
    }

    @Override
    public ListDomain call() throws Exception {

         ListDomain listDomain = null;
        try {
            if (file!=null&&file.exists()) {

                FileInputStream fileInputStream = new FileInputStream(file);
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);
                listDomain = ListDomain.read(dataInputStream);
            }
        } finally {
            countDownLatch.countDown();
        }
        return listDomain;
    }
}

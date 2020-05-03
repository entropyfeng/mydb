package com.github.entropyfeng.mydb.server.persistence.load;

import com.github.entropyfeng.mydb.server.config.RegexConstant;
import com.github.entropyfeng.mydb.server.domain.SetDomain;

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
public class SetLoadTask implements Callable<SetDomain> {


    private String[] fileNames;
    private String path;
    private CountDownLatch countDownLatch;


    public SetLoadTask(String[] fileNames, String path, CountDownLatch countDownLatch) {
        this.fileNames = fileNames;
        this.path = path;
        this.countDownLatch = countDownLatch;
    }


    @Override
    public SetDomain call() throws Exception {


        Optional<String> setFilename = Arrays.stream(fileNames).filter(s -> RegexConstant.SET_PATTERN.matcher(s).find()).max(String::compareTo);
        SetDomain setDomain = null;
        try {
            if (setFilename.isPresent()) {
                File setDump = new File(path + setFilename.get());
                FileInputStream fileInputStream = new FileInputStream(setDump);
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);
                setDomain = SetDomain.read(dataInputStream);
            }
        } finally {
            countDownLatch.countDown();
        }
        return setDomain;
    }
}

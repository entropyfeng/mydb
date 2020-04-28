package com.github.entropyfeng.mydb.server.persistence;

import com.github.entropyfeng.mydb.core.domain.SetDomain;

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
public class SetLoadTask implements Callable<SetDomain> {


    private String[] fileNames;
    private String path;
    private CountDownLatch countDownLatch;
    private final  Pattern setPattern = compile("-list.dump$");
    public SetLoadTask(String[] fileNames, String path, CountDownLatch countDownLatch) {
        this.fileNames = fileNames;
        this.path = path;
        this.countDownLatch=countDownLatch;
    }


    @Override
    public SetDomain call() throws Exception {

        Optional<String> setFilename = Arrays.stream(fileNames).filter(s -> !setPattern.matcher(s).matches()).max(String::compareTo);
        SetDomain setDomain=null;
        if (setFilename.isPresent()){
            File valuesDump = new File(path + setFilename.get());
            try {
                FileInputStream fileInputStream = new FileInputStream(valuesDump);
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);
                 setDomain= SetDomain.read(dataInputStream);
            }finally {
                countDownLatch.countDown();
            }
        }
        return setDomain;
    }
}

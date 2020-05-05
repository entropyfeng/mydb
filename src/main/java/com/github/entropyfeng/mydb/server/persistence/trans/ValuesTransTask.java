package com.github.entropyfeng.mydb.server.persistence.trans;

import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.server.PersistenceHelper;
import com.github.entropyfeng.mydb.server.domain.ValuesDomain;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author entropyfeng
 */
public class ValuesTransTask implements Callable<ValuesDomain> {

    private CountDownLatch countDownLatch;

    private Collection<ProtoBuf.ResBody> resBodies;

    private File file;

    public ValuesTransTask(CountDownLatch countDownLatch, Collection<ProtoBuf.ResBody> resBodies, File file) {
        this.countDownLatch = countDownLatch;
        this.resBodies = resBodies;
        this.file = file;
    }

    @Override
    public ValuesDomain call() throws Exception {
        ValuesDomain valuesDomain;
        try {
            PersistenceHelper.constructFile(resBodies, file);
            FileInputStream fileInputStream = new FileInputStream(file);
            valuesDomain = ValuesDomain.read(new DataInputStream(fileInputStream));
        } finally {
            countDownLatch.countDown();
        }

        return valuesDomain;
    }
}

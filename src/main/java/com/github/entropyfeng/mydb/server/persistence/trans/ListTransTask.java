package com.github.entropyfeng.mydb.server.persistence.trans;

import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.server.PersistenceHelper;
import com.github.entropyfeng.mydb.server.domain.ListDomain;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author entropyfeng
 */
public class ListTransTask implements Callable<ListDomain> {

    private CountDownLatch countDownLatch;

    private Collection<ProtoBuf.DataBody> resBodies;

    private File file;

    public ListTransTask(CountDownLatch countDownLatch, Collection<ProtoBuf.DataBody> resBodies, File file) {
        this.countDownLatch = countDownLatch;
        this.resBodies = resBodies;
        this.file = file;
    }

    @Override
    public ListDomain call() throws Exception {

        ListDomain listDomain;
        try {
            PersistenceHelper.constructFile(resBodies, file);
            FileInputStream fileInputStream = new FileInputStream(file);
            listDomain=ListDomain.read(new DataInputStream(fileInputStream));
        } finally {
            countDownLatch.countDown();
        }

        return listDomain;
    }
}

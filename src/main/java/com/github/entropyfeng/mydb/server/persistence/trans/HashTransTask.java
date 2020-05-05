package com.github.entropyfeng.mydb.server.persistence.trans;

import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.server.PersistenceHelper;
import com.github.entropyfeng.mydb.server.domain.HashDomain;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @author entropyfeng
 */
public class HashTransTask implements Callable<HashDomain> {

    private CountDownLatch countDownLatch;
    
    private Collection<ProtoBuf.ResBody> resBodies;
    
    private File file;

    public HashTransTask(CountDownLatch countDownLatch, Collection<ProtoBuf.ResBody> resBodies, File file) {
        this.countDownLatch = countDownLatch;
        this.resBodies = resBodies;
        this.file = file;
    }

    @Override
    public HashDomain call() throws Exception {
        
        HashDomain listDomain;
        try {
            PersistenceHelper.constructFile(resBodies, file);
            FileInputStream fileInputStream = new FileInputStream(file);
            listDomain=HashDomain.read(new DataInputStream(fileInputStream));
        } finally {
            countDownLatch.countDown();
        }

        return listDomain;
    }
}

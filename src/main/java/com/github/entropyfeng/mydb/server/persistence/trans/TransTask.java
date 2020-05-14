package com.github.entropyfeng.mydb.server.persistence.trans;

import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.server.config.ServerConstant;
import com.google.protobuf.ByteString;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * make each file convert to {@link Collection<com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.DataBody>}
 * and transport in the network.
 * @author entropyfeng
 */
public class TransTask implements Callable<Collection<ProtoBuf.DataBody>> {

    private CountDownLatch countDownLatch;

    private File file;

    public TransTask(CountDownLatch countDownLatch, File file) {
        this.countDownLatch = countDownLatch;
        this.file=file;
    }

    @Override
    public @Nullable Collection<ProtoBuf.DataBody> call() throws Exception {
        try {
            if (file==null){
                return new ArrayList<>(0);
            }
            RandomAccessFile randomAccessFile=new RandomAccessFile(file,"r");
            ArrayList<ProtoBuf.DataBody> resBodies = new ArrayList<>();
            final long length = randomAccessFile.length();
            long pos = 0;
            //每次读1M
            byte[] cache = new byte[ServerConstant.FILE_CHUCK_SIZE];
            while (pos + ServerConstant.FILE_CHUCK_SIZE <= length) {
                randomAccessFile.readFully(cache);
                resBodies.add(ProtoBuf.DataBody.newBuilder().setBytesValue(ByteString.copyFrom(cache)).build());
                pos += ServerConstant.FILE_CHUCK_SIZE;
                randomAccessFile.seek(pos);
            }
            final long other = length - pos;
            byte[] otherCache = new byte[(int) other];
            randomAccessFile.readFully(otherCache);
            resBodies.add(ProtoBuf.DataBody.newBuilder().setBytesValue(ByteString.copyFrom(otherCache)).build());
            return resBodies;
        }finally {
            countDownLatch.countDown();
        }

    }
}

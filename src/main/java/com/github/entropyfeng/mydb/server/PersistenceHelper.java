package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.server.config.Constant;
import com.github.entropyfeng.mydb.server.config.RegexConstant;
import com.github.entropyfeng.mydb.server.config.ServerConfig;
import com.github.entropyfeng.mydb.server.domain.*;
import com.github.entropyfeng.mydb.server.persistence.*;
import com.github.entropyfeng.mydb.server.persistence.dump.*;
import com.github.entropyfeng.mydb.server.persistence.load.*;
import com.google.protobuf.ByteString;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.*;

import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResBody;
import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;


/**
 * @author entropyfeng
 */
public class PersistenceHelper {

    private static final Logger logger = LoggerFactory.getLogger(PersistenceHelper.class);

    public static void dumpAll(ServerDomain serverDomain) {

        Long timeStamp = System.currentTimeMillis();

        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService service = new ThreadPoolExecutor(2, 5, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(), new DumpThreadFactory());

        Future<Boolean> valuesFuture = service.submit(new ValuesDumpTask(countDownLatch, serverDomain.valuesDomain, timeStamp));
        Future<Boolean> listFuture = service.submit(new ListDumpTask(countDownLatch, serverDomain.listDomain, timeStamp));
        Future<Boolean> setFuture = service.submit(new SetDumpTask(countDownLatch, serverDomain.setDomain, timeStamp));
        Future<Boolean> hashFuture = service.submit(new HashDumpTask(countDownLatch, serverDomain.hashDomain, timeStamp));
        Future<Boolean> orderSetFuture = service.submit(new OrderSetDumpTask(countDownLatch, serverDomain.orderSetDomain, timeStamp));

        try {
            countDownLatch.await();
            //一般不会触发中断事件
        } catch (InterruptedException e) {
            logger.error(e.getMessage());
        }

        //------values---------------

        try {
            valuesFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.info(e.getMessage());
        }

        //------set-----------------

        try {
            setFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.info(e.getMessage());
        }

        //--------list--------------
        try {
            listFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.info(e.getMessage());
        }

        //--------hash---------------

        try {
            hashFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.info(e.getMessage());
        }

        //-----orderSet----------------------

        try {
            orderSetFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.info(e.getMessage());
        }

        service.shutdown();
    }


    public static void clearAll(ServerDomain serverDomain) {

        serverDomain.valuesDomain.clear();
        serverDomain.listDomain.clear();
        serverDomain.setDomain.clear();
        serverDomain.hashDomain.clear();
        serverDomain.orderSetDomain.clear();
    }

    public static @NotNull ServerDomain load() {
        String path = ServerConfig.dumpPath;

        FilenameFilter filter = (dir, name) -> RegexConstant.BACK_UP_PATTERN.matcher(name).matches();
        File folder = new File(path);
        //如果不存在文件夹，则返回emptyDomain
        if (!folder.exists()) {
            logger.info("dump file folder not exists");
            return new ServerDomain();
        }
        String[] names = folder.list(filter);
        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService service = new ThreadPoolExecutor(1, 5, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(), new LoadThreadFactory());
        Future<ValuesDomain> valuesDomainFuture = service.submit(new ValuesLoadTask(names, path, countDownLatch));
        Future<ListDomain> listDomainFuture = service.submit(new ListLoadTask(names, path, countDownLatch));
        Future<SetDomain> setDomainFuture = service.submit(new SetLoadTask(names, path, countDownLatch));
        Future<HashDomain> hashDomainFuture = service.submit(new HashLoadTask(names, path, countDownLatch));
        Future<OrderSetDomain> orderSetDomainFuture = service.submit(new OrderSetLoadTask(names, path, countDownLatch));

        try {
            countDownLatch.await();
            //一般不会触发中断事件
        } catch (InterruptedException e) {
            logger.info(e.getMessage());
        }

        //---------------Values------------------
        ValuesDomain valuesDomain = null;
        try {
            valuesDomain = valuesDomainFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        }
        if (valuesDomain == null) {
            valuesDomain = new ValuesDomain();
        }
        //-----------list------------------------

        ListDomain listDomain = null;
        try {
            listDomain = listDomainFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        }
        if (listDomain == null) {
            listDomain = new ListDomain();
        }
        //-----------set------------------------
        SetDomain setDomain = null;
        try {
            setDomain = setDomainFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        }
        if (setDomain == null) {
            setDomain = new SetDomain();
        }
        //--------hash------------------------
        HashDomain hashDomain = null;
        try {
            hashDomain = hashDomainFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.info(e.getMessage());

        }
        if (hashDomain == null) {
            hashDomain = new HashDomain();
        }
        //--------orderSet----------------------
        OrderSetDomain orderSetDomain = null;
        try {
            orderSetDomain = orderSetDomainFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.info(e.getMessage());
        }
        if (orderSetDomain == null) {
            orderSetDomain = new OrderSetDomain();
        }
        service.shutdown();
        return new ServerDomain(valuesDomain, listDomain, setDomain, hashDomain, orderSetDomain);
    }

    public static @NotNull Pair<ResHead, Collection<ResBody>> singleDump(Callable<Boolean> callable) {

        boolean res = false;
        try {
            res = callable.call();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return ResServerHelper.boolRes(res);
    }


    public static void deleteDumpFiles() {
        logger.info("begin delete dumpFiles");

        File folder = new File(ServerConfig.dumpPath);
        if (folder.exists() && folder.isDirectory()) {
            deleteFile(folder);
        }
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    private static void deleteFile(File file) {
        if (file == null || !file.exists()) {
            return;
        }
        File[] files = file.listFiles();
        if (files != null) {
            for (File tempFile : files) {
                if (tempFile.isDirectory()) {
                    deleteFile(tempFile);
                } else {
                    tempFile.delete();
                }
            }
        }
    }


    public Pair<ResHead, Collection<ResBody>> transDumpFile() {

        ArrayList<ResBody> list = new ArrayList<>();
        String path = ServerConfig.dumpPath;

        FilenameFilter filter = (dir, name) -> RegexConstant.BACK_UP_PATTERN.matcher(name).matches();

        File file = new File(path);

        String[] fileNames = file.list(filter);

        if (fileNames != null) {
            Optional<String> valuesFilename = Arrays.stream(fileNames).filter(s -> RegexConstant.VALUES_PATTERN.matcher(s).find()).max(String::compareTo);
            Optional<String> listFilename = Arrays.stream(fileNames).filter(s -> RegexConstant.LIST_PATTERN.matcher(s).find()).max(String::compareTo);
            Optional<String> hashFilename = Arrays.stream(fileNames).filter(s -> RegexConstant.HASH_PATTERN.matcher(s).find()).max(String::compareTo);
            Optional<String> setFilename = Arrays.stream(fileNames).filter(s -> RegexConstant.SET_PATTERN.matcher(s).find()).max(String::compareTo);
            Optional<String> orderSetFilename = Arrays.stream(fileNames).filter(s -> RegexConstant.ORDER_SET_PATTERN.matcher(s).find()).max(String::compareTo);

        }

        Pair<ResHead, Collection<ResBody>> pair = new Pair<>(null, null);
        return pair;
    }

    public static @Nullable ArrayList<ResBody> handleFile(File file) {

        try {
            ArrayList<ResBody> resBodies = new ArrayList<>();
            RandomAccessFile randomAccessFile = new RandomAccessFile(file, "r");
            final long length = randomAccessFile.length();
            long pos = 0;
            //每次读1M
            byte[] cache = new byte[Constant.FILE_CHUCK_SIZE];
            while (pos + Constant.FILE_CHUCK_SIZE <= length) {
                randomAccessFile.readFully(cache);
                resBodies.add(ResBody.newBuilder().setBytesValue(ByteString.copyFrom(cache)).build());
                pos += Constant.FILE_CHUCK_SIZE;
                randomAccessFile.seek(pos);
            }
            final long other = length - pos;
            byte[] otherCache = new byte[(int) other];
            randomAccessFile.readFully(otherCache);
            resBodies.add(ResBody.newBuilder().setBytesValue(ByteString.copyFrom(otherCache)).build());
            return resBodies;
        } catch (IOException e) {
           logger.error(e.getMessage());
        }
        return null;
    }
    public static void constructFile(Collection<ResBody> resBodies,String suffix){
        String path= ServerConfig.dumpPath;
        File file=new File(path+System.currentTimeMillis()+suffix);
        FileOutputStream fileOutputStream= null;
        try {
            fileOutputStream = new FileOutputStream(file);
            BufferedOutputStream outputStream=new BufferedOutputStream(fileOutputStream);
            for(ResBody resBody:resBodies){
                outputStream.write(resBody.getBytesValue().toByteArray());
                outputStream.flush();
            }
            outputStream.flush();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

}

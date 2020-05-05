package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.server.config.RegexConstant;
import com.github.entropyfeng.mydb.server.config.ServerConfig;
import com.github.entropyfeng.mydb.server.domain.*;
import com.github.entropyfeng.mydb.server.persistence.DumpThreadFactory;
import com.github.entropyfeng.mydb.server.persistence.LoadThreadFactory;
import com.github.entropyfeng.mydb.server.persistence.PersistenceDomain;
import com.github.entropyfeng.mydb.server.persistence.TransThreadFactory;
import com.github.entropyfeng.mydb.server.persistence.dump.*;
import com.github.entropyfeng.mydb.server.persistence.load.*;
import com.github.entropyfeng.mydb.server.persistence.trans.TransTask;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;

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

        PersistenceDomain domain = getFiles();


        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService service = new ThreadPoolExecutor(1, 5, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(), new LoadThreadFactory());
        Future<ValuesDomain> valuesDomainFuture = service.submit(new ValuesLoadTask(domain.getValuesDumpFile(), countDownLatch));
        Future<ListDomain> listDomainFuture = service.submit(new ListLoadTask(domain.getListDumpFile(), countDownLatch));
        Future<SetDomain> setDomainFuture = service.submit(new SetLoadTask(domain.getSetDumpFile(), countDownLatch));
        Future<HashDomain> hashDomainFuture = service.submit(new HashLoadTask(domain.getHashDumpFile(), countDownLatch));
        Future<OrderSetDomain> orderSetDomainFuture = service.submit(new OrderSetLoadTask(domain.getOrderSetDumpFile(), countDownLatch));

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

    /**
     * load all dump files from file system,such as values、list、set、hash、orderSet.
     * these file is the latest dump file.
     * if one type of file is not exists,the correspond fileDomain is null.
     *
     * @return {@link PersistenceDomain}
     */
    @NotNull
    public static PersistenceDomain getFiles() {

        PersistenceDomain res = new PersistenceDomain();
        String path = ServerConfig.dumpPath;
        FilenameFilter filter = (dir, name) -> RegexConstant.BACK_UP_PATTERN.matcher(name).matches();
        File directory = new File(path);

        if (directory.isDirectory()) {
            File[] files = directory.listFiles(filter);
            Optional<File> valuesFile = matchSingleDump(files, RegexConstant.VALUES_PATTERN);
            Optional<File> listFile = matchSingleDump(files, RegexConstant.LIST_PATTERN);
            Optional<File> hashFile = matchSingleDump(files, RegexConstant.HASH_PATTERN);
            Optional<File> setFile = matchSingleDump(files, RegexConstant.SET_PATTERN);
            Optional<File> orderSetFile = matchSingleDump(files, RegexConstant.ORDER_SET_PATTERN);
            valuesFile.ifPresent(res::setValuesDumpFile);
            listFile.ifPresent(res::setListDumpFile);
            hashFile.ifPresent(res::setHashDumpFile);
            setFile.ifPresent(res::setSetDumpFile);
            orderSetFile.ifPresent(res::setOrderSetDumpFile);
        }


        return res;
    }


    /**
     * as the order of values->list->set->hash->orderSet dump
     *
     * @return {@link Pair} NotNull
     */
    @NotNull
    public static Pair<ResHead, Collection<ResBody>> transDumpFile() {

        ArrayList<ResBody> resBodies = new ArrayList<>();
        PersistenceDomain domain = getFiles();

        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService service = new ThreadPoolExecutor(1, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5), new TransThreadFactory());
        Future<Collection<ProtoBuf.ResBody>> valuesFuture = service.submit(new TransTask(countDownLatch, domain.getValuesDumpFile()));
        Future<Collection<ProtoBuf.ResBody>> listFuture = service.submit(new TransTask(countDownLatch, domain.getListDumpFile()));

        Future<Collection<ProtoBuf.ResBody>> setFuture = service.submit(new TransTask(countDownLatch, domain.getSetDumpFile()));

        Future<Collection<ProtoBuf.ResBody>> hashFuture = service.submit(new TransTask(countDownLatch, domain.getHashDumpFile()));

        Future<Collection<ProtoBuf.ResBody>> orderSetFuture = service.submit(new TransTask(countDownLatch, domain.getOrderSetDumpFile()));


        constructPartBody(valuesFuture, resBodies);
        constructPartBody(listFuture, resBodies);
        constructPartBody(setFuture, resBodies);
        constructPartBody(hashFuture, resBodies);
        constructPartBody(orderSetFuture, resBodies);


        ResHead resHead = ResHead.newBuilder().setSuccess(true).setResSize(resBodies.size()).build();

        return new Pair<>(resHead, resBodies);
    }

    /**
     * as the order of values->list->set->hash->orderSet dump
     *
     * @param pair the res pair
     * @return {@link PersistenceDomain}
     */
    public static PersistenceDomain dumpFromPair(@NotNull Pair<ResHead, Collection<ResBody>> pair) {


        ArrayList<ResBody> bodies = new ArrayList<>(pair.getValue());
        int currentPos = 0;

        //[from,end)
        //-----------values-------------------
        int valuesSize = bodies.get(currentPos).getIntValue();
        List<ResBody> valuesBody = bodies.subList(currentPos + 1, currentPos + 1 + valuesSize);
        currentPos += valuesSize;
        currentPos++;
        //----------list-----------------------
        int listSize = bodies.get(currentPos).getIntValue();
        List<ResBody> listBody = bodies.subList(currentPos + 1, currentPos + 1 + listSize);
        currentPos += listSize;
        currentPos++;

        //-----------set-------------------
        int setSize = bodies.get(currentPos).getIntValue();

        List<ResBody> setBody = bodies.subList(currentPos + 1, currentPos + 1 + setSize);
        currentPos += setSize;
        currentPos++;
        //---------hash------------------------
        int hashSize = bodies.get(currentPos).getIntValue();
        List<ResBody> hashBody = bodies.subList(currentPos + 1, currentPos + 1 + hashSize);
        currentPos += hashSize;
        currentPos++;

        //---------orderSet--------------------
        int orderSetSize = bodies.get(currentPos).getIntValue();
        List<ResBody> orderSetBody = bodies.subList(currentPos + 1, currentPos + orderSetSize);


        CountDownLatch countDownLatch = new CountDownLatch(5);


        return null;
    }

    private static void constructPartBody(@NotNull Future<Collection<ProtoBuf.ResBody>> future, ArrayList<ResBody> resBodies) {
        Collection<ResBody> collection = null;
        try {
            collection = future.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }

        if (collection == null) {
            resBodies.add(ProtoBuf.ResBody.newBuilder().setIntValue(0).build());
        } else {
            resBodies.add(ProtoBuf.ResBody.newBuilder().setIntValue(collection.size()).build());
            resBodies.addAll(collection);
        }
    }


    public static void constructFile(Collection<ResBody> resBodies, File file) throws IOException {

        FileOutputStream fileOutputStream;

        fileOutputStream = new FileOutputStream(file);
        BufferedOutputStream outputStream = new BufferedOutputStream(fileOutputStream);
        for (ResBody resBody : resBodies) {
            outputStream.write(resBody.getBytesValue().toByteArray());
            outputStream.flush();
        }
        outputStream.flush();

    }


    /**
     * find and load the least dump file for single type of dump file.
     *
     * @param files   the file list of all correspond dump files
     * @param pattern the pattern of matched single dump file (for example values.dump or list.dump or ....)
     * @return {@link Optional<File>}  the file,and the file is likely not present
     */
    @NotNull
    private static Optional<File> matchSingleDump(File[] files, Pattern pattern) {
        return Arrays.stream(files).filter(file -> pattern.matcher(file.getName()).find()).max(Comparator.comparing(File::getName));
    }

}

package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.server.config.ServerConstant;
import com.github.entropyfeng.mydb.server.config.RegexConstant;
import com.github.entropyfeng.mydb.server.config.ServerConfig;
import com.github.entropyfeng.mydb.server.domain.*;
import com.github.entropyfeng.mydb.server.persistence.*;
import com.github.entropyfeng.mydb.server.persistence.dump.*;
import com.github.entropyfeng.mydb.server.persistence.load.*;
import com.github.entropyfeng.mydb.server.persistence.trans.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.*;
import java.util.concurrent.*;
import java.util.regex.Pattern;

import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.DataBody;
import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;


/**
 * @author entropyfeng
 */
public class PersistenceHelper {

    private static final Logger logger = LoggerFactory.getLogger(PersistenceHelper.class);

    public static void dumpAll(ServerDomain serverDomain) {

        Long timeStamp = System.currentTimeMillis();

        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService service = new ThreadPoolExecutor(5, 5, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(), new DumpThreadFactory());

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

        PersistenceFileDomain domain = getFiles();


        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService service = new ThreadPoolExecutor(5, 5, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(), new LoadThreadFactory());
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


        PersistenceObjectDomain persistenceObjectDomain=  constructPersistenceDomain(valuesDomainFuture,listDomainFuture,setDomainFuture,hashDomainFuture,orderSetDomainFuture);
        service.shutdown();
        return new ServerDomain(persistenceObjectDomain);
    }

    public static @NotNull Pair<ResHead, Collection<DataBody>> singleDump(Callable<Boolean> callable) {

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
     * @return {@link PersistenceFileDomain}
     */
    @NotNull
    public static PersistenceFileDomain getFiles() {

        PersistenceFileDomain res = new PersistenceFileDomain();
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
    public static Pair<ResHead, Collection<DataBody>> transDumpFile() {

        ArrayList<DataBody> resBodies = new ArrayList<>();
        PersistenceFileDomain domain = getFiles();

        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService service = new ThreadPoolExecutor(1, 5, 100, TimeUnit.SECONDS, new ArrayBlockingQueue<>(5), new TransThreadFactory());
        Future<Collection<ProtoBuf.DataBody>> valuesFuture = service.submit(new TransTask(countDownLatch, domain.getValuesDumpFile()));
        Future<Collection<ProtoBuf.DataBody>> listFuture = service.submit(new TransTask(countDownLatch, domain.getListDumpFile()));

        Future<Collection<ProtoBuf.DataBody>> setFuture = service.submit(new TransTask(countDownLatch, domain.getSetDumpFile()));

        Future<Collection<ProtoBuf.DataBody>> hashFuture = service.submit(new TransTask(countDownLatch, domain.getHashDumpFile()));

        Future<Collection<ProtoBuf.DataBody>> orderSetFuture = service.submit(new TransTask(countDownLatch, domain.getOrderSetDumpFile()));


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
     * @return {@link PersistenceFileDomain}
     */
    public static PersistenceObjectDomain dumpAndReLoadFromPair(@NotNull Pair<ResHead, Collection<DataBody>> pair) {


        String prefix=ServerConfig.dumpPath+System.currentTimeMillis();
        ExecutorService service=new ThreadPoolExecutor(1,5,10,TimeUnit.SECONDS,new ArrayBlockingQueue<>(5),new AcceptTransThreadFactory());

        CountDownLatch countDownLatch = new CountDownLatch(5);
        ArrayList<DataBody> bodies = new ArrayList<>(pair.getValue());

        int currentPos = 0;

        //[from,end)
        //-----------values-------------------
        int valuesSize = bodies.get(currentPos).getIntValue();
        List<DataBody> valuesBody = bodies.subList(currentPos + 1, currentPos + 1 + valuesSize);
        currentPos += valuesSize;
        currentPos++;

       Future<ValuesDomain> valuesDomainFuture= service.submit(new ValuesTransTask(countDownLatch,valuesBody,new File(prefix+ ServerConstant.VALUES_SUFFIX)));

        //----------list-----------------------
        int listSize = bodies.get(currentPos).getIntValue();
        List<DataBody> listBody = bodies.subList(currentPos + 1, currentPos + 1 + listSize);
        currentPos += listSize;
        currentPos++;

        Future<ListDomain> listDomainFuture=service.submit(new ListTransTask(countDownLatch,listBody,new File(prefix+ ServerConstant.LIST_SUFFIX)));

        //-----------set-------------------
        int setSize = bodies.get(currentPos).getIntValue();

        List<DataBody> setBody = bodies.subList(currentPos + 1, currentPos + 1 + setSize);
        currentPos += setSize;
        currentPos++;

        Future<SetDomain> setDomainFuture=service.submit(new SetTransTask(countDownLatch,setBody,new File(prefix+ ServerConstant.SET_SUFFIX)));

        //---------hash------------------------
        int hashSize = bodies.get(currentPos).getIntValue();
        List<DataBody> hashBody = bodies.subList(currentPos + 1, currentPos + 1 + hashSize);
        currentPos += hashSize;
        currentPos++;

        Future<HashDomain> hashDomainFuture=service.submit(new HashTransTask(countDownLatch,hashBody,new File(prefix+ ServerConstant.HASH_SUFFIX)));
        //---------orderSet--------------------
        int orderSetSize = bodies.get(currentPos).getIntValue();
        List<DataBody> orderSetBody = bodies.subList(currentPos + 1, currentPos + orderSetSize);

        Future<OrderSetDomain> orderSetDomainFuture=service.submit(new OrderSetTransTask(countDownLatch,orderSetBody,new File(prefix+ ServerConstant.ORDER_SET_SUFFIX)));

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return constructPersistenceDomain(valuesDomainFuture,listDomainFuture,setDomainFuture,hashDomainFuture,orderSetDomainFuture);
    }

    private static void constructPartBody(@NotNull Future<Collection<ProtoBuf.DataBody>> future, ArrayList<DataBody> resBodies) {
        Collection<DataBody> collection = null;
        try {
            collection = future.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.error(e.getMessage());
        }

        if (collection == null) {
            resBodies.add(ProtoBuf.DataBody.newBuilder().setIntValue(0).build());
        } else {
            resBodies.add(ProtoBuf.DataBody.newBuilder().setIntValue(collection.size()).build());
            resBodies.addAll(collection);
        }
    }


    public static void constructFile(Collection<DataBody> resBodies, File file) throws IOException {

        FileOutputStream fileOutputStream;

        fileOutputStream = new FileOutputStream(file);
        BufferedOutputStream outputStream = new BufferedOutputStream(fileOutputStream);
        for (DataBody resBody : resBodies) {
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

    /**
     * 从硬盘中读取所有数据结构
     * @param valuesDomainFuture {@link ValuesDomain}
     * @param listDomainFuture {@link ListDomain}
     * @param setDomainFuture {@link SetDomain}
     * @param hashDomainFuture {@link HashDomain}
     * @param orderSetDomainFuture{@link OrderSetDomain}
     * @return {@link PersistenceObjectDomain}
     */
    private static PersistenceObjectDomain constructPersistenceDomain(Future<ValuesDomain> valuesDomainFuture, Future<ListDomain> listDomainFuture, Future<SetDomain> setDomainFuture, Future<HashDomain> hashDomainFuture, Future<OrderSetDomain> orderSetDomainFuture){

        //---------------Values------------------
        ValuesDomain valuesDomain = null;
        try {
            valuesDomain = valuesDomainFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        }
        if (valuesDomain==null){
            valuesDomain=new ValuesDomain();
        }

        //-----------list------------------------

        ListDomain listDomain = null;
        try {
            listDomain = listDomainFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        }

        if (listDomain==null){
            listDomain=new ListDomain();
        }
        //-----------set------------------------
        SetDomain setDomain = null;
        try {
            setDomain = setDomainFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.info(e.getMessage());
            e.printStackTrace();
        }
        if (setDomain==null){
            setDomain=new SetDomain();
        }

        //--------hash------------------------
        HashDomain hashDomain = null;
        try {
            hashDomain = hashDomainFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.info(e.getMessage());
        }

        if (hashDomain==null){
            hashDomain=new HashDomain();
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

        return new PersistenceObjectDomain(valuesDomain,listDomain,setDomain,hashDomain,orderSetDomain);
    }
}

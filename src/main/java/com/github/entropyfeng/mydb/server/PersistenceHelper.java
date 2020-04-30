package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.server.config.ServerConfig;
import com.github.entropyfeng.mydb.server.config.Constant;
import com.github.entropyfeng.mydb.server.domain.*;
import com.github.entropyfeng.mydb.server.persistence.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Collection;
import java.util.concurrent.*;
import java.util.regex.Pattern;

import static java.util.regex.Pattern.compile;

/**
 * @author entropyfeng
 */
public class PersistenceHelper {

    private static final Logger logger= LoggerFactory.getLogger(PersistenceHelper.class);

    public static void dumpAll(ServerDomain serverDomain) {

        Long timeStamp=System.currentTimeMillis();

        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService service = new ThreadPoolExecutor(2, 5, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(), new DumpFactory());

        Future<Boolean> valuesFuture = service.submit(new ValuesDumpTask(countDownLatch, serverDomain.valuesDomain, timeStamp));
        Future<Boolean> listFuture = service.submit(new ListDumpTask(countDownLatch, serverDomain.listDomain, timeStamp));
        Future<Boolean> setFuture = service.submit(new SetDumpTask(countDownLatch, serverDomain.setDomain, timeStamp));
        Future<Boolean> hashFuture = service.submit(new HashDumpTask(countDownLatch, serverDomain.hashDomain, timeStamp));
        Future<Boolean> orderSetFuture = service.submit(new OrderSetDumpTask(countDownLatch, serverDomain.orderSetDomain, timeStamp));

        try {
            countDownLatch.await();
            //一般不会触发中断事件
        } catch (InterruptedException e) {
            e.printStackTrace();
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

    public static  @NotNull ServerDomain load() {
        String path = ServerConfig.getProperties().getProperty(Constant.BACK_UP_PATH_NAME);
        Pattern backupPattern = compile("^[1-9]+(-hash.dump|-list.dump|-orderSet.dump|-set.dump|-values.dump)$");

        FilenameFilter filter = (dir, name) -> backupPattern.matcher(name).matches();
        File folder = new File(path);
        //如果不存在文件夹，则新建一个文件夹。
        if (!folder.exists()) {
            logger.info("dump file folder not exists");
            return new ServerDomain();
        }
        String[] names = folder.list(filter);
        CountDownLatch countDownLatch = new CountDownLatch(5);
        ExecutorService service = new ThreadPoolExecutor(1, 5, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(), new LoadFactory());
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


    public static  @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> singleDump(Callable<Boolean> callable) {

        boolean res=false;
        try {
            res= callable.call();
        } catch (Exception e) {
            logger.info(e.getMessage());
        }
        return ResServerHelper.boolRes(res);
    }

}

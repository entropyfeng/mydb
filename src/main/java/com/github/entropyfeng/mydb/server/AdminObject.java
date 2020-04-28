package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.config.CommonConfig;
import com.github.entropyfeng.mydb.config.Constant;
import com.github.entropyfeng.mydb.core.domain.*;
import com.github.entropyfeng.mydb.server.persistence.*;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.concurrent.*;
import java.util.regex.Pattern;

import static com.github.entropyfeng.mydb.config.Constant.*;
import static java.util.regex.Pattern.compile;

/**
 * @author entropyfeng
 */
public class AdminObject {
    private static final Logger logger = LoggerFactory.getLogger(AdminObject.class);
    private ServerDomain serverDomain;


    public AdminObject(ServerDomain serverDomain) {
        this.serverDomain = serverDomain;
    }

    public static int dumpAll(ServerDomain serverDomain) throws IOException {

        String filePath = CommonConfig.getProperties().getProperty(BACK_UP_PATH_NAME);
        long timeStamp = System.currentTimeMillis();
        String prefix = filePath + timeStamp;
        CountDownLatch countDownLatch = new CountDownLatch(5);

        File file = new File(filePath);
        if (!file.exists()) {
            file.mkdir();
        }
        //------values---------------
        StringBuilder valuesBuilder = new StringBuilder();
        File valuesDump = new File(prefix + VALUES_SUFFIX);

        new ValuesDumpFactory().newThread(new ValuesDumpTask(countDownLatch, serverDomain.valuesDomain, valuesDump, valuesBuilder)).start();

        //------set-----------------

        StringBuilder setBuilder = new StringBuilder();
        File setDump = new File(prefix + SET_SUFFIX);
        new SetDumpFactory().newThread(new SetDumpTask(countDownLatch, serverDomain.setDomain, setDump, setBuilder)).start();

        //--------list--------------

        StringBuilder listBuilder = new StringBuilder();
        File listDump = new File(prefix + LIST_SUFFIX);
        new ListDumpFactory().newThread(new ListDumpTask(countDownLatch, serverDomain.listDomain, listDump, listBuilder)).start();

        //--------hash---------------
        StringBuilder hashBuilder = new StringBuilder();
        File hashDump = new File(prefix + HASH_SUFFIX);
        new HashDumpFactory().newThread(new HashDumpTask(countDownLatch, serverDomain.hashDomain, hashDump, hashBuilder)).start();

        //-----orderSet----------------------
        StringBuilder orderSetBuilder = new StringBuilder();
        File orderSetDump = new File(prefix + ORDER_SET_SUFFIX);
        new OrderSetDumpFactory().newThread(new OrderSetDumpTask(countDownLatch, serverDomain.orderSetDomain, orderSetDump, orderSetBuilder)).start();


        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            logger.info(e.getMessage());
        }

        int successDump = 0;

        if (valuesBuilder.length() == 0) {
            successDump++;
        } else {
            logger.info(valuesBuilder.toString());
        }
        if (setBuilder.length() == 0) {
            successDump++;
        } else {
            logger.info(setBuilder.toString());
        }
        if (hashBuilder.length() == 0) {
            successDump++;
        } else {
            logger.info(hashBuilder.toString());
        }
        if (listBuilder.length() == 0) {
            successDump++;
        } else {
            logger.info(listBuilder.toString());
        }
        if (orderSetBuilder.length() == 0) {
            successDump++;
        } else {
            logger.info(orderSetBuilder.toString());
        }


        return successDump;

    }

    public @NotNull ServerDomain load() {
        String path = CommonConfig.getProperties().getProperty(Constant.BACK_UP_PATH_NAME);
        Pattern backupPattern = compile("^[1-9]+(-hash.dump|-list.dump|-orderSet.dump|-set.dump|-values.dump)$");

        FilenameFilter filter = (dir, name) -> backupPattern.matcher(name).matches();
        File folder = new File(path);
        //如果不存在文件夹，则新建一个文件夹。
        if (!folder.exists()) {
            folder.mkdir();
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
        ValuesDomain valuesDomain=null;
        try {
           valuesDomain= valuesDomainFuture.get();
        } catch (InterruptedException | ExecutionException e) {
           logger.info(e.getMessage());
        }
        if (valuesDomain==null){
            valuesDomain=new ValuesDomain();
        }

        //-----------list------------------------

        ListDomain listDomain=null;
        try {
            listDomain=listDomainFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.info(e.getMessage());
        }
        if (listDomain==null){
            listDomain=new ListDomain();
        }
        //-----------set------------------------

        SetDomain setDomain=null;
        try {
            setDomain=setDomainFuture.get();
        }catch (InterruptedException |ExecutionException e){
            logger.info(e.getMessage());
        }
        if (setDomain==null){
            setDomain=new SetDomain();
        }
        //--------hash------------------------
        HashDomain hashDomain=null;
        try {
         hashDomain=   hashDomainFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.info(e.getMessage());;
        }
        if (hashDomain==null){
            hashDomain=new HashDomain();
        }
        //--------orderSet----------------------
        OrderSetDomain orderSetDomain=null;
        try {
            orderSetDomain=orderSetDomainFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            logger.info(e.getMessage());
        }
        if (orderSetDomain==null){
            orderSetDomain=new OrderSetDomain();
        }
        return new ServerDomain(valuesDomain,listDomain,setDomain,hashDomain,orderSetDomain);
    }

    public static ServerDomain loadServerDomain(ServerDomain serverDomain) {

        CountDownLatch countDownLatch = new CountDownLatch(1);
        ExecutorService service = new ThreadPoolExecutor(2, 5, 10, TimeUnit.SECONDS, new LinkedBlockingDeque<>(), new DumpFactory());
        TestDumpTask testDumpTask = new TestDumpTask(countDownLatch, null, null);
        Future<Boolean> res = service.submit(testDumpTask);

        service.shutdown();
        System.out.println(res.isDone());

        return null;
    }

    public static void main(String[] args) {

        new AdminObject(null).load();
    }
}

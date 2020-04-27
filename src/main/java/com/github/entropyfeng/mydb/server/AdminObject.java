package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.config.CommonConfig;
import com.github.entropyfeng.mydb.config.Constant;
import com.github.entropyfeng.mydb.core.domain.ValuesDomain;
import com.github.entropyfeng.mydb.server.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.*;
import java.util.regex.Pattern;

import static com.github.entropyfeng.mydb.config.Constant.*;
import static java.util.regex.Pattern.compile;

/**
 * @author entropyfeng
 */
public class AdminObject {
   private static final Logger logger= LoggerFactory.getLogger(AdminObject.class);
   private ServerDomain serverDomain;



   public AdminObject(ServerDomain serverDomain){
       this.serverDomain=serverDomain;
   }

   private  boolean dumpValues(ValuesDomain valuesDomain,String filePath){
      //------values---------------
      long timeStamp=System.currentTimeMillis();
      String prefix=filePath+timeStamp;
      CountDownLatch countDownLatch=new CountDownLatch(1);
      StringBuilder valuesBuilder=new StringBuilder();
      File valuesDump=new File(prefix+VALUES_SUFFIX);
      new ValuesDumpFactory().newThread(new ValuesDumpTask(countDownLatch,valuesDomain,valuesDump,valuesBuilder)).start();
      try {
         countDownLatch.await();
      } catch (InterruptedException e) {
         logger.info(e.getMessage());
      }

      if (valuesBuilder.length()==0){
         return true;
      }else {
         logger.info(valuesBuilder.toString());
         return false;
      }


   }

   public static int dumpAll(ServerDomain serverDomain) throws IOException {

      String filePath= CommonConfig.getProperties().getProperty(BACK_UP_PATH_NAME);
      long timeStamp=System.currentTimeMillis();
      String prefix=filePath+timeStamp+"-";
      CountDownLatch countDownLatch=new CountDownLatch(5);

      //------values---------------
      StringBuilder valuesBuilder=new StringBuilder();
      File valuesDump=new File(prefix+VALUES_SUFFIX);
      new ValuesDumpFactory().newThread(new ValuesDumpTask(countDownLatch,serverDomain.valuesDomain,valuesDump,valuesBuilder)).start();

      //------set-----------------

      StringBuilder setBuilder=new StringBuilder();
      File setDump=new File(prefix+SET_SUFFIX);
      new SetDumpFactory().newThread(new SetDumpTask(countDownLatch,serverDomain.setDomain,setDump,setBuilder)).start();

      //--------list--------------

      StringBuilder listBuilder=new StringBuilder();
      File listDump=new File(prefix+ LIST_SUFFIX);
      new ListDumpFactory().newThread(new ListDumpTask(countDownLatch,serverDomain.listDomain,listDump,listBuilder)).start();

      //--------hash---------------
      StringBuilder hashBuilder=new StringBuilder();
      File hashDump=new File(prefix+ HASH_SUFFIX);
      new HashDumpFactory().newThread(new HashDumpTask(countDownLatch,serverDomain.hashDomain,hashDump,hashBuilder)).start();

      //-----orderSet----------------------
      StringBuilder orderSetBuilder=new StringBuilder();
      File orderSetDump=new File(prefix+ ORDER_SET_SUFFIX);
      new OrderSetDumpFactory().newThread(new OrderSetDumpTask(countDownLatch,serverDomain.orderSetDomain,orderSetDump,orderSetBuilder)).start();


      try {
         countDownLatch.await();
      } catch (InterruptedException e) {
        logger.info(e.getMessage());
      }

      int successDump=0;

      if (valuesBuilder.length()==0){
         successDump++;
      }else {
         logger.info(valuesBuilder.toString());
      }
      if (setBuilder.length()==0){
         successDump++;
      }else {
         logger.info(setBuilder.toString());
      }
      if (hashBuilder.length()==0){
         successDump++;
      }else {
         logger.info(hashBuilder.toString());
      }
      if (listBuilder.length()==0){
         successDump++;
      }else {
         logger.info(listBuilder.toString());
      }
      if (orderSetBuilder.length()==0){
         successDump++;
      }else {
         logger.info(orderSetBuilder.toString());
      }


      return successDump;

   }

   public void load(){
      String path = CommonConfig.getProperties().getProperty(Constant.BACK_UP_PATH_NAME);
      Pattern backupPattern = compile("^[1-9]+(-hash.dump|-list.dump|-orderSet.dump|-set.dump|-values.dump)$");
      Pattern valuesPattern = compile("-values.dump$");
      Pattern listPattern = compile("-list.dump$");
      Pattern setPattern = compile("-list.dump$");
      Pattern hashPattern = compile("-hash.dump$");
      Pattern orderSetPattern = compile("-orderSet.dump$");

      FilenameFilter filter = (dir, name) -> backupPattern.matcher(name).matches();
      File folder = new File(path);
      //如果不存在文件夹，则新建一个文件夹。
      if (!folder.exists()) {
         folder.mkdir();
      }

      String[] names = folder.list(filter);

      if (names != null) {
         Optional<String> valuesFilename = Arrays.stream(names).filter(s -> valuesPattern.matcher(s).matches()).max(String::compareTo);
         Optional<String> listFileName = Arrays.stream(names).filter(s -> listPattern.matcher(s).matches()).max(String::compareTo);
         Optional<String> setFileName = Arrays.stream(names).filter(s -> setPattern.matcher(s).matches()).max(String::compareTo);
         Optional<String> hashFileName = Arrays.stream(names).filter(s -> hashPattern.matcher(s).matches()).max(String::compareTo);
         Optional<String> orderSetFileName = Arrays.stream(names).filter(s -> orderSetPattern.matcher(s).matches()).max(String::compareTo);

         if (valuesFilename.isPresent()){
            File valuesDump=new File(path+valuesFilename.get());
            try {
               FileInputStream fileInputStream=new FileInputStream(valuesDump);

               DataInputStream dataInputStream=new DataInputStream(fileInputStream);
               ValuesDomain valuesDomain= ValuesDomain.read(dataInputStream);
            } catch (IOException e) {
               e.printStackTrace();
            }
         }
         if (listFileName.isPresent()){
            File listDump=new File(path+listFileName.get());
         }
         if (setFileName.isPresent()){
            File setFileDump=new File(path+setFileName.get());
         }
         if (hashFileName.isPresent()){
            File hashFileDump=new File(path+hashFileName.get());
         }
         if (orderSetFileName.isPresent()){
            File orderSetFileDump=new File(path+orderSetFileName.get());
         }






      }
   }

   public static ServerDomain loadServerDomain(ServerDomain serverDomain){

      CountDownLatch countDownLatch=new CountDownLatch(1);
      ExecutorService service=new ThreadPoolExecutor(2,5,10,TimeUnit.SECONDS,new LinkedBlockingDeque<>(),new DumpFactory());
      TestDumpTask testDumpTask=new TestDumpTask(countDownLatch,null,null);
       Future<Boolean> res=  service.submit(testDumpTask);

      System.out.println(res.isDone());

      service.shutdown();
      return null;
   }

   public static void main(String[] args) {
      loadServerDomain(null);
   }
}

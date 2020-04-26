package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.core.domain.ValuesDomain;
import com.github.entropyfeng.mydb.server.persistence.*;
import org.checkerframework.checker.units.qual.C;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.*;

/**
 * @author entropyfeng
 */
public class AdminObject {
   private static final Logger logger= LoggerFactory.getLogger(AdminObject.class);
   private ServerDomain serverDomain;

   public static final String VALUES_SUFFIX="values.dump";
   public static final String HASH_SUFFIX="hash.dump";
   public static final String SET_SUFFIX="set.dump";
   public static final String LIST_SUFFIX="list.dump";
   public static final String ORDER_SET_SUFFIX="orderSet.dump";

   public AdminObject(ServerDomain serverDomain){
       this.serverDomain=serverDomain;
   }

   private static boolean dumpValues(ValuesDomain valuesDomain,String filePath){
      //------values---------------
      long timeStamp=System.currentTimeMillis();
      String prefix=filePath+timeStamp+"-";
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

   public static int dumpAll(ServerDomain serverDomain,String filePath) throws IOException {

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

   public static ServerDomain loadServerDomain(){

      return null;
   }
}

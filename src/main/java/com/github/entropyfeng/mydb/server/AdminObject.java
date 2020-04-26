package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.core.domain.ListDomain;
import com.github.entropyfeng.mydb.server.persistence.*;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.*;

/**
 * @author entropyfeng
 */
public class AdminObject {
   private ServerDomain serverDomain;

   public static final String VALUES_SUFFIX="values.dump";
   public static final String HASH_SUFFIX="hash.dump";
   public static final String SET_SUFFIX="set.dump";
   public static final String LIST_SUFFIX="list.dump";
   public static final String ORDER_SET_SUFFIX="orderSet.dump";

   public AdminObject(ServerDomain serverDomain){
       this.serverDomain=serverDomain;
   }

   private void dumpValues(){

   }

   public void dump() throws IOException {

      String filePath="./";
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
         e.printStackTrace();
      }


   }

}

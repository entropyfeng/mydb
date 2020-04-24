package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

/**
 * @author entropyfeng
 */
public class AdminObject {
   private ServerDomain serverDomain;

   public AdminObject(ServerDomain serverDomain){
       this.serverDomain=serverDomain;
   }

   public void dump() throws IOException {

      long timeStamp=System.currentTimeMillis();



      File file=new File("./"+timeStamp+".values.dump");

      FileOutputStream fileOutputStream=new FileOutputStream(file);

      ObjectOutputStream objectOutputStream=new ObjectOutputStream(fileOutputStream);
      objectOutputStream.writeObject(serverDomain.valuesDomain);
      objectOutputStream.close();



   }

}

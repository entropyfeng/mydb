package com.github.entropyfeng.mydb.client.conn;


import com.github.entropyfeng.mydb.client.TurtleClient;
import com.github.entropyfeng.mydb.common.expection.TurtleTimeOutException;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import io.netty.channel.Channel;


import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

/**
 * @author entropyfeng
 */
public class TurtleClientChannelFactory {

    private static volatile Channel channel;


    public static class TurtleClientHolder {
       static {
           TurtleClient client=new TurtleClient();
           try {
               client.start();
           }catch (InterruptedException e){
               e.printStackTrace();
           }
       }

    }

    public static void setChannel(Channel channel) {
        TurtleClientChannelFactory.channel = channel;
    }



    public static ConcurrentHashMap<Long, TurtleProtoBuf.ResponseData> resMap = new ConcurrentHashMap<>();

    public static Channel getChannel() {

        if (channel==null){

        }

        return channel;
    }

    public static TurtleProtoBuf.ResponseData execute(TurtleProtoBuf.ClientCommand command)throws TurtleTimeOutException {
        if (channel==null){
        }
        if (channel!=null&& channel.isWritable()) {
            TurtleProtoBuf.ResponseData responseData=null;
            channel.writeAndFlush(command);
            //blocking....
            while (!resMap.containsKey(command.getRequestId())) {
              responseData= resMap.get(command.getRequestId());
            }
            resMap.remove(command.getRequestId());
            return responseData;
        }else{
          throw new TurtleTimeOutException();
        }
    }
}

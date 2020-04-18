package com.github.entropyfeng.mydb.client.conn;

import com.github.entropyfeng.mydb.common.CommonCommand;
import com.github.entropyfeng.mydb.common.exception.TurtleTimeOutException;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import io.netty.channel.Channel;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author entropyfeng
 */
public class ClientExecute {
    public static ConcurrentHashMap<Long, TurtleProtoBuf.ResponseData> resMap = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<Long, Collection<TurtleProtoBuf.ResponseData>> collectionResMap=new ConcurrentHashMap<>();

    public static TurtleProtoBuf.ResponseData singleExecute(TurtleProtoBuf.ClientCommand command)  {
        System.out.println("single execute");
        Channel channel=TurtleClientChannelFactory.getChannel();
        if (channel!=null) {
            TurtleProtoBuf.ResponseData responseData=null;
            System.out.println("write message");
            channel.writeAndFlush(command);
            System.out.println("after write");

            //blocking....
            while (!resMap.containsKey(command.getRequestId())) {
                responseData= resMap.get(command.getRequestId());
            }
            resMap.remove(command.getRequestId());
            System.out.println("single execute end");
            return responseData;
        }else{
            throw new TurtleTimeOutException();
        }
    }

    public static Collection<TurtleProtoBuf.ResponseData> collectionExecute(TurtleProtoBuf.ClientCommand command)throws TurtleTimeOutException {

        return null;
    }
}

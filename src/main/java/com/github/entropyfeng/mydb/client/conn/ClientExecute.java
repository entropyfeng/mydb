package com.github.entropyfeng.mydb.client.conn;

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

    public static TurtleProtoBuf.ResponseData singleExecute(TurtleProtoBuf.ClientCommand command){
        Channel channel=TurtleClientChannelFactory.getChannel();
        if (channel!=null) {
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

    public static Collection<TurtleProtoBuf.ResponseData> collectionExecute(TurtleProtoBuf.ClientCommand command)throws TurtleTimeOutException {

        return null;
    }
}

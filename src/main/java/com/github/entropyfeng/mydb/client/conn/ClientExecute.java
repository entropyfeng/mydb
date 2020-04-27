package com.github.entropyfeng.mydb.client.conn;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.common.exception.TurtleTimeOutException;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import io.netty.channel.Channel;

import java.util.Collection;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @author entropyfeng
 */
public class ClientExecute {

    public static ConcurrentHashMap<Long, TurtleProtoBuf.ResponseData> resMap = new ConcurrentHashMap<>();

    public static AtomicLong singleId = new AtomicLong(1);

    public static AtomicLong collectionId=new AtomicLong(1);

    public static ConcurrentHashMap<Long, Collection<TurtleProtoBuf.ResponseData>> collectionResMap = new ConcurrentHashMap<>();


    public static TurtleProtoBuf.ResponseData singleExecute(ClientCommandBuilder commandBuilder) {


        Channel channel = TurtleClientChannelFactory.getChannel();
        if (channel != null) {
            TurtleProtoBuf.ResponseData responseData;
            long requestId= singleId.getAndIncrement();
            commandBuilder.writeChannel(channel,requestId);

            //blocking....
            while (!resMap.containsKey(requestId)) {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
            responseData = resMap.get(requestId);
            resMap.remove(requestId);
            return responseData;
        } else {
            throw new TurtleTimeOutException();
        }
    }

    public static Collection<TurtleProtoBuf.ResponseData> collectionExecute(ClientCommandBuilder command) throws TurtleTimeOutException {
        Channel channel = TurtleClientChannelFactory.getChannel();
        if (channel != null) {
            Long requestId=collectionId.getAndIncrement();
            command.writeChannel(channel,requestId);
            Collection<TurtleProtoBuf.ResponseData> responseData ;
            //blocking....
            while (!collectionResMap.containsKey(requestId)) {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            responseData = collectionResMap.get(requestId);
            collectionResMap.remove(requestId);
            return responseData;
        } else {
            throw new TurtleTimeOutException();
        }
    }
}

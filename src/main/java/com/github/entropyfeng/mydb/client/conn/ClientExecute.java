package com.github.entropyfeng.mydb.client.conn;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.client.TurtleClient;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.exception.TurtleTimeOutException;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.DataBody;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @author entropyfeng
 */
public class ClientExecute {
    public ClientExecute(String host,Integer port) {
       globalMap = new ConcurrentHashMap<>();
       turtleClient=new TurtleClient(host,port,this);
       channel= turtleClient.getChannel();
    }


    private AtomicLong idPool=new AtomicLong(1);
    private TurtleClient turtleClient;
    private Channel channel ;
    private static final Logger logger= LoggerFactory.getLogger(ClientExecute.class);

    private  ConcurrentHashMap<Long, Pair<ResHead, Collection<DataBody>>> globalMap ;

    public  Pair<ResHead, Collection<DataBody>> execute(ClientCommandBuilder commandBuilder) {


        if (channel != null) {
            Pair<ResHead, Collection<DataBody>> responseData;
            Long requestId = idPool.getAndIncrement();
            commandBuilder.writeChannel(channel, requestId);

            //blocking....
            while (!globalMap.containsKey(requestId)) {

            }

            responseData = globalMap.get(requestId);
            globalMap.remove(requestId);
            return responseData;
        } else {
            throw new TurtleTimeOutException();
        }
    }

    public  boolean closeClient(){
        Channel channel = turtleClient.getChannel();

        channel.close().syncUninterruptibly();

        return true;
    }
    public void dispatch(Long responseId,Pair<ResHead,Collection<DataBody>> pair){

        globalMap.put(responseId, pair);
    }
}

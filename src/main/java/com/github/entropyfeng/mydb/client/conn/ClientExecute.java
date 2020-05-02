package com.github.entropyfeng.mydb.client.conn;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.common.exception.TurtleTimeOutException;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResBody;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;
import com.github.entropyfeng.mydb.common.Pair;
import io.netty.channel.Channel;

import java.util.Collection;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicLong;


/**
 * @author entropyfeng
 */
public class ClientExecute {

    public static ConcurrentHashMap<Long,Pair<ResHead,Collection<ResBody>>> resMap=new ConcurrentHashMap<>();

    public static AtomicLong requestIdPool = new AtomicLong(1);

    public static Pair<ResHead, Collection<ResBody>> execute(ClientCommandBuilder commandBuilder){

        Channel channel = TurtleClientChannelFactory.getChannel();
        if (channel != null) {
            Pair<ResHead, Collection<ResBody>> responseData;
            long requestId= requestIdPool.getAndIncrement();
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

}

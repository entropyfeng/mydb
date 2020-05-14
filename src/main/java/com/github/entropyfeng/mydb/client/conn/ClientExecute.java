package com.github.entropyfeng.mydb.client.conn;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.RequestIdPool;
import com.github.entropyfeng.mydb.common.exception.TurtleTimeOutException;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.DataBody;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @author entropyfeng
 */
public class ClientExecute {

    public static ConcurrentHashMap<Long, Pair<ResHead, Collection<DataBody>>> resMap = new ConcurrentHashMap<>();

    private static final Logger logger= LoggerFactory.getLogger(ClientExecute.class);

    public static Pair<ResHead, Collection<DataBody>> execute(ClientCommandBuilder commandBuilder) {

        Channel channel = TurtleClientChannelFactory.getChannel();
        if (channel != null) {
            Pair<ResHead, Collection<DataBody>> responseData;
            Long requestId = RequestIdPool.getAndIncrement();
            commandBuilder.writeChannel(channel, requestId);

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

    public static boolean closeClient(){
        Channel channel = TurtleClientChannelFactory.getChannel();
        try {
            channel.close().sync();
        } catch (InterruptedException e) {
            logger.error(e.getCause().toString());
            return false;
        }
        return true;
    }
}

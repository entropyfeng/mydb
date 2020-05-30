package com.github.entropyfeng.mydb.client.asy;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.client.TurtleClient;
import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.client.conn.IClientExecute;
import com.github.entropyfeng.mydb.common.Pair;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.DataBody;
import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;

/**
 * @author entropyfeng
 */
public class AsyClientExecute implements IClientExecute {

    public AsyClientExecute(String host, Integer port) {

        turtleClient = new TurtleClient(host, port, this);
        channel = turtleClient.getChannel();
    }


    private AtomicLong idPool = new AtomicLong(1);
    private TurtleClient turtleClient;
    private Channel channel;
    private static final Logger logger = LoggerFactory.getLogger(ClientExecute.class);

    private ConcurrentHashMap<Long,CompletableFuture<Pair<ResHead, Collection<DataBody>>>> futureMap=new ConcurrentHashMap<>();
    public CompletableFuture<Pair<ResHead, Collection<DataBody>>> execute(ClientCommandBuilder commandBuilder) {

        Long requestId=idPool.getAndIncrement();
        commandBuilder.writeChanel(channel,requestId);
        CompletableFuture<Pair<ResHead, Collection<DataBody>>> future=new CompletableFuture<>();
        futureMap.put(requestId,future);
        return future;

    }

    @Override
    public void dispatch(Long responseId, Pair<ResHead, Collection<DataBody>> pair) {

        CompletableFuture<Pair<ResHead, Collection<DataBody>>> future=  futureMap.remove(responseId);
        if (future!=null){
            future.complete(pair);
        }
    }
    public  boolean closeClient(){
        Channel channel = turtleClient.getChannel();
        channel.close().syncUninterruptibly();
        return true;
    }
}

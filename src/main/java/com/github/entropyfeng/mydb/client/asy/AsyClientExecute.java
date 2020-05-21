package com.github.entropyfeng.mydb.client.asy;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.client.TurtleClient;
import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.client.conn.IClientExecute;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.exception.TurtleTimeOutException;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
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

    public Pair<ResHead, Collection<DataBody>> execute(ClientCommandBuilder commandBuilder) {


        if (channel != null && channel.isActive()) {
            Long requestId = idPool.getAndIncrement();
            commandBuilder.writeChannel(channel, requestId);
        }
           throw new TurtleTimeOutException();

    }

    @Override
    public void dispatch(Long responseId, Pair<ResHead, Collection<DataBody>> pair) {

    }
}

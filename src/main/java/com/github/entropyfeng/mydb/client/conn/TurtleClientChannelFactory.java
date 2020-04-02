package com.github.entropyfeng.mydb.client.conn;


import com.github.entropyfeng.mydb.client.TurtleClient;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import io.netty.channel.Channel;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author entropyfeng
 */
public class TurtleClientChannelFactory {

    private static volatile Channel channel;
    private static volatile boolean alive = false;

    public static class TurtleClientHolder {

    }

    public static void setChannel(Channel channel) {
        TurtleClientChannelFactory.channel = channel;
    }

    public static ConcurrentHashMap<Long, TurtleProtoBuf.ResponseData> resMap = new ConcurrentHashMap<>();

    public static Channel getChannel() {

        return channel;
    }

    public static void execute(TurtleProtoBuf.ClientCommand command) {

        if (alive && channel.isWritable()) {
            TurtleProtoBuf.ResponseData responseData=null;
            channel.writeAndFlush(command);
            while (!resMap.containsKey(command.getRequestId())) {
              responseData= resMap.get(command.getRequestId());
            }

        }
    }
}

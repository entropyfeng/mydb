package com.github.entropyfeng.mydb.client.conn;


import io.netty.channel.Channel;

/**
 * @author entropyfeng
 */
public class TurtleClientChannelFactory {

   private static volatile Channel channel;
   private static volatile boolean alive=false;
    public static void setChannel(Channel channel) {
        TurtleClientChannelFactory.channel = channel;
    }

    public static Channel getChannel() {

        return channel;
    }
}

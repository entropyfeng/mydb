package com.github.entropyfeng.mydb.client.conn;


import com.github.entropyfeng.mydb.client.TurtleClient;
import io.netty.channel.Channel;

/**
 * @author entropyfeng
 */
public class TurtleClientChannelFactory {

    /**
     * 静态内部类延迟初始化
     */
    private static class TurtleClientHolder{
        public static TurtleClient client = new TurtleClient();
    }
    /**
     * @return {@link Channel}
     */
    public static Channel getChannel() {

        return TurtleClientHolder.client.getChannel();
    }

}

package com.github.entropyfeng.mydb.client.conn;


import com.github.entropyfeng.mydb.client.TurtleClient;
import io.netty.channel.Channel;

/**
 * @author entropyfeng
 */
public class TurtleClientChannelFactory {

    private static volatile Channel channel;

    /**
     * 双重检查锁单例模式
     * @return {@link Channel}
     */
    public static Channel getChannel() {

        if (channel==null){
            synchronized (TurtleClientChannelFactory.class){
                if (channel==null){
                    TurtleClient client=new TurtleClient();
                    try {

                        client.start();
                        channel=client.getChannel();
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return channel;
    }

}

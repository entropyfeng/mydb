package com.github.entropyfeng.mydb.client.conn;


import com.github.entropyfeng.mydb.client.TurtleClient;
import io.netty.channel.Channel;

/**
 * @author entropyfeng
 */
public class TurtleClientChannelFactory {

    private static volatile Channel channel;
    private static volatile boolean ready=false;

    public static void setChannel(Channel channel) {
        TurtleClientChannelFactory.channel = channel;
    }

    public static void setReady(boolean ready) {
        TurtleClientChannelFactory.ready = ready;
    }


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
                        while (!ready){
                            //blocking
                        }
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return channel;
    }


}

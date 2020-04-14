package com.github.entropyfeng.mydb.client.conn;


import com.github.entropyfeng.mydb.client.TurtleClient;
import com.github.entropyfeng.mydb.common.expection.TurtleTimeOutException;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import io.netty.channel.Channel;


import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeoutException;

/**
 * @author entropyfeng
 */
public class TurtleClientChannelFactory {

    private static volatile Channel channel;


    public static void setChannel(Channel channel) {
        TurtleClientChannelFactory.channel = channel;
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
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        }
        return channel;
    }


}

package com.github.entropyfeng.mydb.client.conn;


import com.github.entropyfeng.mydb.client.TurtleClient;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author entropyfeng
 */
public class TurtleClientChannelFactory {

    private static Logger logger= LoggerFactory.getLogger(TurtleClientChannelFactory.class);
    private static TurtleClient client = new TurtleClient();

    /**
     * 双重检查锁单例模式
     *
     * @return {@link Channel}
     */
    public static Channel getChannel() {

 /*       if (channel == null) {
            synchronized (TurtleClientChannelFactory.class) {
                if (channel == null) {

                }
            }
        }*/
        return client.getChannel();
    }

}

package com.github.entropyfeng.mydb.server.factory;

import com.github.entropyfeng.mydb.server.ServerDomain;
import com.github.entropyfeng.mydb.server.TurtleServer;
import com.github.entropyfeng.mydb.server.command.ClientRequest;
import com.github.entropyfeng.mydb.server.config.ServerConfig;
import com.github.entropyfeng.mydb.server.handler.TurtleServerHandler;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ThreadFactory;

/**
 * @author entropyfeng
 */
public class MasterSlaveThreadFactory implements ThreadFactory {
    @Override
    public Thread newThread(@NotNull Runnable r) {
        return new Thread(r, "masterSlave Thread");
    }
    /* have some error
    *
    *  */

    public static void daemon(){
        while (ServerConfig.masterSlaveFlag.get()){
           ClientRequest request= TurtleServerHandler.masterQueue.poll();
           if (request!=null){
               TurtleServerHandler.serverMap.forEach((channelId, channel) -> {
                   channel.write(request);
               });
           }
        }
    }
}

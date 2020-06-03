package com.github.entropyfeng.mydb.server.ms;

import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.server.command.ClientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author entropyfeng
 */
public class CommandTransTask implements Runnable {


    private static final Logger logger= LoggerFactory.getLogger(CommandTransTask.class);
    private ConcurrentHashMap<InetSocketAddress, ClientExecute> exeMap;
    ConcurrentLinkedQueue<ClientRequest> masterQueue;
    public CommandTransTask(ConcurrentHashMap<InetSocketAddress, ClientExecute> exeMap, ConcurrentLinkedQueue<ClientRequest> masterQueue){
        this.exeMap=exeMap;
        this.masterQueue=masterQueue;
    }

    @Override
    public void run() {

        //noinspection InfiniteLoopStatement
        while (true){
           ClientRequest request= masterQueue.poll();
           if (request!=null){
               logger.info(request.getOperationName());
               exeMap.forEach((address, execute) -> execute.commandTrans(request));
           }
        }
    }
}

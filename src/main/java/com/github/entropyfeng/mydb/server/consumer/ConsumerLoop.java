package com.github.entropyfeng.mydb.server.consumer;

import com.github.entropyfeng.mydb.server.command.ClientCommand;
import com.github.entropyfeng.mydb.server.config.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import static com.github.entropyfeng.mydb.server.command.ServerExecute.execute;

/**
 * @author entropyfeng
 */
public class ConsumerLoop {

    private static Logger logger= LoggerFactory.getLogger(ConsumerLoop.class);
    public  void loop(AtomicInteger atomicInteger, Object target, ConcurrentLinkedQueue<ClientCommand> queue){

        while (true) {
            ClientCommand command = queue.poll();
            if (command != null) {
                execute(command, target);
            } else {
                if (ServerConfig.serverBlocking.get()){
                    atomicInteger.getAndIncrement();
                    logger.info("{} begin blocking",target.getClass().getSimpleName());
                    while (ServerConfig.serverBlocking.get()){
                    }
                    logger.info("{} after blocking",target.getClass().getSimpleName());
                }else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

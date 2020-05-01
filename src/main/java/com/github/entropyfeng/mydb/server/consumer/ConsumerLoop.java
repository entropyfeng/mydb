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
    private AtomicBoolean released=new AtomicBoolean(false);
    public  void loop(AtomicInteger atomicInteger, Object target, ConcurrentLinkedQueue<ClientCommand> queue){

        while (true) {
            ClientCommand command = queue.poll();
            if (command != null) {
                execute(command, target);
            } else {
                //while the blocking iis null,we  blocking the object,util other thread notify this object

                try {
                    if (ServerConfig.serverBlocking.get()){
                        atomicInteger.getAndIncrement();
                    }
                    Thread.currentThread().wait();
                } catch (InterruptedException e) {
                    //this status will not happened normally
                    logger.info("track interrupt");
                }
                //after other thread notify this thread

            }
        }
    }
}

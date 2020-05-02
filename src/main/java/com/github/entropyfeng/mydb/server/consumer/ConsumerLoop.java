package com.github.entropyfeng.mydb.server.consumer;

import com.github.entropyfeng.mydb.server.command.ClientRequest;
import com.github.entropyfeng.mydb.server.config.ServerConfig;
import com.github.entropyfeng.mydb.server.handler.TurtleServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

import static com.github.entropyfeng.mydb.server.command.ServerExecute.execute;


/**
 * @author entropyfeng
 */
public class ConsumerLoop {

    private static Logger logger = LoggerFactory.getLogger(ConsumerLoop.class);

    public void loop(Object target, ConcurrentLinkedQueue<ClientRequest> queue) {

        while (true) {

            handleServerBlocking(target);

            ClientRequest command = queue.poll();
            if (command != null) {
                if (command.getModify() && ServerConfig.masterSlaveFlag.get()) {
                    TurtleServerHandler.masterQueue.add(command);
                }
                execute(command, target);
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                }
            }
        }
    }

    private void handleServerBlocking(Object target) {
        if (ServerConfig.serverBlocking.get()) {
            synchronized (Thread.currentThread()) {
                if (ServerConfig.serverBlocking.get()) {
                    try {
                        ServerConfig.threadCountDown.countDown();
                        logger.info("{} before blocked", target.getClass().getSimpleName());
                        Thread.currentThread().wait();
                        logger.info("{} after blocked", target.getClass().getSimpleName());
                    } catch (InterruptedException e) {
                        //not except to access it
                        logger.error(e.getMessage());
                    }
                }
            }
        }
    }
}

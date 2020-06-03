package com.github.entropyfeng.mydb.server.consumer;

import com.github.entropyfeng.mydb.server.command.ClientRequest;
import com.github.entropyfeng.mydb.server.config.ServerConfig;
import com.github.entropyfeng.mydb.server.handler.TurtleServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ThreadPoolExecutor;

import static com.github.entropyfeng.mydb.server.command.ServerExecute.execute;


/**
 * @author entropyfeng
 */
public class ConsumerLoop {

    private static Logger logger = LoggerFactory.getLogger(ConsumerLoop.class);

    @SuppressWarnings("InfiniteLoopStatement")
    public void loop(Object target, ConcurrentLinkedQueue<ClientRequest> queue) {

        while (true) {

            handleServerBlocking(target);
            ClientRequest command = queue.poll();

            if (command != null) {
                if (command.getModify() && ServerConfig.masterSlaveFlag.get()) {
                    TurtleServerHandler.masterQueue.add(command);
                    logger.info("add ms command {}",command.getMethod().getName());
                }
                logger.info("commandName ->{}",command.getMethod().getName());
                execute(command, target);
            }else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     *  when adminThread notify consumer thread blocking
     * @param target domain
     */
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

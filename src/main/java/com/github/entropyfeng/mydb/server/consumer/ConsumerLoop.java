package com.github.entropyfeng.mydb.server.consumer;

import com.github.entropyfeng.mydb.server.command.ClientCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.github.entropyfeng.mydb.server.command.ServerExecute.execute;

/**
 * @author entropyfeng
 */
public class ConsumerLoop {

    private static Logger logger= LoggerFactory.getLogger(ConsumerLoop.class);
    public  void loop(AtomicBoolean runningFlag, Object target, ConcurrentLinkedQueue<ClientCommand> queue){

        while (true) {
            ClientCommand command = queue.poll();
            if (command != null) {
                execute(command, target);
            } else {
                runningFlag.set(false);
                try {
                    Thread.currentThread().wait();
                } catch (InterruptedException e) {
                    logger.info("track interrupt");
                }
                runningFlag.set(true);
            }
        }
    }
}

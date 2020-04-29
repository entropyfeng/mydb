package com.github.entropyfeng.mydb.server.consumer;

import com.github.entropyfeng.mydb.server.command.ClientCommand;
import com.github.entropyfeng.mydb.server.core.domain.HashDomain;
import com.github.entropyfeng.mydb.server.core.domain.ValuesDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.github.entropyfeng.mydb.server.command.ServerExecute.execute;

/**
 * @author entropyfeng
 */
public class HashConsumer implements Runnable  {

    private static final Logger logger= LoggerFactory.getLogger(HashConsumer.class);
    private AtomicBoolean runningFlag;
    private HashDomain hashDomain;
    private ConcurrentLinkedQueue<ClientCommand> queue;

    public HashConsumer(AtomicBoolean runningFlag, HashDomain hashDomain, ConcurrentLinkedQueue<ClientCommand> queue) {
        this.runningFlag = runningFlag;
        this.hashDomain = hashDomain;
        this.queue = queue;
    }

    @Override
    public void run() {
        logger.info("run hash Thread");
        while (true){
            while (runningFlag.get()){
                ClientCommand hashCommand = queue.poll();
                if (hashCommand != null) {
                    execute(hashCommand, hashDomain);
                } else {
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

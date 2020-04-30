package com.github.entropyfeng.mydb.server.consumer;

import com.github.entropyfeng.mydb.server.command.ClientCommand;
import com.github.entropyfeng.mydb.server.core.domain.HashDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author entropyfeng
 */
public class HashConsumer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(HashConsumer.class);
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
        new ConsumerLoop().loop(runningFlag, hashDomain, queue);
    }
}

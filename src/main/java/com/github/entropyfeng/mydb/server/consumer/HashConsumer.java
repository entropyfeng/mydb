package com.github.entropyfeng.mydb.server.consumer;

import com.github.entropyfeng.mydb.server.command.ClientCommand;
import com.github.entropyfeng.mydb.server.domain.HashDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author entropyfeng
 */
public class HashConsumer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(HashConsumer.class);
    private AtomicInteger atomicInteger;
    private HashDomain hashDomain;
    private ConcurrentLinkedQueue<ClientCommand> queue;

    public HashConsumer(AtomicInteger atomicInteger, HashDomain hashDomain, ConcurrentLinkedQueue<ClientCommand> queue) {
        this.atomicInteger=atomicInteger;
        this.hashDomain = hashDomain;
        this.queue = queue;
    }

    @Override
    public void run() {
        logger.info("run hash Thread");
        new ConsumerLoop().loop(atomicInteger, hashDomain, queue);
    }
}

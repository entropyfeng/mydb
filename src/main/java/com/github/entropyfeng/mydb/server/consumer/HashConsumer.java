package com.github.entropyfeng.mydb.server.consumer;

import com.github.entropyfeng.mydb.server.command.ClientRequest;
import com.github.entropyfeng.mydb.server.domain.HashDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author entropyfeng
 */
public class HashConsumer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(HashConsumer.class);
    private HashDomain hashDomain;
    private ConcurrentLinkedQueue<ClientRequest> queue;

    public HashConsumer( HashDomain hashDomain, ConcurrentLinkedQueue<ClientRequest> queue) {
        this.hashDomain = hashDomain;
        this.queue = queue;
    }

    @Override
    public void run() {
        logger.info("run hash Thread");
        new ConsumerLoop().loop(hashDomain, queue);
    }
}

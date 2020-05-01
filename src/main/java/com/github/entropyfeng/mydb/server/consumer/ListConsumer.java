package com.github.entropyfeng.mydb.server.consumer;

import com.github.entropyfeng.mydb.server.command.ClientCommand;
import com.github.entropyfeng.mydb.server.domain.ListDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author entropyfeng
 */
public class ListConsumer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ListConsumer.class);
    private AtomicInteger atomicInteger;
    private ListDomain domain;
    private ConcurrentLinkedQueue<ClientCommand> queue;

    public ListConsumer(AtomicInteger atomicInteger, ListDomain domain, ConcurrentLinkedQueue<ClientCommand> queue) {
        this.atomicInteger=atomicInteger;
        this.domain = domain;
        this.queue = queue;
    }

    @Override
    public void run() {
        logger.info("run list Thread");
        new ConsumerLoop().loop(atomicInteger,domain,queue);
    }
}

package com.github.entropyfeng.mydb.server.consumer;

import com.github.entropyfeng.mydb.server.command.ClientCommand;
import com.github.entropyfeng.mydb.server.domain.ValuesDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author entropyfeng
 */
public class ValuesConsumer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ValuesConsumer.class);

    /**
     * read only
     */
    private AtomicInteger atomicInteger;
    private ValuesDomain valuesDomain;
    private ConcurrentLinkedQueue<ClientCommand> queue;

    public ValuesConsumer(AtomicInteger atomicInteger, ValuesDomain valuesDomain, ConcurrentLinkedQueue<ClientCommand> queue) {
        this.atomicInteger=atomicInteger;
        this.valuesDomain = valuesDomain;
        this.queue = queue;
    }

    @Override
    public void run() {
        logger.info("run values Thread");
        new ConsumerLoop().loop(atomicInteger,valuesDomain,queue);
    }
}


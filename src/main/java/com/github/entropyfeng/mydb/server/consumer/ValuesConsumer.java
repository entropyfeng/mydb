package com.github.entropyfeng.mydb.server.consumer;

import com.github.entropyfeng.mydb.server.command.ClientCommand;
import com.github.entropyfeng.mydb.server.domain.ValuesDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;


/**
 * @author entropyfeng
 */
public class ValuesConsumer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ValuesConsumer.class);

    /**
     * read only
     */
    private AtomicBoolean runningFlag;
    private ValuesDomain valuesDomain;
    private ConcurrentLinkedQueue<ClientCommand> queue;

    public ValuesConsumer(AtomicBoolean runningFlag, ValuesDomain valuesDomain, ConcurrentLinkedQueue<ClientCommand> queue) {
        this.runningFlag = runningFlag;
        this.valuesDomain = valuesDomain;
        this.queue = queue;
    }

    @Override
    public void run() {
        logger.info("run values Thread");
        new ConsumerLoop().loop(runningFlag,valuesDomain,queue);
    }
}


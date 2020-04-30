package com.github.entropyfeng.mydb.server.consumer;

import com.github.entropyfeng.mydb.server.command.ClientCommand;
import com.github.entropyfeng.mydb.server.core.domain.ListDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.github.entropyfeng.mydb.server.command.ServerExecute.execute;

/**
 * @author entropyfeng
 */
public class ListConsumer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ListConsumer.class);
    private AtomicBoolean runningFlag;
    private ListDomain domain;
    private ConcurrentLinkedQueue<ClientCommand> queue;

    public ListConsumer(AtomicBoolean runningFlag, ListDomain domain, ConcurrentLinkedQueue<ClientCommand> queue) {
        this.runningFlag = runningFlag;
        this.domain = domain;
        this.queue = queue;
    }

    @Override
    public void run() {
        logger.info("run list Thread");
        new ConsumerLoop().loop(runningFlag,domain,queue);
    }
}

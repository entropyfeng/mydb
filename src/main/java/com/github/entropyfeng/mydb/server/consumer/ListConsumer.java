package com.github.entropyfeng.mydb.server.consumer;

import com.github.entropyfeng.mydb.server.command.ClientRequest;
import com.github.entropyfeng.mydb.server.domain.ListDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * @author entropyfeng
 */
public class ListConsumer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(ListConsumer.class);
    private ListDomain domain;
    private ConcurrentLinkedQueue<ClientRequest> queue;

    public ListConsumer(ListDomain domain, ConcurrentLinkedQueue<ClientRequest> queue) {
        this.domain = domain;
        this.queue = queue;
    }

    @Override
    public void run() {
        logger.info("run list Thread");
        new ConsumerLoop().loop(domain,queue);
    }
}

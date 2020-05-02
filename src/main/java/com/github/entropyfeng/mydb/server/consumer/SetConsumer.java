package com.github.entropyfeng.mydb.server.consumer;

import com.github.entropyfeng.mydb.server.command.ClientRequest;
import com.github.entropyfeng.mydb.server.domain.SetDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * @author entropyfeng
 */
public class SetConsumer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SetConsumer.class);
    private SetDomain setDomain;
    private ConcurrentLinkedQueue<ClientRequest> queue;

    public SetConsumer(SetDomain setDomain, ConcurrentLinkedQueue<ClientRequest> queue) {
        this.setDomain = setDomain;
        this.queue = queue;
    }

    @Override
    public void run() {
        logger.info("run set Thread");
        new ConsumerLoop().loop(setDomain,queue);
    }
}

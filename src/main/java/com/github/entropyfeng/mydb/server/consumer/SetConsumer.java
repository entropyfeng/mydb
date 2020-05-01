package com.github.entropyfeng.mydb.server.consumer;

import com.github.entropyfeng.mydb.server.command.ClientCommand;
import com.github.entropyfeng.mydb.server.domain.SetDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * @author entropyfeng
 */
public class SetConsumer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(SetConsumer.class);
    private AtomicInteger atomicInteger;
    private SetDomain setDomain;
    private ConcurrentLinkedQueue<ClientCommand> queue;

    public SetConsumer(AtomicInteger atomicInteger, SetDomain setDomain, ConcurrentLinkedQueue<ClientCommand> queue) {
        this.atomicInteger=atomicInteger;
        this.setDomain = setDomain;
        this.queue = queue;
    }

    @Override
    public void run() {
        logger.info("run set Thread");
        new ConsumerLoop().loop(atomicInteger,setDomain,queue);
    }
}

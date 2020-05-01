package com.github.entropyfeng.mydb.server.consumer;

import com.github.entropyfeng.mydb.server.command.ClientCommand;
import com.github.entropyfeng.mydb.server.domain.OrderSetDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * @author entropyfeng
 */
public class OrderSetConsumer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(OrderSetConsumer.class);
    private OrderSetDomain orderSetDomain;
    private ConcurrentLinkedQueue<ClientCommand> queue;

    public OrderSetConsumer(OrderSetDomain orderSetDomain, ConcurrentLinkedQueue<ClientCommand> queue) {
        this.orderSetDomain = orderSetDomain;
        this.queue = queue;
    }

    @Override
    public void run() {
        logger.info("run orderSet Thread");
        new ConsumerLoop().loop( orderSetDomain, queue);


    }
}

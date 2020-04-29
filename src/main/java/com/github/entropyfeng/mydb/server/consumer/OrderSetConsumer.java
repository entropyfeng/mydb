package com.github.entropyfeng.mydb.server.consumer;

import com.github.entropyfeng.mydb.server.command.ClientCommand;
import com.github.entropyfeng.mydb.server.core.domain.OrderSetDomain;
import com.github.entropyfeng.mydb.server.core.domain.ValuesDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.github.entropyfeng.mydb.server.command.ServerExecute.execute;

/**
 * @author entropyfeng
 */
public class OrderSetConsumer implements Runnable  {

    private static final Logger logger= LoggerFactory.getLogger(OrderSetConsumer.class);
    private AtomicBoolean runningFlag;
    private OrderSetDomain orderSetDomain;
    private ConcurrentLinkedQueue<ClientCommand> queue;

    public OrderSetConsumer(AtomicBoolean runningFlag, OrderSetDomain orderSetDomain, ConcurrentLinkedQueue<ClientCommand> queue) {
        this.runningFlag = runningFlag;
        this.orderSetDomain=orderSetDomain;
        this.queue = queue;
    }

    @Override
    public void run() {
        logger.info("run orderSet Thread");
        while (true){
            while (runningFlag.get()){
                ClientCommand orderSetCommand = queue.poll();
                if (orderSetCommand != null) {
                    execute(orderSetCommand, orderSetDomain);
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}

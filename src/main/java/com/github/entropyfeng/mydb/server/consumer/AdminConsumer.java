package com.github.entropyfeng.mydb.server.consumer;

import com.github.entropyfeng.mydb.server.AdminObject;
import com.github.entropyfeng.mydb.server.command.ClientRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

import static com.github.entropyfeng.mydb.server.command.ServerExecute.execute;

/**
 * @author entropyfeng
 */
public class AdminConsumer implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(AdminConsumer.class);
    private AdminObject adminObject;
    private ConcurrentLinkedQueue<ClientRequest> queue;

    public AdminConsumer(AdminObject adminObject, ConcurrentLinkedQueue<ClientRequest> queue) {
        this.adminObject = adminObject;
        this.queue = queue;
    }

    @SuppressWarnings("InfiniteLoopStatement")
    @Override
    public void run() {
        logger.info("run admin Thread");
        while (true) {
            ClientRequest adminCommand = queue.poll();
            if (adminCommand != null) {
                execute(adminCommand, adminObject);
            }else {
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

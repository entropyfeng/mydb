package com.github.entropyfeng.mydb.server.consumer;

import com.github.entropyfeng.mydb.server.AdminObject;
import com.github.entropyfeng.mydb.server.command.ClientCommand;
import com.github.entropyfeng.mydb.server.core.domain.HashDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.github.entropyfeng.mydb.server.command.ServerExecute.execute;

/**
 * @author entropyfeng
 */
public class AdminConsumer implements Runnable  {

    private static final Logger logger= LoggerFactory.getLogger(AdminConsumer.class);
    private AdminObject adminObject;
    private ConcurrentLinkedQueue<ClientCommand> queue;

    public AdminConsumer( AdminObject adminObject, ConcurrentLinkedQueue<ClientCommand> queue) {
        this.adminObject=adminObject;
        this.queue = queue;
    }

    @Override
    public void run() {
        logger.info("run hash Thread");
        while (true) {
            ClientCommand adminCommand = queue.poll();
            if (adminCommand != null) {
                execute(adminCommand, adminObject);
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

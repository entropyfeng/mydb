package com.github.entropyfeng.mydb.server.consumer;

import com.github.entropyfeng.mydb.server.AdminObject;
import com.github.entropyfeng.mydb.server.ServerDomain;
import com.github.entropyfeng.mydb.server.TurtleServer;
import com.github.entropyfeng.mydb.server.command.ClientRequest;
import com.github.entropyfeng.mydb.server.config.ServerConfig;
import com.github.entropyfeng.mydb.server.config.ServerStatus;
import com.github.entropyfeng.mydb.server.handler.TurtleServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;

import static com.github.entropyfeng.mydb.server.command.ServerExecute.execute;

/**
 * @author entropyfeng
 */
public class AdminConsumer implements Runnable  {

    private static final Logger logger= LoggerFactory.getLogger(AdminConsumer.class);
    private AdminObject adminObject;
    private ConcurrentLinkedQueue<ClientRequest> queue;

    public AdminConsumer( AdminObject adminObject, ConcurrentLinkedQueue<ClientRequest> queue) {
        this.adminObject=adminObject;
        this.queue = queue;
    }

    @Override
    public void run() {
        logger.info("run hash Thread");
        while (true) {
            ClientRequest adminCommand = queue.poll();
            if (adminCommand != null) {
                if ("slaveOfServer".equals(adminCommand.getOperationName())){
                    TurtleServerHandler.serverMap.put(adminCommand.getChannel().id(),adminCommand.getChannel());
                }
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

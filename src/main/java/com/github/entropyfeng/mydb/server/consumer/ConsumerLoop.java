package com.github.entropyfeng.mydb.server.consumer;

import com.github.entropyfeng.mydb.server.command.ClientRequest;
import com.github.entropyfeng.mydb.server.config.ServerConfig;

import java.util.concurrent.ConcurrentLinkedQueue;
import static com.github.entropyfeng.mydb.server.command.ServerExecute.execute;

/**
 * @author entropyfeng
 */
public class ConsumerLoop {

    public  void loop(Object target, ConcurrentLinkedQueue<ClientRequest> queue){

        while (true) {
            ClientRequest command = queue.poll();
            if (command != null) {
                execute(command, target);
            } else {
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

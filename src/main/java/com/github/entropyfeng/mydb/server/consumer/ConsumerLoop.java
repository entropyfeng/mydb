package com.github.entropyfeng.mydb.server.consumer;

import com.github.entropyfeng.mydb.server.command.ClientCommand;

import java.util.concurrent.ConcurrentLinkedQueue;
import static com.github.entropyfeng.mydb.server.command.ServerExecute.execute;

/**
 * @author entropyfeng
 */
public class ConsumerLoop {

    public  void loop(Object target, ConcurrentLinkedQueue<ClientCommand> queue){

        while (true) {
            ClientCommand command = queue.poll();
            if (command != null) {
                execute(command, target);
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

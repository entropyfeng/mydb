package com.github.entropyfeng.mydb.server.consumer;

import com.github.entropyfeng.mydb.server.command.ClientCommand;
import com.github.entropyfeng.mydb.server.core.domain.SetDomain;
import com.github.entropyfeng.mydb.server.core.domain.ValuesDomain;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.github.entropyfeng.mydb.server.command.ServerExecute.execute;

/**
 * @author entropyfeng
 */
public class SetConsumer implements Runnable  {

    private static final Logger logger= LoggerFactory.getLogger(SetConsumer.class);
    private AtomicBoolean runningFlag;
    private SetDomain setDomain;
    private ConcurrentLinkedQueue<ClientCommand> queue;

    public SetConsumer(AtomicBoolean runningFlag, SetDomain setDomain, ConcurrentLinkedQueue<ClientCommand> queue) {
        this.runningFlag = runningFlag;
        this.setDomain = setDomain;
        this.queue = queue;
    }

    @Override
    public void run() {
        logger.info("run set Thread");
        while (true){
            while (runningFlag.get()){
                ClientCommand setCommand = queue.poll();
                if (setCommand != null) {
                    execute(setCommand, setDomain);
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

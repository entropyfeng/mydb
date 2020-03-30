package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.core.obj.TurtleValue;
import com.github.entropyfeng.mydb.core.obj.ValuesObject;
import com.github.entropyfeng.mydb.server.command.ValuesCommand;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.concurrent.*;

/**
 * @author entropyfeng
 */
public class ValuesDomain implements Runnable{

    private final ValuesObject  valuesObject;
    private final ConcurrentLinkedDeque<ValuesCommand> valuesQueue;




    public ValuesDomain(ConcurrentLinkedDeque<ValuesCommand> valuesQueue) {
        this.valuesQueue = valuesQueue;
        this.valuesObject=new ValuesObject(new HashMap<>());


        ExecutorService executorService =Executors.newCachedThreadPool();

        new Thread(()->{

        },"valuesThread");
    }

    @Override
    public void run() {
        while (true){
            ValuesCommand command=  valuesQueue.pop();
            if (command!=null){
                try {
                    command.getMethod().invoke(valuesObject,command.getValues());
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

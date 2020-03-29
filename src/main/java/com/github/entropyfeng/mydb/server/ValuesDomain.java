package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.core.obj.TurtleValue;
import com.github.entropyfeng.mydb.core.obj.ValuesObject;
import com.github.entropyfeng.mydb.server.command.ValuesCommand;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ThreadFactory;

/**
 * @author entropyfeng
 */
public class ValuesDomain{

    private Thread thread;
    private final ValuesObject  valuesObject;
    private final ConcurrentLinkedDeque<ValuesCommand> valuesQueue;


    public ValuesDomain(ConcurrentLinkedDeque<ValuesCommand> valuesQueue) {
        this.valuesQueue = valuesQueue;
        this.valuesObject=new ValuesObject(new HashMap<>());

        thread= new Thread(()->{
            while (true){
              ValuesCommand command=  valuesQueue.pop();
                while (command!=null){
                    try {
                        command.getMethod().invoke(valuesObject,command.getValues());
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }
        },"valuesThread");
    }

}

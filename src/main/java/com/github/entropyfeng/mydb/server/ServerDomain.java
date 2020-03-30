package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.core.obj.ListObject;
import com.github.entropyfeng.mydb.core.obj.SetObject;
import com.github.entropyfeng.mydb.core.obj.ValuesObject;
import com.github.entropyfeng.mydb.server.command.ValuesCommand;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


/**
 * @author entropyfeng
 */
public class ServerDomain  extends Thread {

    private ValuesObject valuesObject;

    private ListObject listObject;

    private SetObject setObject;

    private ConcurrentLinkedDeque<ValuesCommand> valuesQueue;

    private ConcurrentLinkedDeque<Runnable> listQueue;


    public void xx(){
      new ValuesThreadFactory().newThread(this::runValues).start();

    }

    private void runValues() {
        while (true){
            ValuesCommand valuesCommand = valuesQueue.pop();
            Object res = null;
            try {
                res = valuesCommand.getMethod().invoke(valuesCommand, valuesCommand.getValues());
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
        }


    }

}

package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.core.obj.ListObject;
import com.github.entropyfeng.mydb.core.obj.SetObject;
import com.github.entropyfeng.mydb.core.obj.ValuesObject;
import com.github.entropyfeng.mydb.server.command.ValuesCommand;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingDeque;


/**
 * @author entropyfeng
 */
public class ServerDomain {

    private ValuesObject valuesObject;

    private ListObject listObject;

    private SetObject setObject;

    private ConcurrentLinkedDeque<ValuesExe> valuesQueue;

    private ConcurrentLinkedDeque<Runnable> listQueue;


    public void xx(){
        @SuppressWarnings("a")
        ValuesCommand valuesCommand=new ValuesCommand();
        Method method;
        try {
           method= valuesObject.getClass().getDeclaredMethod(valuesCommand.getOperationName(),valuesCommand.getParaTypes());
           method.invoke(valuesObject,valuesCommand.getParas());
        } catch (NoSuchMethodException e) {
            throw new Error();
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }


        while (!valuesQueue.isEmpty()){
            valuesQueue.pop().run();
        }
    }

}

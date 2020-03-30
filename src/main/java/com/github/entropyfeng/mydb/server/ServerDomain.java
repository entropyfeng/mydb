package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.obj.ListObject;
import com.github.entropyfeng.mydb.core.obj.SetObject;
import com.github.entropyfeng.mydb.core.obj.ValuesObject;
import com.github.entropyfeng.mydb.server.command.ValuesCommand;
import com.github.entropyfeng.mydb.server.factory.ListThreadFactory;
import com.github.entropyfeng.mydb.server.factory.ValuesThreadFactory;
import io.netty.channel.Channel;
import sun.misc.ThreadGroupUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ConcurrentLinkedDeque;


/**
 * @author entropyfeng
 */
public class ServerDomain {

    public ServerDomain() {
      valuesObject=new ValuesObject();
      valuesQueue=new ConcurrentLinkedDeque<>();

      listObject=new ListObject();
      listQueue=new ConcurrentLinkedDeque<>();
    }

    private ValuesObject valuesObject;

    private ListObject listObject;

    private SetObject setObject;

    private ConcurrentLinkedDeque<ValuesCommand> valuesQueue;

    private ConcurrentLinkedDeque<Runnable> listQueue;


    public void xx() {
        new ValuesThreadFactory().newThread(this::runValues).start();
        new ListThreadFactory().newThread(this::runList).start();

    }

    private void runList() {
        while (true) {

        }
    }

    private void runValues() {
        while (true) {
            ValuesCommand valuesCommand = valuesQueue.pop();
            Object res = null;
            try {
                res = valuesCommand.getMethod().invoke(valuesCommand, valuesCommand.getValues());
            } catch (IllegalAccessException | InvocationTargetException e) {
                e.printStackTrace();
            }
            if (res != null) {
            }
        }
    }

    public  void acceptClientCommand(TurtleProtoBuf.ClientCommand clientCommand, Channel channel){

    }

}

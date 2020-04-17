package com.github.entropyfeng.mydb.client.conn;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;

import java.util.concurrent.Callable;

/**
 * @author entropyfeng
 */
public class ClientTask implements Callable<TurtleProtoBuf.ResponseData> {

    private final TurtleProtoBuf.ClientCommand clientCommand;

    public ClientTask(TurtleProtoBuf.ClientCommand clientCommand) {
        this.clientCommand = clientCommand;

    }

    @Override
    public TurtleProtoBuf.ResponseData call() throws Exception {


        return null;
    }
}

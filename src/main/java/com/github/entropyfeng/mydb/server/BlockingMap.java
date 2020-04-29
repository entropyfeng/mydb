package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.server.command.ClientRequest;

import java.nio.channels.Channel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * @author entropyfeng
 */
public class BlockingMap {

    public static BlockingQueue<Pair<ClientRequest,Channel>> globalBlocking=new LinkedBlockingQueue<>();

    public static void accept(ClientRequest clientRequest, Channel channel){


    }
}

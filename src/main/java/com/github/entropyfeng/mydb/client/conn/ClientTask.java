package com.github.entropyfeng.mydb.client.conn;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.common.Pair;

import java.util.Collection;
import java.util.concurrent.Callable;

import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.*;

/**
 * @author entropyfeng
 */
public class ClientTask implements Callable<Pair<ResHead, Collection<ResBody>>> {

    private ClientCommandBuilder commandBuilder;

    @Override
    public Pair<ResHead, Collection<ResBody>> call() throws Exception {

        return null;
    }
}

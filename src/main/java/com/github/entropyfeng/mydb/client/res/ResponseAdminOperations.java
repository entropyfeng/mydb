package com.github.entropyfeng.mydb.client.res;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.ops.IAdminOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public class ResponseAdminOperations implements IAdminOperations {

    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> clear() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ADMIN, "clear");
        return ClientExecute.execute(builder);
    }

    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> lazyClear() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ADMIN, "lazyClear");
        return ClientExecute.execute(builder);
    }

    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> dump() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ADMIN, "dump");
        return ClientExecute.execute(builder);
    }

    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> lazyDump() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ADMIN, "lazyDump");
        return ClientExecute.execute(builder);
    }

    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> deleteAllDump() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ADMIN, "deleteAllDump");
        return ClientExecute.execute(builder);
    }

    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> slaveOf(String host, Integer port) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ADMIN, "slaveOf");
        builder.addStringPara(host);
        builder.addIntegerPara(port);
        return ClientExecute.execute(builder);

    }
}

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

    private ClientExecute clientExecute;

    public ResponseAdminOperations(ClientExecute clientExecute) {
        this.clientExecute = clientExecute;
    }

    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> clear() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ADMIN, "clear");
        return clientExecute.execute(builder);
    }

    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> lazyClear() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ADMIN, "lazyClear");
        return clientExecute.execute(builder);
    }

    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> dump() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ADMIN, "dump");
        return clientExecute.execute(builder);
    }

    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> lazyDump() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ADMIN, "lazyDump");
        return clientExecute.execute(builder);
    }

    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> deleteAllDump() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ADMIN, "deleteAllDump");
        return clientExecute.execute(builder);
    }

    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> slaveOf(String host, Integer port) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ADMIN, "slaveOf");
        builder.addStringPara(host);
        builder.addIntegerPara(port);
        return clientExecute.execute(builder);

    }

}

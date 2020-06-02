package com.github.entropyfeng.mydb.client.res;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.ops.IAdminOperations;

import java.util.Collection;

import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.DataBody;
import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;

/**
 * @author entropyfeng
 */
public class ResponseAdminOperations implements IAdminOperations {

    private ClientExecute clientExecute;

    public ResponseAdminOperations(ClientExecute clientExecute) {
        this.clientExecute = clientExecute;
    }

    @Override
    public Pair<ResHead, Collection<DataBody>> clear() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ADMIN, "clear");
        return clientExecute.execute(builder);
    }

    @Override
    public Pair<ResHead, Collection<DataBody>> lazyClear() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ADMIN, "lazyClear");
        return clientExecute.execute(builder);
    }

    @Override
    public Pair<ResHead, Collection<DataBody>> dump() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ADMIN, "dump");
        return clientExecute.execute(builder);
    }

    @Override
    public Pair<ResHead, Collection<DataBody>> lazyDump() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ADMIN, "lazyDump");
        return clientExecute.execute(builder);
    }

    @Override
    public Pair<ResHead, Collection<DataBody>> deleteAllDump() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ADMIN, "deleteAllDump");
        return clientExecute.execute(builder);
    }

    @Override
    public Pair<ResHead, Collection<DataBody>> slaveOf(String host, Integer port) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ADMIN, "slaveOf");
        builder.addStringPara(host);
        builder.addIntegerPara(port);
        return clientExecute.execute(builder);

    }

}

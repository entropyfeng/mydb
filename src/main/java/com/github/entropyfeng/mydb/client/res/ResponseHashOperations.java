package com.github.entropyfeng.mydb.client.res;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.ops.IHashOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.DataBody;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public class ResponseHashOperations implements IHashOperations {


    private ClientExecute clientExecute;

    public ResponseHashOperations(ClientExecute clientExecute) {
        this.clientExecute = clientExecute;
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> get(@NotNull String key, @NotNull TurtleValue tKey) {

        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "get");
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> get(@NotNull String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "get");
        builder.addStringPara(key);
        return clientExecute.execute(builder);

    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> exists(@NotNull String key, @NotNull TurtleValue tKey, TurtleValue tValue) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "exists");
        builder.addStringPara(key);
        builder.addTurtlePara(tKey);
        builder.addTurtlePara(tValue);
        return clientExecute.execute(builder);
    }


    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> exists(@NotNull String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "exists");
        builder.addStringPara(key);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> put(@NotNull String key, @NotNull TurtleValue tKey, @NotNull TurtleValue tValue) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "put");
        builder.addStringPara(key);
        builder.addTurtlePara(tKey);
        builder.addTurtlePara(tValue);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> delete(@NotNull String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "delete");
        builder.addStringPara(key);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> delete(@NotNull String key, @NotNull TurtleValue tKey) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "delete");
        builder.addStringPara(key);
        builder.addTurtlePara(tKey);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> sizeOf(@NotNull String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "sizeOf");
        builder.addStringPara(key);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> clear() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "clear");
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> dump() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "dump");
        return clientExecute.execute(builder);
    }
}

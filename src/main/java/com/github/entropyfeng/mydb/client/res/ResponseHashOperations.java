package com.github.entropyfeng.mydb.client.res;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.ops.IHashOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public class ResponseHashOperations implements IHashOperations {


    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> get(@NotNull String key, @NotNull TurtleValue tKey) {

        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "get");
        return ClientExecute.execute(builder);

    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> get(@NotNull String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "get");
        builder.addStringPara(key);
        return ClientExecute.execute(builder);

    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> exists(@NotNull String key, @NotNull TurtleValue tKey, TurtleValue tValue) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "exists");
        builder.addStringPara(key);
        builder.addTurtlePara(tKey);
        builder.addTurtlePara(tValue);
        return ClientExecute.execute(builder);
    }


    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> exists(@NotNull String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "exists");
        builder.addStringPara(key);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> put(@NotNull String key, @NotNull TurtleValue tKey, @NotNull TurtleValue tValue) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "put");
        builder.addStringPara(key);
        builder.addTurtlePara(tKey);
        builder.addTurtlePara(tValue);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> delete(@NotNull String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "delete");
        builder.addStringPara(key);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> delete(@NotNull String key, @NotNull TurtleValue tKey) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "delete");
        builder.addStringPara(key);
        builder.addTurtlePara(tKey);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> sizeOf(@NotNull String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "sizeOf");
        builder.addStringPara(key);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> clear() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "clear");
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> dump() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "dump");
        return ClientExecute.execute(builder);
    }
}

package com.github.entropyfeng.mydb.client.ops;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.ops.IHashOperations;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public class ResponseHashOperations implements IHashOperations {


    @Override
    public @NotNull TurtleProtoBuf.ResponseData get(@NotNull String key, @NotNull TurtleValue tKey) {

        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "get");
        return ClientExecute.singleExecute(builder.build());

    }

    @Override
    public @NotNull Collection<TurtleProtoBuf.ResponseData> get(@NotNull String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "get");
        builder.addStringPara(key);
        return ClientExecute.collectionExecute(builder.build());

    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData exists(@NotNull String key, @NotNull TurtleValue tKey, TurtleValue tValue) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "exists");
        builder.addStringPara(key);
        builder.addTurtlePara(tKey);
        builder.addTurtlePara(tValue);
        return ClientExecute.singleExecute(builder.build());
    }


    @Override
    public @NotNull TurtleProtoBuf.ResponseData exists(@NotNull String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "exists");
        builder.addStringPara(key);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData put(@NotNull String key, @NotNull TurtleValue tKey, @NotNull TurtleValue tValue) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "put");
        builder.addStringPara(key);
        builder.addTurtlePara(tKey);
        builder.addTurtlePara(tValue);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData delete(@NotNull String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "delete");
        builder.addStringPara(key);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData delete(@NotNull String key, @NotNull TurtleValue tKey) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "delete");
        builder.addStringPara(key);
        builder.addTurtlePara(tKey);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData sizeOf(@NotNull String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "sizeOf");
        builder.addStringPara(key);
        return ClientExecute.singleExecute(builder.build());
    }
}

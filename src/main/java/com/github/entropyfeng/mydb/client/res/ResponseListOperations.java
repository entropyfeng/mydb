package com.github.entropyfeng.mydb.client.res;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.ops.IListOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public class ResponseListOperations implements IListOperations {


    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> size() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "size");
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> sizeOf(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "sizeOf");
        builder.addStringPara(key);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> leftPush(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "leftPush");

        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> leftPushAll(String key, Collection<TurtleValue> values) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "leftPushAll");

        builder.addStringPara(key);
        builder.addTurtleCollectionPara(values);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> leftPushIfPresent(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "leftPushIfPresent");

        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> leftPushIfAbsent(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "leftPushIfAbsent");

        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> rightPush(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "rightPush");

        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> rightPushAll(String key, Collection<TurtleValue> values) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "rightPushAll");

        builder.addStringPara(key);
        builder.addTurtleCollectionPara(values);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> rightPushIfPresent(String key, TurtleValue value) {

        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "rightPushIfPresent");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> rightPushIfAbsent(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "rightPushIfAbsent");

        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> leftPop(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "leftPop");
        builder.addStringPara(key);

        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> left(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "left");
        builder.addStringPara(key);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> rightPop(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "rightPop");
        builder.addStringPara(key);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> right(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "right");
        builder.addStringPara(key);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> clear(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "clear");
        builder.addStringPara(key);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> clear() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "clear");
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> exist(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "exist");
        builder.addStringPara(key);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> exist(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "exist");

        builder.addStringPara(key);
        builder.addTurtlePara(value);
        return ClientExecute.execute(builder);
    }


    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> dump() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "dump");
        return ClientExecute.execute(builder);
    }
}

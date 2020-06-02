package com.github.entropyfeng.mydb.client.res;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.ops.IListOperations;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.DataBody;
import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;

/**
 * @author entropyfeng
 */
public class ResponseListOperations implements IListOperations {

    public ResponseListOperations(ClientExecute clientExecute) {
        this.clientExecute = clientExecute;
    }

    private ClientExecute clientExecute;
    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> size() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "size");
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> sizeOf(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "sizeOf");
        builder.addStringPara(key);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> leftPush(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "leftPush");

        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> leftPushAll(String key, Collection<TurtleValue> values) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "leftPushAll");

        builder.addStringPara(key);
        builder.addTurtleCollectionPara(values);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> leftPushIfPresent(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "leftPushIfPresent");

        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> leftPushIfAbsent(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "leftPushIfAbsent");

        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> rightPush(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "rightPush");

        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> rightPushAll(String key, Collection<TurtleValue> values) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "rightPushAll");

        builder.addStringPara(key);
        builder.addTurtleCollectionPara(values);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> rightPushIfPresent(String key, TurtleValue value) {

        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "rightPushIfPresent");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> rightPushIfAbsent(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "rightPushIfAbsent");

        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> leftPop(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "leftPop");
        builder.addStringPara(key);

        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> left(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "left");
        builder.addStringPara(key);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> rightPop(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "rightPop");
        builder.addStringPara(key);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> right(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "right");
        builder.addStringPara(key);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> clear(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "clear");
        builder.addStringPara(key);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> clear() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "clear");
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> exist(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "exist");
        builder.addStringPara(key);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> exist(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "exist");

        builder.addStringPara(key);
        builder.addTurtlePara(value);
        return clientExecute.execute(builder);
    }


    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> dump() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "dump");
        return clientExecute.execute(builder);
    }
}

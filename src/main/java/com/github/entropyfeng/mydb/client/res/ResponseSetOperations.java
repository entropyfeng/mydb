package com.github.entropyfeng.mydb.client.res;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.ops.ISetOperations;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.*;

/**
 * @author entropyfeng
 */
public class ResponseSetOperations implements ISetOperations {

    public ResponseSetOperations(ClientExecute clientExecute) {
        this.clientExecute = clientExecute;
    }

    private ClientExecute clientExecute;
    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> exist(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "exist");
        builder.addStringPara(key);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> exist(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "exist");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> add(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "add");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> union(String key, String otherKey) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "union");
        builder.addStringPara(key);
        builder.addStringPara(otherKey);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> unionAndGet(String key, String otherKey) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "unionAndGet");
        builder.addStringPara(key);
        builder.addStringPara(otherKey);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> unionAndGet(String key, Collection<TurtleValue> turtleValues) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "unionAndGet");
        builder.addStringPara(key);
        builder.addTurtleCollectionPara(turtleValues);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

  @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> union(String key, Collection<TurtleValue> turtleValues) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "union");
        builder.addStringPara(key);
        builder.addTurtleCollectionPara(turtleValues);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> intersect(String key, String otherKey) {

        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "intersect");
        builder.addStringPara(key);
        builder.addStringPara(otherKey);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> intersect(String key, Collection<TurtleValue> turtleValues) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "intersect");
        builder.addStringPara(key);
        builder.addTurtleCollectionPara(turtleValues);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> difference(String key, String otherKey) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "difference");
        builder.addStringPara(key);
        builder.addStringPara(otherKey);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> difference(String key, Collection<TurtleValue> turtleValues) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "difference");
        builder.addStringPara(key);
        builder.addTurtleCollectionPara(turtleValues);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> entries(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "entries");
        builder.addStringPara(key);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> size() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "size");
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> sizeOf(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "sizeOf");
        builder.addStringPara(key);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> clear() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "clear");
        builder.setModifyAble(true);
        return clientExecute.execute(builder);

    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> dump() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "dump");
        return clientExecute.execute(builder);
    }
}

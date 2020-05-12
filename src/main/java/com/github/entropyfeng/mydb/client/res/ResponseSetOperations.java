package com.github.entropyfeng.mydb.client.res;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.ops.ISetOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @// TODO: 2020/4/30 xxxxxxxx
 * @author entropyfeng
 */
public class ResponseSetOperations implements ISetOperations {


    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> exist(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "exist");
        builder.addStringPara(key);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> exist(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "exist");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> add(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "add");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> union(String key, String otherKey) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "union");
        builder.addStringPara(key);
        builder.addStringPara(otherKey);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> unionAndGet(String key, String otherKey) {
        return null;
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> unionAndGet(String key, Collection<TurtleValue> turtleValues) {
        return null;
    }

  @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> union(String key, Collection<TurtleValue> turtleValues) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "union");
        builder.addStringPara(key);
        builder.addTurtleCollectionPara(turtleValues);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> intersect(String key, String otherKey) {

        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "intersect");
        builder.addStringPara(key);
        builder.addStringPara(otherKey);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> intersect(String key, Collection<TurtleValue> turtleValues) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "intersect");
        builder.addStringPara(key);
        builder.addTurtleCollectionPara(turtleValues);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> difference(String key, String otherKey) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "difference");
        builder.addStringPara(key);
        builder.addStringPara(otherKey);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> difference(String key, Collection<TurtleValue> turtleValues) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "difference");
        builder.addStringPara(key);
        builder.addTurtleCollectionPara(turtleValues);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> entries(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "entries");
        builder.addStringPara(key);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> clear() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "clear");
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);

    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> dump() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "dump");
        return ClientExecute.execute(builder);
    }
}

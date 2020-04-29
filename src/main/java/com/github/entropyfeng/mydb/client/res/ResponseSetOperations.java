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
 * @author entropyfeng
 */
public class ResponseSetOperations implements ISetOperations {


    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> exist(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "exist");
        builder.addStringPara(key);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> exist(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "exist");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> add(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "add");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> union(String key, String otherKey) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "union");
        builder.addStringPara(key);
        builder.addStringPara(otherKey);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> unionAndGet(String key, String otherKey) {
        return null;
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> unionAndGet(String key, Collection<TurtleValue> turtleValues) {
        return null;
    }

  @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> union(String key, Collection<TurtleValue> turtleValues) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "union");
        builder.addStringPara(key);
        builder.addTurtleCollectionPara(turtleValues);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> intersect(String key, String otherKey) {

        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "intersect");
        builder.addStringPara(key);
        builder.addStringPara(otherKey);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> intersect(String key, Collection<TurtleValue> turtleValues) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "intersect");
        builder.addStringPara(key);
        builder.addTurtleCollectionPara(turtleValues);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> difference(String key, String otherKey) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "difference");
        builder.addStringPara(key);
        builder.addStringPara(otherKey);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> difference(String key, Collection<TurtleValue> turtleValues) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "difference");
        builder.addStringPara(key);
        builder.addTurtleCollectionPara(turtleValues);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> entries(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "entries");
        builder.addStringPara(key);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> clear() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "clear");
        return ClientExecute.execute(builder);

    }
}

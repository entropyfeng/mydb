package com.github.entropyfeng.mydb.client.res;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.ops.IOrderSetOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.server.PersistenceHelper;
import com.github.entropyfeng.mydb.server.command.ClientCommand;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public class ResponseOrderSetOperations implements IOrderSetOperations {


    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> exists(String key, TurtleValue value, Double score) {

        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "exists");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.addDoublePara(score);

        return ClientExecute.execute(builder);

    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> exists(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "exists");
        builder.addStringPara(key);
        builder.addTurtlePara(value);


        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> exists(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "exists");
        builder.addStringPara(key);

        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> add(String key, TurtleValue value, Double score) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "add");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.addDoublePara(score);

        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> add(String key, Collection<TurtleValue> values, Collection<Double> doubles) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "add");
        builder.addStringPara(key);
        builder.addTurtleCollectionPara(values);
        builder.addDoubleCollectionPara(doubles);

        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> count(String key, Double begin, Double end) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "count");
        builder.addStringPara(key);
        builder.addDoublePara(begin);
        builder.addDoublePara(end);

        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> range(String key, Double begin, Double end) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "range");
        builder.addStringPara(key);
        builder.addDoublePara(begin);
        builder.addDoublePara(end);

        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> inRange(String key, Double begin, Double end) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "inRange");
        builder.addStringPara(key);
        builder.addDoublePara(begin);
        builder.addDoublePara(end);

        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> delete(String key, TurtleValue value, Double score) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "delete");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.addDoublePara(score);

        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> delete(String key, Double begin, Double end) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "delete");
        builder.addStringPara(key);
        builder.addDoublePara(begin);
        builder.addDoublePara(end);

        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> delete(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "delete");
        builder.addStringPara(key);
        builder.addTurtlePara(value);


        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> delete(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "delete");
        builder.addStringPara(key);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> clear() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "clear");
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> dump() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "dump");
        return ClientExecute.execute(builder);
    }
}
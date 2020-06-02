package com.github.entropyfeng.mydb.client.res;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.ops.IOrderSetOperations;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.DataBody;
import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;

/**
 * @author entropyfeng
 */
public class ResponseOrderSetOperations implements IOrderSetOperations {

    public ResponseOrderSetOperations(ClientExecute clientExecute) {
        this.clientExecute = clientExecute;
    }

    private ClientExecute clientExecute;

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> exists(String key, TurtleValue value, Double score) {

        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "exists");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.addDoublePara(score);

        return clientExecute.execute(builder);

    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> exists(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "exists");
        builder.addStringPara(key);
        builder.addTurtlePara(value);


        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> exists(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "exists");
        builder.addStringPara(key);

        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> add(String key, TurtleValue value, Double score) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "add");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.addDoublePara(score);

        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> add(String key, Collection<TurtleValue> values, Collection<Double> doubles) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "add");
        builder.addStringPara(key);
        builder.addTurtleCollectionPara(values);
        builder.addDoubleCollectionPara(doubles);

        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> count(String key, Double begin, Double end) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "count");
        builder.addStringPara(key);
        builder.addDoublePara(begin);
        builder.addDoublePara(end);

        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> range(String key, Double begin, Double end) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "range");
        builder.addStringPara(key);
        builder.addDoublePara(begin);
        builder.addDoublePara(end);

        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> inRange(String key, Double begin, Double end) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "inRange");
        builder.addStringPara(key);
        builder.addDoublePara(begin);
        builder.addDoublePara(end);

        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> delete(String key, TurtleValue value, Double score) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "delete");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.addDoublePara(score);

        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> delete(String key, Double begin, Double end) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "delete");
        builder.addStringPara(key);
        builder.addDoublePara(begin);
        builder.addDoublePara(end);

        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> delete(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "delete");
        builder.addStringPara(key);
        builder.addTurtlePara(value);


        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> delete(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "delete");
        builder.addStringPara(key);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> clear() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "clear");
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ResHead, Collection<DataBody>> dump() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.ZSET, "dump");
        return clientExecute.execute(builder);
    }
}

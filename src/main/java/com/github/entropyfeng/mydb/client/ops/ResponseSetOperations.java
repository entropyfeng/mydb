package com.github.entropyfeng.mydb.client.ops;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.ops.ISetOperations;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public class ResponseSetOperations implements ISetOperations {


    @Override
    public @NotNull TurtleProtoBuf.ResponseData exist(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "exist");
        builder.addStringPara(key);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData exist(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "exist");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData add(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "add");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData union(String key, String otherKey) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "union");
        builder.addStringPara(key);
        builder.addStringPara(otherKey);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData union(String key, Collection<TurtleValue> turtleValues) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "union");
        builder.addStringPara(key);
        builder.addTurtleCollectionPara(turtleValues);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData intersect(String key, String otherKey) {

        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "intersect");
        builder.addStringPara(key);
        builder.addStringPara(otherKey);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData intersect(String key, Collection<TurtleValue> turtleValues) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "intersect");
        builder.addStringPara(key);
        builder.addTurtleCollectionPara(turtleValues);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData difference(String key, String otherKey) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "difference");
        builder.addStringPara(key);
        builder.addStringPara(otherKey);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData difference(String key, Collection<TurtleValue> turtleValues) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "difference");
        builder.addStringPara(key);
        builder.addTurtleCollectionPara(turtleValues);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull Collection<TurtleProtoBuf.ResponseData> entries(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "entries");
        builder.addStringPara(key);
        return ClientExecute.collectionExecute(builder.build());
    }
}

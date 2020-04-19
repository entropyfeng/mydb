package com.github.entropyfeng.mydb.client.ops;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.ops.IListOperations;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public class ResponseListOperations implements IListOperations {


    @Override
    public @NotNull TurtleProtoBuf.ResponseData size() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "size");
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData sizeOf(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "sizeOf");

        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData leftPush(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "leftPush");

        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData leftPushAll(String key, Collection<TurtleValue> values) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "leftPushAll");

        builder.addStringPara(key);
        builder.addTurtleCollectionPara(values);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData leftPushIfPresent(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "leftPushIfPresent");

        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData leftPushIfAbsent(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "leftPushIfAbsent");

        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData rightPush(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "rightPush");

        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData rightPushAll(String key, Collection<TurtleValue> values) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "rightPushAll");

        builder.addStringPara(key);
        builder.addTurtleCollectionPara(values);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData rightPushIfPresent(String key, TurtleValue value) {

        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "rightPushIfPresent");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData rightPushIfAbsent(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "rightPushIfAbsent");

        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData leftPop(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "leftPop");
        builder.addStringPara(key);

        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData left(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "left");
        builder.addStringPara(key);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData rightPop(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "rightPop");
        builder.addStringPara(key);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData right(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "right");
        builder.addStringPara(key);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData clear(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "clear");
        builder.addStringPara(key);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData clearAll() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "clearAll");
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData exist(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "exist");
        builder.addStringPara(key);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData exist(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "exist");

        builder.addStringPara(key);
        builder.addTurtlePara(value);
        return ClientExecute.singleExecute(builder.build());
    }
}

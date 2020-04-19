package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.TurtleParaType;
import com.github.entropyfeng.mydb.common.ops.ISetOperations;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.obj.TurtleValue;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public class DefaultSetOperations implements ISetOperations {


    @Override
    public @NotNull TurtleProtoBuf.ResponseData exist(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "exist");
        builder.addPara(TurtleParaType.STRING, key);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData exist(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "exist");
        builder.addPara(TurtleParaType.STRING, key);
        builder.addPara(TurtleParaType.TURTLE_VALUE,value);

        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData add(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "add");
        builder.addPara(TurtleParaType.STRING, key);
        builder.addPara(TurtleParaType.TURTLE_VALUE,value);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData union(String key, String otherKey) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "union");
        builder.addPara(TurtleParaType.STRING, key);
        builder.addPara(TurtleParaType.STRING,otherKey);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData union(String key, Collection<TurtleValue> turtleValues) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "union");
        builder.addPara(TurtleParaType.STRING, key);
        builder.addPara(TurtleParaType.COLLECTION,turtleValues);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData intersect(String key, String otherKey) {

        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "intersect");
        builder.addPara(TurtleParaType.STRING, key);
        builder.addPara(TurtleParaType.STRING,otherKey);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData intersect(String key, Collection<TurtleValue> turtleValues) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "intersect");
        builder.addPara(TurtleParaType.STRING, key);
        builder.addPara(TurtleParaType.COLLECTION,turtleValues);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData difference(String key, String otherKey) {
        return null;
    }

    @Override
    public @NotNull TurtleProtoBuf.ResponseData difference(String key, Collection<TurtleValue> turtleValues) {
        return null;
    }

    @Override
    public @NotNull Collection<TurtleProtoBuf.ResponseData> entries(String key) {
        return null;
    }
}

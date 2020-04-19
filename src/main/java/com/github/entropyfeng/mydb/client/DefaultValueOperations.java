package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.TurtleParaType;
import com.github.entropyfeng.mydb.common.ops.IValueOperations;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.obj.TurtleValue;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * @author entropyfeng
 */
public class DefaultValueOperations implements IValueOperations {
    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData set(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time) {

        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "set");
        builder.addPara(TurtleParaType.STRING, key);
        builder.addPara(TurtleParaType.TURTLE_VALUE, value);
        builder.addPara(TurtleParaType.LONG, (Long) time);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData setIfAbsent(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "setIfAbsent");
        builder.addPara(TurtleParaType.STRING, key);
        builder.addPara(TurtleParaType.TURTLE_VALUE, value);
        builder.addPara(TurtleParaType.LONG, (Long) time);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData setIfPresent(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "setIfPresent");
        builder.addPara(TurtleParaType.STRING, key);
        builder.addPara(TurtleParaType.TURTLE_VALUE, value);
        builder.addPara(TurtleParaType.LONG, (Long) time);
builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData get(@NotNull String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "get");
        builder.addPara(TurtleParaType.STRING, key);
        return ClientExecute.singleExecute(builder.build());
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData increment(@NotNull String key, @NotNull Integer intValue) throws UnsupportedOperationException, NoSuchElementException {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "increment");
        builder.addPara(TurtleParaType.STRING, key);
        builder.addPara(TurtleParaType.INTEGER,intValue);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData increment(@NotNull String key, @NotNull Long longValue) throws UnsupportedOperationException, NoSuchElementException {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "increment");
        builder.addPara(TurtleParaType.STRING, key);
        builder.addPara(TurtleParaType.LONG,longValue);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData increment(@NotNull String key, @NotNull Double doubleValue) throws UnsupportedOperationException, NoSuchElementException {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "increment");
        builder.addPara(TurtleParaType.STRING, key);
        builder.addPara(TurtleParaType.DOUBLE,doubleValue);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData increment(@NotNull String key, @NotNull BigInteger bigInteger) throws UnsupportedOperationException, NoSuchElementException {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "increment");
        builder.addPara(TurtleParaType.STRING, key);
        builder.addPara(TurtleParaType.NUMBER_INTEGER,bigInteger);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData increment(@NotNull String key, @NotNull BigDecimal bigDecimal) throws UnsupportedOperationException, NoSuchElementException {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "increment");
        builder.addPara(TurtleParaType.STRING, key);
        builder.addPara(TurtleParaType.NUMBER_DECIMAL,bigDecimal);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData append(@NotNull String key, @NotNull String appendValue) throws UnsupportedOperationException, NoSuchElementException {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "append");
        builder.addPara(TurtleParaType.STRING, key);
        builder.addPara(TurtleParaType.STRING,appendValue);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @NotNull
    @Override
    public Collection<TurtleProtoBuf.ResponseData> allValues() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "allValues");
        return ClientExecute.collectionExecute(builder.build());
    }
}

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
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.addLongPara(time);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData setIfAbsent(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "setIfAbsent");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.addLongPara(time);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData setIfPresent(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "setIfPresent");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.addLongPara(time);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData get(@NotNull String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "get");
        builder.addStringPara(key);
        return ClientExecute.singleExecute(builder.build());
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData increment(@NotNull String key, @NotNull Integer intValue) throws UnsupportedOperationException, NoSuchElementException {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "increment");
        builder.addStringPara(key);
        builder.addIntegerValue(intValue);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData increment(@NotNull String key, @NotNull Long longValue) throws UnsupportedOperationException, NoSuchElementException {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "increment");
        builder.addStringPara(key);
        builder.addLongPara(longValue);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData increment(@NotNull String key, @NotNull Double doubleValue) throws UnsupportedOperationException, NoSuchElementException {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "increment");
        builder.addStringPara(key);
        builder.addDoublePara(doubleValue);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData increment(@NotNull String key, @NotNull BigInteger bigInteger) throws UnsupportedOperationException, NoSuchElementException {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "increment");
        builder.addStringPara(key);
        builder.addBigIntegerPara(bigInteger);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData increment(@NotNull String key, @NotNull BigDecimal bigDecimal) throws UnsupportedOperationException, NoSuchElementException {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "increment");
        builder.addStringPara(key);
        builder.addBigDecimalPara(bigDecimal);
        builder.setModifyAble(true);
        return ClientExecute.singleExecute(builder.build());
    }

    @NotNull
    @Override
    public TurtleProtoBuf.ResponseData append(@NotNull String key, @NotNull String appendValue) throws UnsupportedOperationException, NoSuchElementException {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "append");
        builder.addStringPara(key);
        builder.addStringPara(appendValue);
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

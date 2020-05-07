package com.github.entropyfeng.mydb.client.res;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.ops.IValueOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.Pair;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * @author entropyfeng
 */
public class ResponseValueOperations implements IValueOperations {
    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> set(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time) {

        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "set");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.addLongPara(time);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> setIfAbsent(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "setIfAbsent");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.addLongPara(time);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> setIfPresent(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "setIfPresent");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.addLongPara(time);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> get(@NotNull String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "get");
        builder.addStringPara(key);
        return ClientExecute.execute(builder);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> increment(@NotNull String key, @NotNull Integer intValue) throws UnsupportedOperationException, NoSuchElementException {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "increment");
        builder.addStringPara(key);
        builder.addIntegerPara(intValue);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> increment(@NotNull String key, @NotNull Long longValue) throws UnsupportedOperationException, NoSuchElementException {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "increment");
        builder.addStringPara(key);
        builder.addLongPara(longValue);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> increment(@NotNull String key, @NotNull Double doubleValue) throws UnsupportedOperationException, NoSuchElementException {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "increment");
        builder.addStringPara(key);
        builder.addDoublePara(doubleValue);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> increment(@NotNull String key, @NotNull BigInteger bigInteger) throws UnsupportedOperationException, NoSuchElementException {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "increment");
        builder.addStringPara(key);
        builder.addBigIntegerPara(bigInteger);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> increment(@NotNull String key, @NotNull BigDecimal bigDecimal) throws UnsupportedOperationException, NoSuchElementException {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "increment");
        builder.addStringPara(key);
        builder.addBigDecimalPara(bigDecimal);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> append(@NotNull String key, @NotNull String appendValue) throws UnsupportedOperationException, NoSuchElementException {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "append");
        builder.addStringPara(key);
        builder.addStringPara(appendValue);
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> allValues() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "allValues");
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> allEntries() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "allEntries");
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> allKeys() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "allKeys");
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> clear() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "clear");
        builder.setModifyAble(true);
        return ClientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> dump() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "dump");

        return ClientExecute.execute(builder);
    }
}

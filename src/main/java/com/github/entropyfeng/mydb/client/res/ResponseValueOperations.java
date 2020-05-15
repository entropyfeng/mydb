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
    
    private ClientExecute clientExecute;

    public ResponseValueOperations(ClientExecute clientExecute) {
        this.clientExecute = clientExecute;
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> set(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time) {

        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "set");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.addLongPara(time);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> setIfAbsent(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "setIfAbsent");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.addLongPara(time);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> setIfPresent(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "setIfPresent");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.addLongPara(time);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> get(@NotNull String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "get");
        builder.addStringPara(key);
        return clientExecute.execute(builder);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> increment(@NotNull String key, @NotNull Integer intValue) throws UnsupportedOperationException, NoSuchElementException {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "increment");
        builder.addStringPara(key);
        builder.addIntegerPara(intValue);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> increment(@NotNull String key, @NotNull Long longValue) throws UnsupportedOperationException, NoSuchElementException {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "increment");
        builder.addStringPara(key);
        builder.addLongPara(longValue);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> increment(@NotNull String key, @NotNull Double doubleValue) throws UnsupportedOperationException, NoSuchElementException {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "increment");
        builder.addStringPara(key);
        builder.addDoublePara(doubleValue);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> increment(@NotNull String key, @NotNull BigInteger bigInteger) throws UnsupportedOperationException, NoSuchElementException {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "increment");
        builder.addStringPara(key);
        builder.addBigIntegerPara(bigInteger);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> increment(@NotNull String key, @NotNull BigDecimal bigDecimal) throws UnsupportedOperationException, NoSuchElementException {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "increment");
        builder.addStringPara(key);
        builder.addBigDecimalPara(bigDecimal);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> append(@NotNull String key, @NotNull String appendValue) throws UnsupportedOperationException, NoSuchElementException {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "append");
        builder.addStringPara(key);
        builder.addStringPara(appendValue);
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> allValues() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "allValues");
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> allEntries() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "allEntries");
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> allKeys() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "allKeys");
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> clear() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "clear");
        builder.setModifyAble(true);
        return clientExecute.execute(builder);
    }

    @Override
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> dump() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "dump");

        return clientExecute.execute(builder);
    }
}

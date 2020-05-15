package com.github.entropyfeng.mydb.client.ops;

import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.client.conn.ClientResHelper;
import com.github.entropyfeng.mydb.client.res.ResponseHashOperations;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.ops.HashOperations;
import com.github.entropyfeng.mydb.common.ops.IHashOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public class DefaultHashOperations implements HashOperations {
    public DefaultHashOperations(ClientExecute clientExecute) {
        this.hashOperations = new ResponseHashOperations(clientExecute);
    }

    private IHashOperations hashOperations;

    @Override
    public TurtleValue get(@NotNull String key, @NotNull TurtleValue tKey) {

        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = hashOperations.get(key, tKey);

        return ClientResHelper.turtleValueRes(pair);
    }

    @Override
    public Collection<TurtleValue> get(@NotNull String key) {

        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = hashOperations.get(key);

        return ClientResHelper.turtleCollection(pair);
    }

    @Override
    public Boolean exists(@NotNull String key, @NotNull TurtleValue tKey, TurtleValue tValue) {

        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = hashOperations.exists(key, tKey, tValue);

        return ClientResHelper.boolRes(pair);
    }

    @Override
    public Boolean exists(@NotNull String key) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = hashOperations.exists(key);

        return ClientResHelper.boolRes(pair);
    }

    @Override
    public void put(@NotNull String key, @NotNull TurtleValue tKey, @NotNull TurtleValue tValue) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = hashOperations.put(key, tKey, tValue);

        ClientResHelper.voidRes(pair);
    }

    @Override
    public Boolean delete(@NotNull String key) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = hashOperations.delete(key);

        return ClientResHelper.boolRes(pair);
    }

    @Override
    public Boolean delete(@NotNull String key, @NotNull TurtleValue tKey) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = hashOperations.delete(key, tKey);

        return ClientResHelper.boolRes(pair);
    }

    @Override
    public Integer sizeOf(@NotNull String key) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = hashOperations.sizeOf(key);

        return ClientResHelper.integerRes(pair);
    }
}

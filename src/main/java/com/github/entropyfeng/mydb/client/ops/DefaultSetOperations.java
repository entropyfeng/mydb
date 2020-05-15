package com.github.entropyfeng.mydb.client.ops;

import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.client.conn.ClientResHelper;
import com.github.entropyfeng.mydb.client.res.ResponseSetOperations;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.ops.ISetOperations;
import com.github.entropyfeng.mydb.common.ops.SetOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public class DefaultSetOperations implements SetOperations {
    public DefaultSetOperations(ClientExecute clientExecute) {
        this.setOperations = new ResponseSetOperations(clientExecute);
    }

    private ISetOperations setOperations;

    @Override
    public Boolean exist(String key) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = setOperations.exist(key);
        return ClientResHelper.boolRes(pair);

    }

    @Override
    public Boolean exist(String key, TurtleValue value) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = setOperations.exist(key, value);
        return ClientResHelper.boolRes(pair);
    }

    @Override
    public void add(String key, TurtleValue value) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = setOperations.add(key, value);
        ClientResHelper.voidRes(pair);
    }

    @Override
    public Collection<TurtleValue> union(String key, String otherKey) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = setOperations.union(key, otherKey);
        return ClientResHelper.turtleCollection(pair);
    }

    @Override
    public Collection<TurtleValue> union(String key, Collection<TurtleValue> turtleValues) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = setOperations.union(key, turtleValues);

        return ClientResHelper.turtleCollection(pair);
    }

    @Override
    public Collection<TurtleValue> intersect(String key, String otherKey) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = setOperations.intersect(key, otherKey);

        return ClientResHelper.turtleCollection(pair);
    }

    @Override
    public Collection<TurtleValue> intersect(String key, Collection<TurtleValue> turtleValues) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = setOperations.intersect(key, turtleValues);
        return ClientResHelper.turtleCollection(pair);
    }

    @Override
    public Collection<TurtleValue> difference(String key, String otherKey) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = setOperations.difference(key, otherKey);

        return ClientResHelper.turtleCollection(pair);
    }

    @Override
    public Collection<TurtleValue> difference(String key, Collection<TurtleValue> turtleValues) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = setOperations.difference(key, turtleValues);

        return ClientResHelper.turtleCollection(pair);
    }

    @Override
    public Collection<TurtleValue> entries(String key) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = setOperations.entries(key);

        return ClientResHelper.turtleCollection(pair);
    }
}

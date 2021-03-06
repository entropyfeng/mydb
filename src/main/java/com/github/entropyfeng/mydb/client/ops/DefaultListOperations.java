package com.github.entropyfeng.mydb.client.ops;

import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.client.conn.ClientResHelper;
import com.github.entropyfeng.mydb.client.res.ResponseListOperations;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.ops.IListOperations;
import com.github.entropyfeng.mydb.common.ops.ListOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public class DefaultListOperations implements ListOperations {
    public DefaultListOperations(ClientExecute clientExecute) {
        this.listOperations = new ResponseListOperations(clientExecute);
    }

    private IListOperations listOperations;


    @Override
    public Integer size() {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = listOperations.size();
        return ClientResHelper.integerRes(pair);

    }

    @Override
    public Integer sizeOf(String key) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = listOperations.sizeOf(key);
        return ClientResHelper.integerRes(pair);
    }

    @Override
    public void leftPush(String key, TurtleValue value) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = listOperations.leftPush(key, value);
        ClientResHelper.voidRes(pair);
    }

    @Override
    public void leftPushAll(String key, Collection<TurtleValue> values) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = listOperations.leftPushAll(key, values);
        ClientResHelper.voidRes(pair);
    }

    @Override
    public Boolean leftPushIfPresent(String key, TurtleValue value) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = listOperations.leftPushIfPresent(key, value);
        return ClientResHelper.boolRes(pair);
    }

    @Override
    public Boolean leftPushIfAbsent(String key, TurtleValue value) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = listOperations.leftPushIfAbsent(key, value);
        return ClientResHelper.boolRes(pair);
    }

    @Override
    public void rightPush(String key, TurtleValue value) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = listOperations.rightPush(key, value);
        ClientResHelper.voidRes(pair);
    }

    @Override
    public void rightPushAll(String key, Collection<TurtleValue> values) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = listOperations.rightPushAll(key, values);
        ClientResHelper.voidRes(pair);
    }

    @Override
    public Boolean rightPushIfPresent(String key, TurtleValue value) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = listOperations.rightPushIfPresent(key, value);
        return ClientResHelper.boolRes(pair);
    }

    @Override
    public Boolean rightPushIfAbsent(String key, TurtleValue value) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = listOperations.rightPushIfAbsent(key, value);
        return ClientResHelper.boolRes(pair);
    }

    @Override
    public TurtleValue leftPop(String key) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = listOperations.leftPop(key);
        return ClientResHelper.turtleValueRes(pair);
    }

    @Override
    public TurtleValue left(String key) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = listOperations.left(key);
        return ClientResHelper.turtleValueRes(pair);
    }

    @Override
    public TurtleValue rightPop(String key) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = listOperations.rightPop(key);
        return ClientResHelper.turtleValueRes(pair);
    }

    @Override
    public TurtleValue right(String key) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = listOperations.right(key);
        return ClientResHelper.turtleValueRes(pair);
    }

    @Override
    public void clear(String key) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = listOperations.clear(key);
        ClientResHelper.voidRes(pair);
    }

    @Override
    public void clear() {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = listOperations.clear();
        ClientResHelper.voidRes(pair);
    }

    @Override
    public Boolean exist(String key) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = listOperations.exist(key);
        return ClientResHelper.boolRes(pair);
    }

    @Override
    public Boolean exist(String key, TurtleValue value) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = listOperations.exist(key, value);
        return ClientResHelper.boolRes(pair);
    }
}

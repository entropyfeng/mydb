package com.github.entropyfeng.mydb.client.ops;

import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.client.conn.ClientResHelper;
import com.github.entropyfeng.mydb.client.res.ResponseOrderSetOperations;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.ops.IOrderSetOperations;
import com.github.entropyfeng.mydb.common.ops.OrderSetOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public class DefaultOrderSetOperations implements OrderSetOperations {
    public DefaultOrderSetOperations(ClientExecute clientExecute) {

        this.setOperations = new ResponseOrderSetOperations(clientExecute);
    }

    private IOrderSetOperations setOperations;

    @Override
    public Boolean exists(String key, TurtleValue value, Double score) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = setOperations.exists(key, value, score);

        return ClientResHelper.boolRes(pair);
    }

    @Override
    public Boolean exists(String key, TurtleValue value) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = setOperations.exists(key, value);

        return ClientResHelper.boolRes(pair);
    }

    @Override
    public Boolean exists(String key) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = setOperations.exists(key);

        return ClientResHelper.boolRes(pair);
    }

    @Override
    public Boolean add(String key, TurtleValue value, Double score) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = setOperations.add(key, value, score);

        return ClientResHelper.boolRes(pair);
    }

    @Override
    public Integer add(String key, Collection<TurtleValue> values, Collection<Double> doubles) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = setOperations.add(key, values, doubles);

        return ClientResHelper.integerRes(pair);
    }

    @Override
    public Integer count(String key, Double begin, Double end) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = setOperations.count(key, begin, end);

        return ClientResHelper.integerRes(pair);
    }

    @Override
    public Collection<TurtleValue> range(String key, Double begin, Double end) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = setOperations.range(key, begin, end);

        return ClientResHelper.turtleCollection(pair);
    }

    @Override
    public Boolean inRange(String key, Double begin, Double end) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = setOperations.inRange(key, begin, end);

        return ClientResHelper.boolRes(pair);
    }

    @Override
    public Boolean delete(String key, TurtleValue value, Double score) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = setOperations.delete(key, value, score);

        return ClientResHelper.boolRes(pair);
    }

    @Override
    public Integer delete(String key, Double begin, Double end) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = setOperations.delete(key, begin, end);

        return ClientResHelper.integerRes(pair);
    }

    @Override
    public Boolean delete(String key, TurtleValue value) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = setOperations.delete(key, value);

        return ClientResHelper.boolRes(pair);
    }

    @Override
    public Integer delete(String key) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = setOperations.delete(key);

        return ClientResHelper.integerRes(pair);
    }

}

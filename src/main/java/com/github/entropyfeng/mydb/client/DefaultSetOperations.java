package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.client.conn.ClientResHelper;
import com.github.entropyfeng.mydb.client.ops.ResponseSetOperations;
import com.github.entropyfeng.mydb.common.ops.ISetOperations;
import com.github.entropyfeng.mydb.common.ops.SetOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.common.protobuf.ResHelper;
import com.github.entropyfeng.mydb.core.TurtleValue;
import com.github.entropyfeng.mydb.core.helper.Pair;

import java.util.Collection;

/**

 * @author entropyfeng
 */
public class DefaultSetOperations implements SetOperations {
    private ISetOperations setOperations = new ResponseSetOperations();

    @Override
    public Boolean exist(String key) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> pair = setOperations.exist(key);
        return ClientResHelper.boolRes(pair);

    }

    @Override
    public Boolean exist(String key, TurtleValue value) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> pair = setOperations.exist(key,value);
        return ClientResHelper.boolRes(pair);
    }

    @Override
    public void add(String key, TurtleValue value) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> pair = setOperations.add(key,value);
      ClientResHelper.voidRes(pair);
    }

    @Override
    public Collection<TurtleValue> union(String key, String otherKey) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> pair = setOperations.union(key,otherKey);
      return ClientResHelper.turtleCollection(pair);
    }

    @Override
    public Collection<TurtleValue> union(String key, Collection<TurtleValue> turtleValues) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> pair = setOperations.union(key,turtleValues);

        return ClientResHelper.turtleCollection(pair);
    }

    @Override
    public Collection<TurtleValue> intersect(String key, String otherKey) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> pair = setOperations.intersect(key,otherKey);

        return ClientResHelper.turtleCollection(pair);
    }

    @Override
    public Collection<TurtleValue> intersect(String key, Collection<TurtleValue> turtleValues) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> pair = setOperations.intersect(key,turtleValues);
        return ClientResHelper.turtleCollection(pair);
    }

    @Override
    public Collection<TurtleValue> difference(String key, String otherKey) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> pair = setOperations.difference(key,otherKey);

        return ClientResHelper.turtleCollection(pair);
    }

    @Override
    public Collection<TurtleValue> difference(String key, Collection<TurtleValue> turtleValues) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> pair = setOperations.difference(key,turtleValues);

        return ClientResHelper.turtleCollection(pair);
    }

    @Override
    public Collection<TurtleValue> entries(String key) {
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> pair = setOperations.entries(key);

        return ClientResHelper.turtleCollection(pair);
    }
}

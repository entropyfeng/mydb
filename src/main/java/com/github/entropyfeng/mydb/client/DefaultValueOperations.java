package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.client.conn.ClientResHelper;
import com.github.entropyfeng.mydb.client.ops.ResponseValueOperations;
import com.github.entropyfeng.mydb.common.exception.ElementOutOfBoundException;
import com.github.entropyfeng.mydb.common.ops.IValueOperations;
import com.github.entropyfeng.mydb.common.ops.ValueOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResBody;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;
import com.github.entropyfeng.mydb.core.TurtleValue;
import com.github.entropyfeng.mydb.core.helper.Pair;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * @author entropyfeng
 *
 * all res type is nullable
 */
public class DefaultValueOperations implements ValueOperations {
    private IValueOperations valueOperations = new ResponseValueOperations();


    @Override
    public void set(String key, TurtleValue value, Long time) throws ElementOutOfBoundException {

        Pair<ResHead, Collection<ResBody>> pair = valueOperations.set(key, value, time);

        ClientResHelper.voidRes(pair);


    }

    @Override
    public Boolean setIfAbsent(String key, TurtleValue value, Long time) throws ElementOutOfBoundException {
        Pair<ResHead, Collection<ResBody>> pair = valueOperations.setIfAbsent(key, value, time);

        return ClientResHelper.boolRes(pair);

    }

    @Override
    public Boolean setIfPresent(String key, TurtleValue value, Long time) {
        Pair<ResHead, Collection<ResBody>> pair = valueOperations.setIfPresent(key, value, time);

        return ClientResHelper.boolRes(pair);
    }

    @Override
    public TurtleValue get(String key) {
        Pair<ResHead, Collection<ResBody>> pair = valueOperations.get(key);
        return ClientResHelper.turtleValueRes(pair);
    }

    @Override
    public TurtleValue increment(String key, Integer intValue) throws UnsupportedOperationException, NoSuchElementException {
        Pair<ResHead, Collection<ResBody>> pair = valueOperations.increment(key, intValue);
        return ClientResHelper.turtleValueRes(pair);
    }

    @Override
    public TurtleValue increment(String key, Long longValue) throws UnsupportedOperationException, NoSuchElementException {
        Pair<ResHead, Collection<ResBody>> pair = valueOperations.increment(key, longValue);
        return ClientResHelper.turtleValueRes(pair);
    }

    @Override
    public TurtleValue increment(String key, Double doubleValue) throws UnsupportedOperationException, NoSuchElementException {
        Pair<ResHead, Collection<ResBody>> pair = valueOperations.increment(key, doubleValue);
        return ClientResHelper.turtleValueRes(pair);
    }

    @Override
    public TurtleValue increment(String key, BigInteger bigInteger) throws UnsupportedOperationException, NoSuchElementException {
        Pair<ResHead, Collection<ResBody>> pair = valueOperations.increment(key, bigInteger);
        return ClientResHelper.turtleValueRes(pair);
    }

    @Override
    public TurtleValue increment(String key, BigDecimal bigDecimal) throws UnsupportedOperationException, NoSuchElementException {
        Pair<ResHead, Collection<ResBody>> pair = valueOperations.increment(key, bigDecimal);
        return ClientResHelper.turtleValueRes(pair);
    }

    @Override
    public void append(String key, String appendValue) throws UnsupportedOperationException, NoSuchElementException {
        Pair<ResHead, Collection<ResBody>> pair = valueOperations.append(key, appendValue);
        ClientResHelper.voidRes(pair);
    }

    @Override
    public Collection<TurtleValue> allValues() {
        Pair<ResHead, Collection<ResBody>> pair = valueOperations.allValues();
        return ClientResHelper.turtleCollection(pair);
    }

    @Override
    public Collection<String> allKeys() {
        Pair<ResHead, Collection<ResBody>> pair = valueOperations.allValues();
        return ClientResHelper.stringCollection(pair);
    }

    @Override
    public Collection<Pair<String, TurtleValue>> allEntries() {
        Pair<ResHead, Collection<ResBody>> pair = valueOperations.allValues();
        return ClientResHelper.stringTurtleCollection(pair);
    }
}

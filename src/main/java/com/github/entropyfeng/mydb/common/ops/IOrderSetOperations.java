package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public interface IOrderSetOperations {

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> exists(String key, TurtleValue value, Double score);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> exists(String key, TurtleValue value);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> exists(String key);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> add(String key, TurtleValue value, Double score);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> add(String key, Collection<TurtleValue> values, Collection<Double> doubles);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> count(String key, Double begin, Double end);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> range(String key, Double begin, Double end);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> inRange(String key, Double begin, Double end);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> delete(String key, TurtleValue value, Double score);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> delete(String key, Double begin, Double end);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> delete(String key, TurtleValue value);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> delete(String key);


    @NotNull Pair<ProtoBuf.ResHead,Collection<ProtoBuf.ResBody>> clear();

}

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

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> exists(String key, TurtleValue value, Double score);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> exists(String key, TurtleValue value);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> exists(String key);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> add(String key, TurtleValue value, Double score);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> add(String key, Collection<TurtleValue> values, Collection<Double> doubles);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> count(String key, Double begin, Double end);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> range(String key, Double begin, Double end);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> inRange(String key, Double begin, Double end);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> delete(String key, TurtleValue value, Double score);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> delete(String key, Double begin, Double end);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> delete(String key, TurtleValue value);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> delete(String key);


    @NotNull Pair<ProtoBuf.ResHead,Collection<ProtoBuf.DataBody>> clear();

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> dump();

}

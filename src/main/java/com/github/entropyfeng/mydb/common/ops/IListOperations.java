package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author entropyfeng
 */
public interface IListOperations {

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> size();

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> sizeOf(String key);

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> leftPush(String key, TurtleValue value);

    default @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> leftPushAll(String key, TurtleValue... values) {
       return leftPushAll(key, Arrays.asList(values));
       
    }

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> leftPushAll(String key, Collection<TurtleValue> values);
    
    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> leftPushIfPresent(String key, TurtleValue value);

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> leftPushIfAbsent(String key, TurtleValue value);

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> rightPush(String key, TurtleValue value);


    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> rightPushAll(String key, Collection<TurtleValue> values);

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> rightPushIfPresent(String key, TurtleValue value);

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> rightPushIfAbsent(String key, TurtleValue value);

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> leftPop(String key);
    
    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> left(String key);

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> rightPop(String key);

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> right(String key);

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> clear(String key);

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> clear();

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> exist(String key);

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> exist(String key, TurtleValue value);

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> dump();
}

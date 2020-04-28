package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;
import com.github.entropyfeng.mydb.core.helper.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;

/**
 * @author entropyfeng
 */
public interface IListOperations {

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> size();

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> sizeOf(String key);

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> leftPush(String key, TurtleValue value);

    default @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> leftPushAll(String key, TurtleValue... values) {
       return leftPushAll(key, Arrays.asList(values));
       
    }

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> leftPushAll(String key, Collection<TurtleValue> values);
    
    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> leftPushIfPresent(String key, TurtleValue value);

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> leftPushIfAbsent(String key, TurtleValue value);

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> rightPush(String key, TurtleValue value);


    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> rightPushAll(String key, Collection<TurtleValue> values);

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> rightPushIfPresent(String key, TurtleValue value);

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> rightPushIfAbsent(String key, TurtleValue value);

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> leftPop(String key);
    
    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> left(String key);

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> rightPop(String key);

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> right(String key);

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> clear(String key);

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> clearAll();

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> exist(String key);

    @NotNull  Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> exist(String key, TurtleValue value);
}

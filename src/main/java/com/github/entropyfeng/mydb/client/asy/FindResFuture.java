package com.github.entropyfeng.mydb.client.asy;

import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;

import java.util.Collection;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author entropyfeng
 */
public class FindResFuture implements Callable<Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>>> {

    private Long requestId;
    private  ConcurrentHashMap<Long, Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>>> globalMap;

    public FindResFuture(Long requestId, ConcurrentHashMap<Long, Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>>> globalMap) {
        this.requestId = requestId;
        this.globalMap = globalMap;
    }

    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> call() {
        while (globalMap.containsKey(requestId)){

        }
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>>  responseData = globalMap.get(requestId);
        globalMap.remove(requestId);
        return responseData;

    }
}

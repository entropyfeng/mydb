package com.github.entropyfeng.mydb.client.conn;

import com.github.entropyfeng.mydb.common.Pair;


import java.util.Collection;

import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.*;

/**
 * @author entropyfeng
 */
public interface IClientExecute {

    /**
     * 当对应的请求数据返回后，会触发该函数
     * @param responseId 与requestId对应
     * @param pair 返回值
     */
    void dispatch(Long responseId, Pair<ResHead, Collection<DataBody>> pair);
}

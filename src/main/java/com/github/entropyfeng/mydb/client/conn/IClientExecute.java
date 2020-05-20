package com.github.entropyfeng.mydb.client.conn;

import com.github.entropyfeng.mydb.common.Pair;


import java.util.Collection;

import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.*;

public interface IClientExecute {

    public void dispatch(Long responseId, Pair<ResHead, Collection<DataBody>> pair);
}

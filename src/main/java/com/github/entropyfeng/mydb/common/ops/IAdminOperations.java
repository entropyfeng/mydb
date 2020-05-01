package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.common.Pair;

import java.util.Collection;

import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResBody;
import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;

/**
 * @author entropyfeng
 */
public interface IAdminOperations {

    Pair<ResHead, Collection<ResBody>> clear();

    Pair<ResHead, Collection<ResBody>> lazyClear();

    Pair<ResHead, Collection<ResBody>> dump();

    Pair<ResHead, Collection<ResBody>> lazyDump();
}

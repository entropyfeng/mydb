package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.common.Pair;

import java.util.Collection;

import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.DataBody;
import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;

/**
 * @author entropyfeng
 */
public interface IAdminOperations {

    Pair<ResHead, Collection<DataBody>> clear();

    Pair<ResHead, Collection<DataBody>> lazyClear();

    Pair<ResHead, Collection<DataBody>> dump();

    Pair<ResHead, Collection<DataBody>> lazyDump();

    /**
     * delete all dump files
     * @return
     */
    Pair<ResHead,Collection<DataBody>> deleteAllDump();
    /**
     * make this server to be the slave server of destination server
     * @param host the host of the destination server
     * @param port the port of the destination server
     * @return {@link Pair}
     */
    Pair<ResHead,Collection<DataBody>> slaveOf(String host,Integer port);

    default void closeClient(){

    }
}

package com.github.entropyfeng.mydb.client.ops;

import com.github.entropyfeng.mydb.client.conn.ClientResHelper;
import com.github.entropyfeng.mydb.client.res.ResponseAdminOperations;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.ops.AdminOperations;
import com.github.entropyfeng.mydb.common.ops.IAdminOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.DataBody;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public class DefaultAdminOperations implements AdminOperations {
    private IAdminOperations adminOperations = new ResponseAdminOperations();

    @Override
    public void clear() {
        Pair<ResHead, Collection<DataBody>> pair = adminOperations.clear();

        ClientResHelper.voidRes(pair);
    }

    @Override
    public void lazyClear() {
        Pair<ResHead, Collection<DataBody>> pair = adminOperations.lazyClear();

        ClientResHelper.voidRes(pair);
    }

    @Override
    public void dump() {
        Pair<ResHead, Collection<DataBody>> pair = adminOperations.dump();

        ClientResHelper.voidRes(pair);
    }

    @Override
    public void lazyDump() {
        Pair<ResHead, Collection<DataBody>> pair = adminOperations.lazyDump();

        ClientResHelper.voidRes(pair);
    }

    @Override
    public void deleteAllDumps() {
        Pair<ResHead, Collection<DataBody>> pair = adminOperations.deleteAllDump();

        ClientResHelper.voidRes(pair);
    }
    @Override
    public void slaveOf(String host, Integer port){

        Pair<ResHead, Collection<DataBody>> pair= adminOperations.slaveOf(host, port);
        ClientResHelper.voidRes(pair);
    }
}

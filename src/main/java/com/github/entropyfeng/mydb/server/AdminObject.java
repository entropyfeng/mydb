package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author entropyfeng
 */
public class AdminObject {
    private static final Logger logger = LoggerFactory.getLogger(AdminObject.class);

    private ServerDomain serverDomain;

    private AtomicBoolean runningFlag;

    public AdminObject(ServerDomain serverDomain,AtomicBoolean runningFlag) {
        this.serverDomain = serverDomain;
        this.runningFlag=runningFlag;
    }


    Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> clear(){



        serverDomain.valuesDomain.clear();
        serverDomain.listDomain.clear();
        serverDomain.setDomain.clear();
        serverDomain.orderSetDomain.clear();
        serverDomain.hashDomain.clear();

        return ResServerHelper.emptyRes();
    }


    public void ordinaryDump(){


    }

    public void dump(){

        logger.info("dump begin");
        logger.info("request complete all task in all domains .");
        ServerDomain.interrupted.set(true);
        logger.info("all queue is empty .");
        logger.info("begin dump .");
        PersistenceHelper.dumpAll(serverDomain);
        logger.info("after dump .");

    }

}

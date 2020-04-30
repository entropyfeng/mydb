package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.server.command.ClientCommand;
import com.github.entropyfeng.mydb.server.core.domain.ListDomain;
import com.github.entropyfeng.mydb.server.core.domain.OrderSetDomain;
import com.github.entropyfeng.mydb.server.core.domain.ValuesDomain;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
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


    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> lazyClear(){
        try {
          Method valuesMethod=  ValuesDomain.class.getMethod("clear");
          Method listMethod= ListDomain.class.getMethod("clear");
          Method setMethod=ListDomain.class.getMethod("clear");
          Method hashMethod=ListDomain.class.getMethod("clear");
          Method orderSetMethod= OrderSetDomain.class.getMethod("clear");
            serverDomain.valuesQueue.add(new ClientCommand(valuesMethod,null,null,null));
            serverDomain.listQueue.add(new ClientCommand(listMethod,null,null,null));
            serverDomain.setQueue.add(new ClientCommand(setMethod,null,null,null));
            serverDomain.hashQueue.add(new ClientCommand(hashMethod,null,null,null));
            serverDomain.orderSetQueue.add(new ClientCommand(orderSetMethod,null,null,null));
        } catch (NoSuchMethodException e) {
            logger.error("not find method->{}",e.getMessage());
        }

        return ResServerHelper.emptyRes();
    }

    @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> lazyDump(){
        try {
            Method valuesMethod=  ValuesDomain.class.getMethod("");
            Method listMethod= ListDomain.class.getMethod("clear");
            Method setMethod=ListDomain.class.getMethod("clear");
            Method hashMethod=ListDomain.class.getMethod("clear");
            Method orderSetMethod= OrderSetDomain.class.getMethod("clear");
            serverDomain.valuesQueue.add(new ClientCommand(valuesMethod,null,null,null));
            serverDomain.listQueue.add(new ClientCommand(listMethod,null,null,null));
            serverDomain.setQueue.add(new ClientCommand(setMethod,null,null,null));
            serverDomain.hashQueue.add(new ClientCommand(hashMethod,null,null,null));
            serverDomain.orderSetQueue.add(new ClientCommand(orderSetMethod,null,null,null));
        } catch (NoSuchMethodException e) {
            logger.error("not find method->{}",e.getMessage());
        }

        return ResServerHelper.emptyRes();
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

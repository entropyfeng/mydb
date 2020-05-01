package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.ops.IAdminOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.server.command.ClientCommand;
import com.github.entropyfeng.mydb.server.config.Constant;
import com.github.entropyfeng.mydb.server.config.ServerConfig;
import com.github.entropyfeng.mydb.server.domain.ListDomain;
import com.github.entropyfeng.mydb.server.domain.OrderSetDomain;
import com.github.entropyfeng.mydb.server.domain.ValuesDomain;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Collection;

/**
 * @author entropyfeng
 */
public class AdminObject implements IAdminOperations {
    private static final Logger logger = LoggerFactory.getLogger(AdminObject.class);

    private ServerDomain serverDomain;


    public AdminObject(ServerDomain serverDomain) {
        this.serverDomain = serverDomain;
    }


    @Override
   public  @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> lazyClear() {
        try {
            Method valuesMethod = ValuesDomain.class.getMethod("clear");
            Method listMethod = ListDomain.class.getMethod("clear");
            Method setMethod = ListDomain.class.getMethod("clear");
            Method hashMethod = ListDomain.class.getMethod("clear");
            Method orderSetMethod = OrderSetDomain.class.getMethod("clear");
            doAllDomains(valuesMethod, listMethod, setMethod, hashMethod, orderSetMethod);
        } catch (NoSuchMethodException e) {
            logger.error("not find method->{}", e.getMessage());
        }

        return ResServerHelper.emptyRes();
    }

    @Override
    @NotNull
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> lazyDump() {
        try {
            Method valuesMethod = ValuesDomain.class.getMethod("dump");
            Method listMethod = ListDomain.class.getMethod("dump");
            Method setMethod = ListDomain.class.getMethod("dump");
            Method hashMethod = ListDomain.class.getMethod("dump");
            Method orderSetMethod = OrderSetDomain.class.getMethod("dump");
            doAllDomains(valuesMethod, listMethod, setMethod, hashMethod, orderSetMethod);
        } catch (NoSuchMethodException e) {
            logger.error("not find method->{}", e.getMessage());
        }

        return ResServerHelper.emptyRes();
    }



    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> clear() {

        //all blocking queue will not accept new client command util the server is not blocking
        ServerConfig.serverBlocking.set(true);
        //wait util all consumer thread consumer its task
        while (ServerConfig.blockingDomainNumber.get()!= Constant.CONSUMER_THREAD_NUMBER){

        }
        serverDomain.valuesDomain.clear();
        serverDomain.listDomain.clear();
        serverDomain.setDomain.clear();
        serverDomain.orderSetDomain.clear();
        serverDomain.hashDomain.clear();

        ServerConfig.blockingDomainNumber.set(0);
        ServerConfig.serverBlocking.set(false);


        return ResServerHelper.emptyRes();
    }

    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> dump() {

        logger.info("dump begin");
        logger.info("request complete all task in all domains .");
        ServerConfig.serverBlocking.set(true);
        while (ServerConfig.blockingDomainNumber.get()!= Constant.CONSUMER_THREAD_NUMBER){

        }
        logger.info("all queue is empty .");
        logger.info("begin dump .");
        PersistenceHelper.dumpAll(serverDomain);
        logger.info("after dump .");

        ServerConfig.blockingDomainNumber.set(0);
        ServerConfig.serverBlocking.set(false);


        return ResServerHelper.emptyRes();
    }


    private void doAllDomains(Method valuesMethod, Method listMethod, Method setMethod, Method hashMethod, Method orderSetMethod) {
        serverDomain.valuesQueue.add(new ClientCommand(valuesMethod, null, null, null));
        serverDomain.listQueue.add(new ClientCommand(listMethod, null, null, null));
        serverDomain.setQueue.add(new ClientCommand(setMethod, null, null, null));
        serverDomain.hashQueue.add(new ClientCommand(hashMethod, null, null, null));
        serverDomain.orderSetQueue.add(new ClientCommand(orderSetMethod, null, null, null));
    }
}

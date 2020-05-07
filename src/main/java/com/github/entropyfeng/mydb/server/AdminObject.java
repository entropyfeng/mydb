package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.client.TurtleClient;
import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.client.conn.ClientThreadFactory;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.RequestIdPool;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.ops.IAdminOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.server.command.ClientRequest;
import com.github.entropyfeng.mydb.server.config.ServerConfig;
import com.github.entropyfeng.mydb.server.domain.*;
import com.github.entropyfeng.mydb.server.persistence.PersistenceObjectDomain;
import io.netty.channel.Channel;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;


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
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> lazyClear() {
        try {
            Method valuesMethod = ValuesDomain.class.getMethod("clear");
            Method listMethod = ListDomain.class.getMethod("clear");
            Method setMethod = ListDomain.class.getMethod("clear");
            Method hashMethod = ListDomain.class.getMethod("clear");
            Method orderSetMethod = OrderSetDomain.class.getMethod("clear");
            lazyMethod(valuesMethod, listMethod, setMethod, hashMethod, orderSetMethod);
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
            Method setMethod = SetDomain.class.getMethod("dump");
            Method hashMethod = HashDomain.class.getMethod("dump");
            Method orderSetMethod = OrderSetDomain.class.getMethod("dump");
            lazyMethod(valuesMethod, listMethod, setMethod, hashMethod, orderSetMethod);
        } catch (NoSuchMethodException e) {
            logger.error("not find method->{}", e.getMessage());
        }

        return ResServerHelper.emptyRes();
    }

    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> deleteAllDump() {
        PersistenceHelper.deleteDumpFiles();
        return ResServerHelper.emptyRes();
    }

    /**
     * appear at salve server
     * 从服务器向主服务器发送请求
     * @param host the host of the destination server
     * @param port the port of the destination server
     * @return {@link Pair}
     */
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> slaveOf(String host, Integer port) {

        ClientCommandBuilder clientCommandBuilder = new ClientCommandBuilder(TurtleModel.ADMIN, "slaveOfServer");
        clientCommandBuilder.addStringPara(ServerConfig.serverHost);
        clientCommandBuilder.addIntegerPara(ServerConfig.port);

        TurtleClient turtleClient = new TurtleClient(host, port);


        new ClientThreadFactory().newThread(() -> {

            try {
                turtleClient.start();
            } catch (InterruptedException e) {
                logger.error(e.getCause().toString());
            }

        });
        Channel channel = turtleClient.getChannel();
        Long requestId=RequestIdPool.getAndIncrement();
        clientCommandBuilder.writeChannel(channel, requestId);
        ConcurrentHashMap<Long, Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>>> resMap=ClientExecute.resMap;
        while (!resMap.containsKey(requestId)) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> pair= resMap.get(requestId);

        PersistenceObjectDomain domain= PersistenceHelper.dumpAndReLoadFromPair(pair);

        serverDomain.replace(domain.getValuesDomain(),domain.getListDomain(),domain.getSetDomain(),domain.getHashDomain(),domain.getOrderSetDomain());
        //-------
        ClientCommandBuilder commandBuilder=new ClientCommandBuilder(TurtleModel.ADMIN,"exceptAcceptData");
        clientCommandBuilder.addStringPara(ServerConfig.serverHost);
        clientCommandBuilder.addIntegerPara(ServerConfig.port);
        commandBuilder.writeChannel(channel,RequestIdPool.getAndIncrement());
        return ResServerHelper.emptyRes();
    }


    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> slaveOfServer(String host,Integer port) {

        MasterSlaveHelper.registerSlave(host, port);
        dump();
        return PersistenceHelper.transDumpFile();

    }

    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> exceptAcceptData(String host,Integer port){

        return ResServerHelper.emptyRes();
    }
    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> clear() {
        logger.info("clear begin");
        logger.info("all consumer threads will blocking");
        ServerConfig.serverBlocking.set(true);

        try {
            ServerConfig.threadCountDown.await();
            logger.info("all consumer threads is blocking");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PersistenceHelper.clearAll(serverDomain);

        ServerConfig.threadCountDown = new CountDownLatch(5);
        ServerConfig.serverBlocking.set(false);
        serverDomain.notifyAllValuesThread();

        logger.info("clear end");
        return ResServerHelper.emptyRes();

    }

    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> dump() {

        logger.info("dump begin");
        logger.info("all consumer threads will blocking");
        ServerConfig.serverBlocking.set(true);

        try {
            ServerConfig.threadCountDown.await();
            logger.info("all consumer threads is blocking");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        PersistenceHelper.dumpAll(serverDomain);

        ServerConfig.threadCountDown = new CountDownLatch(5);
        ServerConfig.serverBlocking.set(false);
        serverDomain.notifyAllValuesThread();

        logger.info("dump end");
        return ResServerHelper.emptyRes();
    }

    public void lazyMethod(Method valuesMethod, Method listMethod, Method setMethod, Method hashMethod, Method orderSetMethod) {
        serverDomain.valuesQueue.add(new ClientRequest(valuesMethod));
        serverDomain.listQueue.add(new ClientRequest(listMethod));
        serverDomain.setQueue.add(new ClientRequest(setMethod));
        serverDomain.hashQueue.add(new ClientRequest(hashMethod));
        serverDomain.orderSetQueue.add(new ClientRequest(orderSetMethod));
    }

}

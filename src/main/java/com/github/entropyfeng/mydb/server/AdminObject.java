package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.client.TurtleClient;
import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.RequestIdPool;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.ops.IAdminOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.server.command.ClientRequest;
import com.github.entropyfeng.mydb.server.config.ServerConfig;
import com.github.entropyfeng.mydb.server.domain.*;
import io.netty.channel.Channel;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.RandomAccessFile;
import java.lang.reflect.Method;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.util.Collection;
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

    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> slaveOf(String host, Integer port) {

        ClientCommandBuilder clientCommandBuilder = new ClientCommandBuilder(TurtleModel.ADMIN, "slaveOfServer");


        TurtleClient turtleClient = new TurtleClient(host, port);

        try {
            turtleClient.start();
           Channel channel= turtleClient.getChannel();
           clientCommandBuilder.writeChannel(channel, RequestIdPool.getAndIncrement());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return ResServerHelper.emptyRes();
    }


    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> slaveOfServer() {

        dump();

        return null;
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

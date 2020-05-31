package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.ops.IAdminOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.server.command.ClientRequest;
import com.github.entropyfeng.mydb.server.config.ServerConfig;
import com.github.entropyfeng.mydb.server.domain.*;
import com.github.entropyfeng.mydb.server.handler.TurtleServerHandler;
import com.github.entropyfeng.mydb.server.persistence.PersistenceObjectDomain;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.net.InetSocketAddress;
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
    public @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> lazyClear() {
        lazyMethod("clear");
        return ResServerHelper.emptyRes();
    }

    /**
     * 向各个阻塞队列中添加dump命令
     * @return emptyRes
     */
    @Override
    @NotNull
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> lazyDump() {

        lazyMethod("dump");
        return ResServerHelper.emptyRes();
    }

    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> deleteAllDump() {
        PersistenceHelper.deleteDumpFiles();
        return ResServerHelper.emptyRes();
    }

    /**
     * appear only at salve server
     * 从服务器向主服务器发送请求，连接由从服务器主动创建
     *
     * @param host the host of the destination server
     * @param port the port of the destination server
     * @return {@link Pair}
     */
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> slaveOf(String host, Integer port) {


        //从服务器向主服务器请求转储文件
        ClientCommandBuilder clientCommandBuilder = new ClientCommandBuilder(TurtleModel.ADMIN, "slaveRequestDump");

        ClientExecute clientExecute = new ClientExecute(host, port);

        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair = clientExecute.execute(clientCommandBuilder);
        PersistenceObjectDomain domain = PersistenceHelper.dumpAndReLoadFromPair(pair);
        serverDomain.replace(domain);
        //-------向主服务器发送从服务器IP与端口
        ClientCommandBuilder commandBuilder = new ClientCommandBuilder(TurtleModel.ADMIN, "exceptAcceptData");
        clientCommandBuilder.addStringPara(ServerConfig.serverHost);
        clientCommandBuilder.addIntegerPara(ServerConfig.port);
        clientExecute.execute(commandBuilder);
        clientExecute.closeClient();
        return ResServerHelper.emptyRes();
    }


    /**
     * 从服务器向主服务器请求发送转储文件
     *
     * @return 向从服务器返回转储文件
     */
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> slaveRequestDump() {

        dump();
        return PersistenceHelper.transDumpFile();

    }

    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> exceptAcceptData(String host, Integer port) {

        TurtleServerHandler.slaveSet.add(new InetSocketAddress(host, port));

        TurtleServerHandler.registerSlaveServer(host, port);
        return ResServerHelper.emptyRes();
    }

    @NotNull
    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> clear() {

        logger.info("clear begin and all consumer threads will blocking");
        ServerConfig.serverBlocking.set(true);

        try {
            ServerConfig.threadCountDown.await();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        logger.info("all consumer threads is blocked");
        PersistenceHelper.clearAll(serverDomain);

        ServerConfig.threadCountDown = new CountDownLatch(5);
        ServerConfig.serverBlocking.set(false);
        serverDomain.notifyAllValuesThread();

        logger.info("clear en and all consumer thread will resume.");
        return ResServerHelper.emptyRes();

    }

    @Override
    public Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> dump() {

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

        //重新设置countDownLatch
        ServerConfig.threadCountDown = new CountDownLatch(5);
        ServerConfig.serverBlocking.set(false);

        serverDomain.notifyAllValuesThread();


        logger.info("dump end");
        return ResServerHelper.emptyRes();
    }

    /**
     * 向各个阻塞队列{如 values list set hash orderSet}中插入相关命令
     *
     * @param methodName 所有对象都具备的函数名
     */
    private void lazyMethod(String methodName) {

        try {
            Method valuesMethod = ValuesDomain.class.getMethod(methodName);
            Method listMethod = ListDomain.class.getMethod(methodName);
            Method setMethod = SetDomain.class.getMethod(methodName);
            Method hashMethod = HashDomain.class.getMethod(methodName);
            Method orderSetMethod = OrderSetDomain.class.getMethod(methodName);

            //-------------------------------------------------------------------
            serverDomain.valuesQueue.add(new ClientRequest(valuesMethod));
            serverDomain.listQueue.add(new ClientRequest(listMethod));
            serverDomain.setQueue.add(new ClientRequest(setMethod));
            serverDomain.hashQueue.add(new ClientRequest(hashMethod));
            serverDomain.orderSetQueue.add(new ClientRequest(orderSetMethod));

        } catch (NoSuchMethodException e) {
            logger.error("not find method->{}", e.getMessage());
        }

    }

}

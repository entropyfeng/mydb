package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.server.config.ServerStatus;
import com.github.entropyfeng.mydb.server.consumer.*;
import com.github.entropyfeng.mydb.server.core.domain.*;
import com.github.entropyfeng.mydb.server.core.zset.OrderSet;
import com.github.entropyfeng.mydb.server.command.ClientCommand;
import com.github.entropyfeng.mydb.server.command.ClientRequest;
import com.github.entropyfeng.mydb.server.factory.*;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import static com.github.entropyfeng.mydb.server.command.ServerExecute.constructCommand;


/**
 * @author entropyfeng
 */
public class ServerDomain {

    private static final Logger logger = LoggerFactory.getLogger(ServerDomain.class);

    /**
     * 服务器状态
     */
    private volatile ServerStatus serverStatus;

    public static AtomicBoolean interrupted = new AtomicBoolean(false);

    public AtomicBoolean runningFlag=new AtomicBoolean(true);
    public ServerDomain() {

        this.adminObject = new AdminObject(this,runningFlag);

        this.valuesDomain = new ValuesDomain();
        this.listDomain = new ListDomain();
        this.setDomain = new SetDomain();
        this.hashDomain = new HashDomain();
        this.orderSetDomain = new OrderSetDomain();

        constructQueue();

        start();
    }


    public ServerDomain(ValuesDomain valuesDomain, ListDomain listDomain, SetDomain setDomain, HashDomain hashDomain, OrderSetDomain orderSetDomain) {

        this.adminObject = new AdminObject(this,runningFlag);

        this.valuesDomain = valuesDomain;
        this.listDomain = listDomain;
        this.setDomain = setDomain;
        this.hashDomain = hashDomain;
        this.orderSetDomain = orderSetDomain;

        constructQueue();
        start();

    }

    protected AdminObject adminObject;

    protected ValuesDomain valuesDomain;

    protected ListDomain listDomain;

    protected SetDomain setDomain;

    protected HashDomain hashDomain;

    protected OrderSetDomain orderSetDomain;


    protected ConcurrentLinkedQueue<ClientCommand> valuesQueue;

    protected ConcurrentLinkedQueue<ClientCommand> listQueue;

    protected ConcurrentLinkedQueue<ClientCommand> setQueue;

    protected ConcurrentLinkedQueue<ClientCommand> hashQueue;

    protected ConcurrentLinkedQueue<ClientCommand> orderSetQueue;

    protected ConcurrentLinkedQueue<ClientCommand> adminQueue;
    protected Thread valueThread;
    protected Thread listThread;
    protected Thread setThread;
    protected Thread hashThread;
    protected Thread orderSetThread;

    protected Thread adminThread;

    public void start() {

        valueThread = new ValuesThreadFactory().newThread(new ValuesConsumer(runningFlag,valuesDomain,valuesQueue));
        listThread = new ListThreadFactory().newThread(new ListConsumer(runningFlag,listDomain,listQueue));
        setThread = new SetThreadFactory().newThread(new SetConsumer(runningFlag,setDomain,setQueue));
        hashThread = new HashThreadFactory().newThread(new HashConsumer(runningFlag,hashDomain,hashQueue));
        orderSetThread = new OrderSetThreadFactory().newThread(new OrderSetConsumer(runningFlag,orderSetDomain,orderSetQueue));

        adminThread=new AdminThreadFactory().newThread(new AdminConsumer(adminObject,adminQueue));


        valueThread.start();
        listThread.start();
        setThread.start();
        hashThread.start();
        orderSetThread.start();

        adminThread.start();


    }






    private void notifyAllDomain(){
        this.valueThread.notify();
        this.setThread.notify();
        this.hashThread.notify();
        this.listThread.notify();
        this.orderSetThread.notify();
    }


    public void accept(ClientRequest clientRequest, Channel channel) {

        switch (clientRequest.getModel()) {
            case VALUE:
                constructCommand(clientRequest, channel, ValuesDomain.class, valuesQueue);
                return;
            case ADMIN:
                constructCommand(clientRequest,channel, AdminObject.class,adminQueue);
                return;
            case LIST:
                constructCommand(clientRequest, channel, ListDomain.class, listQueue);
                return;
            case SET:
                constructCommand(clientRequest, channel, SetDomain.class, setQueue);
                return;
            case HASH:
                constructCommand(clientRequest, channel, HashDomain.class, hashQueue);
                return;
            case ZSET:
                constructCommand(clientRequest, channel, OrderSet.class, orderSetQueue);
                return;
            default:
                throw new UnsupportedOperationException();
        }
    }



    private void constructQueue() {
        this.valuesQueue = new ConcurrentLinkedQueue<>();
        this.listQueue = new ConcurrentLinkedQueue<>();
        this.setQueue = new ConcurrentLinkedQueue<>();
        this.hashQueue = new ConcurrentLinkedQueue<>();
        this.orderSetQueue = new ConcurrentLinkedQueue<>();

        this.adminQueue = new ConcurrentLinkedQueue<>();
    }
}

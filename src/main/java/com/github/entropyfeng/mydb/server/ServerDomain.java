package com.github.entropyfeng.mydb.server;


import com.github.entropyfeng.mydb.server.command.ClientRequest;
import com.github.entropyfeng.mydb.server.consumer.*;
import com.github.entropyfeng.mydb.server.domain.*;
import com.github.entropyfeng.mydb.server.factory.*;
import com.github.entropyfeng.mydb.server.persistence.PersistenceObjectDomain;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * @author entropyfeng
 */
public class ServerDomain {

    private static final Logger logger = LoggerFactory.getLogger(ServerDomain.class);


    public ServerDomain() {

        this.adminObject = new AdminObject(this);

        this.valuesDomain = new ValuesDomain();
        this.listDomain = new ListDomain();
        this.setDomain = new SetDomain();
        this.hashDomain = new HashDomain();
        this.orderSetDomain = new OrderSetDomain();

        constructQueue();
        start();
    }


    public ServerDomain(PersistenceObjectDomain domain) {
        this.adminObject = new AdminObject(this);

        this.valuesDomain = domain.getValuesDomain();
        this.listDomain = domain.getListDomain();
        this.setDomain = domain.getSetDomain();
        this.hashDomain = domain.getHashDomain();
        this.orderSetDomain = domain.getOrderSetDomain();

        constructQueue();
        start();
    }

    public AdminObject adminObject;

    protected ValuesDomain valuesDomain;

    protected ListDomain listDomain;

    protected SetDomain setDomain;

    protected HashDomain hashDomain;

    protected OrderSetDomain orderSetDomain;


    protected ConcurrentLinkedQueue<ClientRequest> valuesQueue;

    protected ConcurrentLinkedQueue<ClientRequest> listQueue;

    protected ConcurrentLinkedQueue<ClientRequest> setQueue;

    protected ConcurrentLinkedQueue<ClientRequest> hashQueue;

    protected ConcurrentLinkedQueue<ClientRequest> orderSetQueue;

    protected ConcurrentLinkedQueue<ClientRequest> adminQueue;


    protected Thread valueThread;
    protected Thread listThread;
    protected Thread setThread;
    protected Thread hashThread;
    protected Thread orderSetThread;

    protected Thread adminThread;

    public void start() {

        valueThread = new ValuesThreadFactory().newThread(new ValuesConsumer(valuesDomain, valuesQueue));
        listThread = new ListThreadFactory().newThread(new ListConsumer(listDomain, listQueue));
        setThread = new SetThreadFactory().newThread(new SetConsumer(setDomain, setQueue));
        hashThread = new HashThreadFactory().newThread(new HashConsumer(hashDomain, hashQueue));
        orderSetThread = new OrderSetThreadFactory().newThread(new OrderSetConsumer(orderSetDomain, orderSetQueue));

        adminThread = new AdminThreadFactory().newThread(new AdminConsumer(adminObject, adminQueue));


        valueThread.start();
        listThread.start();
        setThread.start();
        hashThread.start();
        orderSetThread.start();

        adminThread.start();


    }

    public void accept(ClientRequest clientRequest) {

        switch (clientRequest.getModel()) {
            case VALUE:
                valuesQueue.add(clientRequest);
                break;
            case ZSET:
                orderSetQueue.add(clientRequest);
                break;
            case LIST:
                listQueue.add(clientRequest);
                break;
            case SET:
                setQueue.add(clientRequest);
                break;
            case HASH:
                hashQueue.add(clientRequest);
                break;
            default:
                adminQueue.add(clientRequest);
                break;
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

    public void notifyAllValuesThread() {
        this.valueThread.notifyAll();
        this.listThread.notifyAll();
        this.setThread.notifyAll();
        this.hashThread.notifyAll();
        this.orderSetThread.notifyAll();
    }

    /**
     * use empty domain to replace the old domains, all blocking queue will remain
     *
     * @param valuesDomain   {@link ValuesDomain}
     * @param listDomain     {@link ListDomain}
     * @param setDomain      {@link SetDomain}
     * @param hashDomain     {@link HashDomain}
     * @param orderSetDomain {@link OrderSetDomain}
     */
    public void replace(@NotNull ValuesDomain valuesDomain,@NotNull ListDomain listDomain, @NotNull SetDomain setDomain, @NotNull HashDomain hashDomain, @NotNull OrderSetDomain orderSetDomain) {
        this.valuesDomain = valuesDomain;
        this.listDomain = listDomain;
        this.setDomain = setDomain;
        this.hashDomain = hashDomain;
        this.orderSetDomain = orderSetDomain;
    }
}

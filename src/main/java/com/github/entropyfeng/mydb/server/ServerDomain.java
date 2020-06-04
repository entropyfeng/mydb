package com.github.entropyfeng.mydb.server;


import com.github.entropyfeng.mydb.server.command.ClientRequest;
import com.github.entropyfeng.mydb.server.consumer.*;
import com.github.entropyfeng.mydb.server.domain.*;
import com.github.entropyfeng.mydb.server.factory.*;
import com.github.entropyfeng.mydb.server.persistence.PersistenceObjectDomain;
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
    }


    public ServerDomain(PersistenceObjectDomain domain) {
        this.adminObject = new AdminObject(this);

        this.valuesDomain = domain.getValuesDomain();
        this.listDomain = domain.getListDomain();
        this.setDomain = domain.getSetDomain();
        this.hashDomain = domain.getHashDomain();
        this.orderSetDomain = domain.getOrderSetDomain();
        constructQueue();
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
                logger.info("add valueQueue {}",clientRequest.getMethod().getName());
                valuesQueue.add(clientRequest);
                logger.info("value queue size ->{}",valuesQueue.size());
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

        synchronized (valueThread) {
            this.valueThread.notifyAll();
        }
        synchronized (listThread) {
            this.listThread.notifyAll();
        }
        synchronized (setThread) {
            this.setThread.notifyAll();
        }

        synchronized (hashThread) {
            this.hashThread.notifyAll();
        }
        synchronized (orderSetThread) {
            this.orderSetThread.notifyAll();
        }


    }


    /**
     * use masterServer's domain to replace the old domains
     * and reconstruct all values blocking queue
     *
     * @param domain {@link PersistenceObjectDomain}
     */
    public void replace(PersistenceObjectDomain domain) {
        this.valuesDomain = domain.getValuesDomain();
        this.listDomain = domain.getListDomain();
        this.setDomain = domain.getSetDomain();
        this.hashDomain = domain.getHashDomain();
        this.orderSetDomain = domain.getOrderSetDomain();

    }

}

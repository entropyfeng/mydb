package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.common.protobuf.SingleResHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author entropyfeng
 */
public class AdminObject {
    private static final Logger logger = LoggerFactory.getLogger(AdminObject.class);

    private ServerDomain serverDomain;

    public AdminObject(ServerDomain serverDomain) {
        this.serverDomain = serverDomain;
    }

    public TurtleProtoBuf.ResponseData dump(){

        logger.info("request dump");
        logger.info("request complete all task in all domains .");
        ServerDomain.interrupted.set(true);
        logger.info("all queue is empty .");
        logger.info("begin dump .");
        PersistenceHelper.dumpAll(serverDomain);
        logger.info("after dump .");
        return SingleResHelper.voidResponse();
    }

}

package com.github.entropyfeng.mydb.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author entropyfeng
 */
public class AdminObject {
    private static final Logger logger = LoggerFactory.getLogger(AdminObject.class);

    private ServerDomain serverDomain;

    public AdminObject(ServerDomain serverDomain) {
        this.serverDomain = serverDomain;
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

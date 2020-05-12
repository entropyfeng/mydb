package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.server.config.Constant;

import java.util.concurrent.CountDownLatch;

/**
 * @author entropyfeng
 */
public class ServerBlockTool {

    private static CountDownLatch countDownLatch=new CountDownLatch(Constant.CONSUMER_THREAD_NUMBER);

    private static void fireBlocking(){

    }
}

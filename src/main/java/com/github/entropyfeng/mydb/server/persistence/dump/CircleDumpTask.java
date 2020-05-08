package com.github.entropyfeng.mydb.server.persistence.dump;

import com.github.entropyfeng.mydb.server.ServerDomain;

/**
 * @author entropyfeng
 */
public class CircleDumpTask implements Runnable {

    private ServerDomain serverDomain;

    public CircleDumpTask(ServerDomain serverDomain) {
        this.serverDomain = serverDomain;
    }

    @Override
    public void run() {
        serverDomain.adminObject.lazyDump();
    }
}

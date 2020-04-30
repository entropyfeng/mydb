package com.github.entropyfeng.mydb.server.config;

/**
 * @author entropyfeng
 * this enum describe the status of the server .
 */
public enum ServerStatus {
    /**
     * the server is closed
     */
    CLOSE,
    /**
     * the server is running normally
     */
    RUNNING,
    /**
     * dump backup file blocking
     */
    DUMP_BLOCKING,

    /**
     * the server is Master-slave synchronization
     * and this server is master node
     */
    MASTER_BLOCKING,

    /**
     * the server is doing master-slave copying
     */
    MASTER_RUNNING,
    /**
     * the server is slave node
     */
    SLAVING


}

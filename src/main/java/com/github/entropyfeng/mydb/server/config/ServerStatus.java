package com.github.entropyfeng.mydb.server.config;

/**
 * @author entropyfeng
 */

public enum ServerStatus {
    /**
     * 关闭
     */
    CLOSE,
    /**
     * 正在运行
     */
    RUNNING,
    /**
     * 阻塞
     */
    BLOCKING,

    /**
     * 正在dump文件
     */
    RUNNING_DUMP

}

package com.github.entropyfeng.mydb.common.ops;

/**
 * @author entropyfeng
 */
public interface AdminOperations {

    /**
     * 阻塞清除所有数据
     */
    void clear();

    void lazyClear();

    void dump();

    void lazyDump();

    /**
     * 删除所有转储文件
     */
    void deleteAllDumps();

    /**
     *
     * @param host 主服务器主机ip
     * @param port 主服务器端口
     */
    void slaveOf(String host,Integer port);

    Boolean closeClient();
}

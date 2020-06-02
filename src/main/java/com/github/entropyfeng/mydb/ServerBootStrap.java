package com.github.entropyfeng.mydb;

import com.github.entropyfeng.mydb.server.TurtleServer;
import com.github.entropyfeng.mydb.server.config.ServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.entropyfeng.mydb.server.util.ServerUtil.createDumpFolder;

/**
 * @author entropyfeng
 * @date 2019/12/27 13:34
 */
public class ServerBootStrap {

    private static final Logger logger = LoggerFactory.getLogger(ServerBootStrap.class);

    public static void main(String[] args) {

        try {
            if (!createDumpFolder()) {
                logger.error("create dump directory error !");
            }
            String host = ServerConfig.serverHost;
            Integer port = ServerConfig.port;
            new TurtleServer(host, port).start();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}

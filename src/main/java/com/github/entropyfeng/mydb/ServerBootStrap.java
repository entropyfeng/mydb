package com.github.entropyfeng.mydb;

import com.github.entropyfeng.mydb.server.config.ServerConfig;
import com.github.entropyfeng.mydb.server.TurtleServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author entropyfeng
 * @date 2019/12/27 13:34
 */
public class ServerBootStrap {

    private static final Logger logger=LoggerFactory.getLogger(ServerBootStrap.class);

    /**
     *  create dumpFile directory.
     * @return true->the directory already exist or create the new directory in current.
     * false->the directory not exist in previous,and create new directory error this time.
     */
    private static boolean createDumpFolder(){
        File file = new File(ServerConfig.dumpPath);
        if (!file.exists()) {
            return file.mkdir();
        }
        return true;
    }

    public static void main(String[] args) throws Exception {

        if (!createDumpFolder()){
            logger.error("create dump directory error !");
        }
        String host = ServerConfig.serverHost;
        Integer port= ServerConfig.port;
        new TurtleServer(host, port).start();
    }
}

package com.github.entropyfeng.mydb.server.config;

import com.github.entropyfeng.mydb.util.ConfigUtil;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author entropyfeng
 * @date 2020/3/5 14:25
 */
public class ServerConfig {
    private static volatile Properties properties;


    /**
     * 双重检查锁单例
     * singleton module
     * the properties is correct strictly
     * @return {@link Properties}
     */
    public static Properties getProperties() {
        if (properties==null){
            synchronized (ServerConfig.class){
                if(properties==null){
                    properties=ConfigUtil.readProperties(Constant.CONFIG_FILE_NAME);
                    ConfigUtil.checkProperties(properties);
                }
            }
        }
        return properties;
    }

    public static String serverHost=getProperties().getProperty(Constant.HOST);

    public static Integer port=ConfigUtil.getIntegerProperty(getProperties(),Constant.PORT);

    public static String dumpPath=getProperties().getProperty(Constant.BACK_UP_PATH_NAME);

    public static Integer precision= ConfigUtil.getIntegerProperty(ServerConfig.getProperties(),Constant.SYSTEM_CLOCK_REFRESH);

    public static ServerStatus serverStatus=ServerStatus.CLOSE;

    public static AtomicBoolean serverBlocking=new AtomicBoolean(false);

    public static AtomicBoolean masterSlaveFlag=new AtomicBoolean(false);

    public static AtomicInteger blockingDomainNumber=new AtomicInteger(0);
}

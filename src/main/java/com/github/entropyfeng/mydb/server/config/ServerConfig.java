package com.github.entropyfeng.mydb.server.config;

import com.github.entropyfeng.mydb.common.util.CommonUtil;
import com.github.entropyfeng.mydb.server.util.ServerUtil;

import java.util.Properties;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

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
                    properties= CommonUtil.readProperties(ServerConstant.CONFIG_FILE_NAME);
                    ServerUtil.checkProperties(properties);
                }
            }
        }
        return properties;
    }

    public static String serverHost=getProperties().getProperty(ServerConstant.HOST);

    public static Integer port= CommonUtil.getIntegerProperty(getProperties(), ServerConstant.PORT);

    public static String dumpPath=getProperties().getProperty(ServerConstant.BACK_UP_PATH_NAME);

    public static Integer precision= CommonUtil.getIntegerProperty(ServerConfig.getProperties(), ServerConstant.SYSTEM_CLOCK_REFRESH);

    public static Integer dumpCircle= CommonUtil.getIntegerProperty(ServerConfig.getProperties(), ServerConstant.SYSTEM_DUMP_CIRCLE);

    public static AtomicBoolean serverBlocking=new AtomicBoolean(false);

    public static  AtomicBoolean masterSlaveFlag=new AtomicBoolean(false);


    public static volatile CountDownLatch threadCountDown=new CountDownLatch(ServerConstant.CONSUMER_THREAD_NUMBER);
}

package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.util.CommonUtil;
import com.github.entropyfeng.mydb.server.config.ServerConfig;
import com.github.entropyfeng.mydb.server.util.ServerUtil;

import java.util.Properties;

/**
 * @author entropyfeng
 */
public class ClientConfig {
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
                    properties= CommonUtil.readProperties(ClientConstant.CONFIG_FILE_NAME);
                   ClientUtil.checkProperties(properties);
                }
            }
        }
        return properties;
    }

    public static String desHost=getProperties().getProperty(ClientConstant.DESTINATION_HOST_NAME);

    public static Integer desPort=CommonUtil.getIntegerProperty(getProperties(),ClientConstant.DESTINATION_PORT_NAME);
}

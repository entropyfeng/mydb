package com.github.entropyfeng.mydb.config;

import com.github.entropyfeng.mydb.util.CommonUtil;
import com.github.entropyfeng.mydb.util.ConfigUtil;

import java.util.Properties;

/**
 * @author entropyfeng
 * @date 2020/3/5 14:25
 */
public class CommonConfig {
    private static volatile Properties properties;

    /**
     * 双重检查锁单例
     * singleton module
     * the properties is correct strictly
     * @return {@link Properties}
     */
    public static Properties getProperties() {
        if (properties==null){
            synchronized (CommonConfig.class){
                if(properties==null){
                    properties=ConfigUtil.readProperties(Constant.CONFIG_FILE_NAME);
                    ConfigUtil.checkProperties(properties);
                }
            }
        }
        return properties;
    }
}

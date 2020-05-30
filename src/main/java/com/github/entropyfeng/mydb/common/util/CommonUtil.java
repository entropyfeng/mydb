package com.github.entropyfeng.mydb.common.util;

import com.github.entropyfeng.mydb.server.util.ServerUtil;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * @author entropyfeng
 * @date 2020/3/4 14:42
 */
public class CommonUtil {
    /**
     * 从配置文件读取配置信息
     *
     * @param filename 文件名
     * @return {@link Properties}
     */
    @NotNull
    public static Properties readProperties(String filename) {
        Properties properties = new Properties();
        InputStream inputStream = ServerUtil.class.getClassLoader().getResourceAsStream(filename);
        if (inputStream!=null){
            try {
                properties.load(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
            } catch (IOException e) {
                properties = new Properties();
            }
        }
        return properties;
    }

    /**
     * 从配置文件中读取整型信息
     * @param properties {@link Properties}
     * @param paraName 参数名
     * @return 参数所对应的值
     */
    public static Integer getIntegerProperty(Properties properties,String paraName){
        return new Integer(properties.getProperty(paraName));
    }
}

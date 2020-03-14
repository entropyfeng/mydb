package com.github.entropyfeng.mydb.util;

import com.github.entropyfeng.mydb.config.Constant;
import io.netty.util.internal.StringUtil;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author entropyfeng
 * @date 2020/2/23 9:17
 */
public class ConfigUtil {


    /**
     * 从配置文件读取配置信息
     *
     * @param filename 文件名
     * @return {@link Properties}
     */
    public static Properties readProperties(String filename) {
        Properties properties = new Properties();
        InputStream inputStream = ConfigUtil.class.getClassLoader().getResourceAsStream(filename);
        try {
            properties.load(new InputStreamReader(inputStream, "UTF-8"));
        } catch (IOException e) {
            properties = null;
        }
        return properties;
    }


    /**
     * 检查 配置文件参数是否合法
     * @param properties {@link Properties}
     * @throws IllegalArgumentException 参数异常
     */
    public static void checkProperties(Properties properties) throws Error {

        boolean res=true;
        StringBuilder stringBuilder=new StringBuilder();

        if (properties == null) {
            properties = new Properties();
        }
        //username
        String username=properties.getProperty(Constant.USER_NAME);
        if (StringUtil.isNullOrEmpty(username)) {
           res=false;
           stringBuilder.append("lack param "+Constant.USER_NAME+"\n");
        }

        //password
        String password=properties.getProperty(Constant.PASSWORD);
        if (StringUtil.isNullOrEmpty(password)) {
            res=false;
            stringBuilder.append("lack param "+Constant.PASSWORD+"\n");
        }

        //port
        String port = properties.getProperty(Constant.PORT);
        if (StringUtil.isNullOrEmpty(port)) {
            res=false;
            stringBuilder.append("lack param "+Constant.PORT);
        } else if (!NetUtil.checkPort(port)) {
            res=false;
            stringBuilder.append("illegal port in config file .\n");
        }

        //host
        String host=properties.getProperty(Constant.HOST);
        if(StringUtil.isNullOrEmpty(host)){
            res=false;
            stringBuilder.append("lack param "+Constant.HOST);
        }else if(!NetUtil.checkHost(host)){
            res=false;
            stringBuilder.append("illegal host in config file .\n");
        }

        if(!res){
            throw new Error(stringBuilder.toString());
        }

    }
    public static long getIntegerProperty(Properties properties,String paraName){
        return new Long(properties.getProperty(paraName));
    }
}

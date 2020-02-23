package com.github.entropyfeng.mydb.util;

import com.github.entropyfeng.mydb.constant.Constant;
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
     * 检查 配置文件参数是否合法,并填充默认值
     * @param properties {@link Properties}
     * @throws IllegalArgumentException 参数异常
     */
    public static void checkProperties(Properties properties) throws IllegalArgumentException {

        boolean res=true;
        StringBuilder stringBuilder=new StringBuilder();

        if (properties == null) {
            properties = new Properties();
        }
        //username
        String username=properties.getProperty(Constant.USER_NAME);
        if (StringUtil.isNullOrEmpty(username)) {
            properties.setProperty(Constant.USER_NAME, "root");
        }else {
            properties.setProperty(Constant.USER_NAME,username);
        }

        //password
        String password=properties.getProperty(Constant.PASSWORD);
        if (StringUtil.isNullOrEmpty(password)) {
            properties.setProperty(Constant.PASSWORD, "root");
        }else {
            properties.setProperty(Constant.PASSWORD,password);
        }

        //port
        String port = properties.getProperty(Constant.PORT);
        if (StringUtil.isNullOrEmpty(port)) {
            properties.setProperty(Constant.PORT, "4407");
        } else if (NetUtil.checkPort(port)) {
            properties.setProperty(Constant.PORT, port);
        } else {
            res=false;
            stringBuilder.append("illegal port in config file .\n");
        }

        //host
        String host=properties.getProperty(Constant.HOST);
        if(StringUtil.isNullOrEmpty(host)){
            properties.setProperty(Constant.HOST,"127.0.0.1");
        }else if(NetUtil.checkHost(host)){
            properties.setProperty(Constant.HOST,host);
        }else {
            res=false;
            stringBuilder.append("illegal host in config file .\n");
        }

        if(!res){
            throw new IllegalArgumentException(stringBuilder.toString());
        }

    }
}

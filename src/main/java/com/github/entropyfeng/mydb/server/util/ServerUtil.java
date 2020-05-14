package com.github.entropyfeng.mydb.server.util;

import com.github.entropyfeng.mydb.server.config.ServerConstant;
import com.github.entropyfeng.mydb.common.util.NetUtil;
import io.netty.util.internal.StringUtil;
import java.util.Properties;

/**
 * @author entropyfeng
 * @date 2020/2/23 9:17
 */
public class ServerUtil {





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
        String username=properties.getProperty(ServerConstant.USER_NAME);
        if (StringUtil.isNullOrEmpty(username)) {
           res=false;
           stringBuilder.append("lack param "+ ServerConstant.USER_NAME+"\n");
        }

        //password
        String password=properties.getProperty(ServerConstant.PASSWORD);
        if (StringUtil.isNullOrEmpty(password)) {
            res=false;
            stringBuilder.append("lack param "+ ServerConstant.PASSWORD+"\n");
        }

        //port
        String port = properties.getProperty(ServerConstant.PORT);
        if (StringUtil.isNullOrEmpty(port)) {
            res=false;
            stringBuilder.append("lack param "+ ServerConstant.PORT);
        } else if (!NetUtil.isLegalPort(port)) {
            res=false;
            stringBuilder.append("illegal port in config file .\n");
        }

        //host
        String host=properties.getProperty(ServerConstant.HOST);
        if(StringUtil.isNullOrEmpty(host)){
            res=false;
            stringBuilder.append("lack param "+ ServerConstant.HOST);
        }else if(!NetUtil.isLegalHost(host)){
            res=false;
            stringBuilder.append("illegal host in config file .\n");
        }

        //backupPath
        String path=properties.getProperty(ServerConstant.BACK_UP_PATH_NAME);
        if (StringUtil.isNullOrEmpty(path)){
            res=false;
            stringBuilder.append("backUpPath is illegal .\n");
        }
        if(!res){
            throw new Error(stringBuilder.toString());
        }

    }



}

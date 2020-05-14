package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.util.NetUtil;
import io.netty.util.internal.StringUtil;

import java.util.Properties;

/**
 * @author entropyfeng
 */
public class ClientUtil {

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
        
        //port
        String port = properties.getProperty(ClientConstant.DESTINATION_PORT_NAME);
        if (StringUtil.isNullOrEmpty(port)) {
            res=false;
            stringBuilder.append("lack param "+ ClientConstant.DESTINATION_PORT_NAME);
        } else if (!NetUtil.isLegalPort(port)) {
            res=false;
            stringBuilder.append("illegal port in config file .\n");
        }

        //host
        String host=properties.getProperty(ClientConstant.DESTINATION_HOST_NAME);
        if(StringUtil.isNullOrEmpty(host)){
            res=false;
            stringBuilder.append("lack param "+ ClientConstant.DESTINATION_HOST_NAME);
        }else if(!NetUtil.isLegalHost(host)){
            res=false;
            stringBuilder.append("illegal host in config file .\n");
        }
        
        
        if(!res){
            throw new Error(stringBuilder.toString());
        }

    }

}

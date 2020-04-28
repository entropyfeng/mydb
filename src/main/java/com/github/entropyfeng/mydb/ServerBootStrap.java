package com.github.entropyfeng.mydb;

import com.github.entropyfeng.mydb.config.CommonConfig;
import com.github.entropyfeng.mydb.config.Constant;
import com.github.entropyfeng.mydb.server.TurtleServer;
import com.github.entropyfeng.mydb.util.CommonUtil;
import com.github.entropyfeng.mydb.util.ConfigUtil;

/**
 * @author entropyfeng
 * @date 2019/12/27 13:34
 */
public class ServerBootStrap {


    public static void main(String[] args) throws Exception {
        String host = (String) CommonConfig.getProperties().get(Constant.HOST);
        Integer port= ConfigUtil.getIntegerProperty(CommonConfig.getProperties(),Constant.PORT);
        new TurtleServer(host, port).start();
    }
}

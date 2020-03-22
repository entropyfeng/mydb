package com.github.entropyfeng.mydb;

import com.github.entropyfeng.mydb.core.dict.ElasticMap;
import com.github.entropyfeng.mydb.net.TurtleServer;


import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;

/**
 * @author entropyfeng
 * @date 2019/12/27 13:34
 */
public class ServerBootStrap {



    public static void main(String[] args)throws Exception {

        new TurtleServer("0.0.0.0",4407).start();


    }
}

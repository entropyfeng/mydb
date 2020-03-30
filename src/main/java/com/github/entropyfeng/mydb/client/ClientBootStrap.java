package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.config.CommonConfig;
import com.github.entropyfeng.mydb.server.TurtleServer;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author entropyfeng
 */
public class ClientBootStrap {


    public static void main(String[] args) throws Exception{
        TurtleClient client= new TurtleClient("0.0.0.0",4407);
        client.start();
    }
}

package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.config.CommonConfig;

import java.util.HashMap;
import java.util.Objects;

/**
 * @author entropyfeng
 */
public class ClientBootStrap {


    public static void main(String[] args) throws Exception{

    new TurtleClient("0.0.0.0",4407).start();


    }
}

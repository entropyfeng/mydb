package com.github.entropyfeng.mydb.client;


/**
 * @author entropyfeng
 */
public class ClientBootStrap {

    public static void main(String[] args) throws Exception{
        TurtleClient client= new TurtleClient("0.0.0.0",4407);
        client.start();
    }
}

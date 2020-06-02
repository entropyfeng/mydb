package com.github.entropyfeng.mydb.client.asy;

import com.github.entropyfeng.mydb.client.conn.ClientExecute;

/**
 * 异步API
 * @author entropyfeng
 */
public class AsyTemplate {


    private AsyClientExecute clientExecute;
    public AsyTemplate(String host,Integer port){
        clientExecute=new AsyClientExecute(host, port);
    }



}

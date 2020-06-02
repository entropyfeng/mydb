package com.github.entropyfeng.mydb.client.asy;

import com.github.entropyfeng.mydb.client.asy.res.*;
import com.github.entropyfeng.mydb.client.conn.ClientExecute;

/**
 * 异步API
 * @author entropyfeng
 */
public class AsyTemplate {


    private AsyClientExecute clientExecute;

    public AsyTemplate(String host,Integer port){
        clientExecute=new AsyClientExecute(host, port);
        valueOperations=new AsyValueOperations(clientExecute);
        listOperations=new AsyListOperations(clientExecute);
        setOperations=new AsySetOperations(clientExecute);
        hashOperations=new AsyHashOperations(clientExecute);
        orderSetOperations=new AsyOrderSetOperations(clientExecute);
    }

    private AsyValueOperations valueOperations;
    private AsyListOperations listOperations;
    private AsySetOperations setOperations;
    private AsyHashOperations hashOperations;
    private AsyOrderSetOperations orderSetOperations;
    public AsyValueOperations opsForValues(){
        return valueOperations;
    }
    public AsyListOperations opsForList(){
        return listOperations;
    }
    public AsySetOperations opsForSet(){
        return setOperations;
    }
    public  AsyHashOperations opsForHash(){
        return hashOperations;
    }
    public AsyOrderSetOperations opsForOrderSet(){
        return orderSetOperations;
    }


}

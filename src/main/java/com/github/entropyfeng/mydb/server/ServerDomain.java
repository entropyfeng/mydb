package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.core.obj.ValuesObject;

import java.util.HashMap;


/**
 * @author entropyfeng
 */
public class ServerDomain {

    private ValuesObject valuesObject;

    public ServerDomain(){
        valuesObject=new ValuesObject(new HashMap<>());
    }
    public void invoke(String operationName){
       // valuesObject.getClass().getDeclaredMethod(operationName);
    }

}

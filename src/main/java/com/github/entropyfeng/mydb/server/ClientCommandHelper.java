package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;

import java.lang.reflect.Method;

/**
 * @author enntropyfeng
 */
public class ClientCommandHelper {

    public void xx(TurtleProtoBuf.ClientCommand clientCommand){

       switch (clientCommand.getModel()){
           case COMMON:parseForCommon(clientCommand); break;
           case ADMIN:parseForAdmin();break;
           case CONCRETE:parseForConcrete();break;
           default:throw  new UnsupportedOperationException();
       }
    }

    private void parseForCommon(TurtleProtoBuf.ClientCommand clientCommand){
        final String operationName=clientCommand.getOperationName();

    }

    private void parseForAdmin(){

    }
    private void parseForConcrete(){

    }
}

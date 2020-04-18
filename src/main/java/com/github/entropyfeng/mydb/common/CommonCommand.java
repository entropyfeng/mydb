package com.github.entropyfeng.mydb.common;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.obj.TurtleValue;

/**
 * @author entropyfeng
 */
public final class CommonCommand {


    public static TurtleProtoBuf.ClientCommand helloServerCommand() {
        return TurtleProtoBuf.ClientCommand.newBuilder().setOperationName(CommonConstant.HELLO_SERVER).build();
    }

    public static TurtleProtoBuf.ClientCommand helloClientCommand() {
        return TurtleProtoBuf.ClientCommand.newBuilder().setOperationName(CommonConstant.HELLO_CLIENT).build();
    }

    public static TurtleProtoBuf.ClientCommand emptyCommand(){

        return TurtleProtoBuf.ClientCommand.newBuilder().build();
    }

    public static TurtleProtoBuf.ClientCommand sayHelloCommand() {

        ClientCommandBuilder builder=new ClientCommandBuilder(TurtleModel.VALUE,"sayHello");
        return builder.build();
    }
    public static TurtleProtoBuf.ClientCommand insertValue(){
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "set");
        builder.addPara(TurtleParaType.STRING, "hello");
        TurtleValue turtleValue=new TurtleValue("10086a");
        builder.addPara(TurtleParaType.TURTLE_VALUE, turtleValue);
        builder.addPara(TurtleParaType.LONG, 0L);
        return builder.build();
    }

}

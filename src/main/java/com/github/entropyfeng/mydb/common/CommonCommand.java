package com.github.entropyfeng.mydb.common;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;

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


}

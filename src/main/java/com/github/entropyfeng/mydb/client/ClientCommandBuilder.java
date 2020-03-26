package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.util.CommonUtil;
import com.google.protobuf.CodedInputStream;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * @author etropyfeng
 */
public class ClientCommandBuilder {
    private TurtleProtoBuf.ClientCommand.Builder builder = TurtleProtoBuf.ClientCommand.newBuilder();

    public ClientCommandBuilder(TurtleProtoBuf.TurtleModel turtleModel, TurtleProtoBuf.TurtleObject turtleObject, String operationName) {

        builder.setModel(turtleModel);
        builder.setObj(turtleObject);
        builder.setOperationName(operationName);
    }

    public ClientCommandBuilder addPara(TurtleProtoBuf.TurtleParaType paraType, TurtleProtoBuf.TurtleCommonValue value) {
        builder.addParas(TurtleProtoBuf.TurtlePara.newBuilder().setKey(paraType).setValue(value).build());
        return this;
    }

    public TurtleProtoBuf.ClientCommand build() {
        return builder.build();
    }

}
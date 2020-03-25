package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.config.SupportModel;
import com.github.entropyfeng.mydb.config.SupportObject;
import com.github.entropyfeng.mydb.config.SupportPara;


import java.io.IOException;
import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author entropyfeng
 */
public class ClientCommand implements Serializable {

    /**
     * 1字节
     */
    public final SupportModel supportModel;
    /**
     * 1字节
     */
    public final SupportObject supportObject;

    /**
     * 占1字节
     */
    public byte operationParaNumber;

    /**
     * 2*operationName 字节
     */
    public final String operationName;

    /**
     * 占operationParaNumber*1 byte
     */
    private final List<Byte> parasTypeList;

    private final List<Object> parasList;



    public ClientCommand(SupportModel supportModel, SupportObject supportObject, String operationName) {
        this.supportModel = supportModel;
        this.supportObject = supportObject;
        this.operationName = operationName;
        parasList = new ArrayList<>();
        parasTypeList = new ArrayList<>();
    }

    public void addPara(SupportPara supportPara, Object value) {
        this.parasTypeList.add(supportPara.toType());
        this.parasList.add(value);
        operationParaNumber++;
    }


}

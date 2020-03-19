package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.config.SupportModel;
import com.github.entropyfeng.mydb.config.SupportObject;
import com.github.entropyfeng.mydb.config.SupportPara;
import com.github.entropyfeng.mydb.core.obj.ValueObject;
import com.github.entropyfeng.mydb.core.obj.ValuesObject;

import java.io.Serializable;
import java.nio.ByteBuffer;
import java.util.List;

/**
 * @author entropyfeng
 */
public class ClientCommand implements Serializable {
    private long timeStamp;

    /**
     * 1字节
     */
    private SupportModel supportModel;
    /**
     * 1字节
     */
    private SupportObject supportObject;
    /**
     * 2*operationName 字节
     */
    private String operationName;
    /**
     * 占4字节
     */
    private int operationParaNumber;
    /**
     * 占operationParaNumber*1 byte
     */
    private List<SupportPara> parasTypeList;

    private List<Object> parasList;

    ClientCommand(SupportModel supportModel,SupportObject supportObject,String operationName){
        this.supportModel=supportModel;
        this.supportObject=supportObject;
        this.operationName=operationName;
    }
    void addPara(SupportPara supportPara){

    }

    void xx(SupportPara paraType,Object para){

        long length=0;


        switch (paraType){
            case LONG:length+=8;break;
            case INTEGER:length+=4;break;
            case DOUBLE:length+=8;break;
            case STRING:length+=((String)para).length();break;
            case NUMBER:length+=((String)para).length();break;
            case VALUE_OBJECT:((ValueObject)para).toByte().length;break;

        }
    }

}

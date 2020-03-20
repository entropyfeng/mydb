package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.config.SupportModel;
import com.github.entropyfeng.mydb.config.SupportObject;
import com.github.entropyfeng.mydb.config.SupportPara;
import com.github.entropyfeng.mydb.core.obj.TurtleObject;

import java.io.Serializable;
import java.util.ArrayList;
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

    ClientCommand(SupportModel supportModel, SupportObject supportObject, String operationName) {
        this.supportModel = supportModel;
        this.supportObject = supportObject;
        this.operationName = operationName;
        parasList = new ArrayList<>();
        parasTypeList = new ArrayList<>();
    }

    void addPara(SupportPara supportPara, Object value) {
        this.parasTypeList.add(supportPara);
        this.parasList.add(value);
        operationParaNumber++;
    }


    void xx(SupportPara paraType, Object para) {
        long length = 0;

        switch (paraType) {
            case INTEGER:
                length += 4;
                break;
            case LONG:
            case DOUBLE:
                length += 8;
                break;
            case STRING:
            case NUMBER:
                length += ((String) para).length();
                break;
            case VALUE_OBJECT:
                length += ((TurtleObject) para).toByte().length;
                break;
            default:
                throw new IllegalArgumentException("unSupport paraType" + para.toString());

        }
    }

}

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

    public long dataLength;
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

    public byte[] toBytes(){

        return null;
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
            case NUMBER_DECIMAL:
            case NUMBER_INTEGER:
                length += ((String) para).length()*2;
                break;
            case TURTLE_OBJECT:
                length += ((TurtleObject) para).toByte().length*2;
                break;
            default:
                throw new IllegalArgumentException("unSupport paraType" + para.toString());

        }
    }

}

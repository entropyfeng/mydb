package com.github.entropyfeng.mydb.server;

import java.util.ArrayList;
import java.util.List;

/**
 * @author entropyfeng
 */
public class ValuesCommand implements IClientCommand{

    private String operationName;
    private Class<?>[] paraTypes;
    private List<Object> paras;

    public ValuesCommand(String operationName, Class<?>[] paraTypes, List<Object> paras) {
        this.operationName = operationName;
        this.paraTypes = paraTypes;
        this.paras = paras;
    }

    @Override
    public String getOperationName() {
        return operationName;
    }

    public Class<?>[] getParaTypes() {
        return paraTypes;
    }

    public List<Object> getParas() {
        return paras;
    }
}
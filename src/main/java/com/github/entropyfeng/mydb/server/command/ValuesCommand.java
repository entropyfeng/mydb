package com.github.entropyfeng.mydb.server.command;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author entropyfeng
 */
public class ValuesCommand implements ConcreteCommand {

    private String operationName;
    private Class<?>[] paraTypes;
    private List<Object> paras;
    private Method method;
    public ValuesCommand(){

    }

    public ValuesCommand(String operationName, Class<?>[] paraTypes, List<Object> paras) {
        this.operationName = operationName;
        this.paraTypes = paraTypes;
        this.paras = paras;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
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

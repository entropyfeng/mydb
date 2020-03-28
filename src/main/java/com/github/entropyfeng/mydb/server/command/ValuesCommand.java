package com.github.entropyfeng.mydb.server.command;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author entropyfeng
 */
public class ValuesCommand  {


    private final Method method;
    private final List<Object> values;

    public ValuesCommand(Method method, List<Object> values) {
        this.method = method;
        this.values = values;
    }

    public Method getMethod() {
        return method;
    }

    public List<Object> getValues() {
        return values;
    }
}

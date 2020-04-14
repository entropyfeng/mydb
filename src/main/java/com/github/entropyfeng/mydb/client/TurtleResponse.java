package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public class TurtleResponse {
    //若为空则未出现异常

    public TurtleProtoBuf.ExceptionType exceptionType;
    public String exception;

    public Object res;
    public Collection<Object> collectionRes;

}

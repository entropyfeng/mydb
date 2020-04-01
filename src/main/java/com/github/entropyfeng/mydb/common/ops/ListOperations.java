package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.core.obj.TurtleValue;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

/**
 * @author entropyfeng
 */
public interface ListOperations {

    public Integer size();


    public Integer sizeOf(String string);

    public Integer leftPush(String string, TurtleValue value);

    public default Integer leftPushAll(String string,TurtleValue ...values){
        return leftPushAll(string, Arrays.asList(values));
    }

    public Integer leftPushAll(String string, Collection<TurtleValue> values);



}

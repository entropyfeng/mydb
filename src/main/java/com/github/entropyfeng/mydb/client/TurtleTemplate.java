package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.ops.ValueOperations;

/**
 * @author entropyfeng
 */
public class TurtleTemplate {

    public TurtleTemplate() {

        valueOperations=new DefaultValueOperations();
    }


    private ValueOperations valueOperations;

    public ValueOperations opsForValues(){
        return this.valueOperations;
    }

}

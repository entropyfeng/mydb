package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.ops.ListOperations;
import com.github.entropyfeng.mydb.common.ops.ValueOperations;

/**
 * @author entropyfeng
 */
public class TurtleTemplate {

    public TurtleTemplate() {

        valueOperations=new DefaultValueOperations();
        listOperations=new DefaultListOperations();
    }


    private ValueOperations valueOperations;

    private ListOperations listOperations;
    public ValueOperations opsForValues(){
        return this.valueOperations;
    }
    public ListOperations opsForList(){
        return this.listOperations;
    }

}

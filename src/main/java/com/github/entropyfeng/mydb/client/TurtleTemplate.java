package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.ops.IValueOperations;
import com.github.entropyfeng.mydb.common.ops.ValueOperations;

/**
 * @author entropyfeng
 */
public class TurtleTemplate  {


    public TurtleTemplate() {
        this.valueOperations = new DefaultValueOperations();
    }

    private IValueOperations valueOperations;


    public IValueOperations opsForValues(){
        return this.valueOperations;
    }


}

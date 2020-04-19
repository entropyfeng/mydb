package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.ops.ValueOperations;

/**
 * @author entropyfeng
 */
public class TurtleTemplate {

    public TurtleTemplate() {
        template=new ResponseDataTemplate();
        valueOperations=new DefaultValueOperations();
    }

    private ResponseDataTemplate template;

    private ValueOperations valueOperations;


}

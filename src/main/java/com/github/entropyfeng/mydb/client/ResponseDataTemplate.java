package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.ops.IListOperations;
import com.github.entropyfeng.mydb.common.ops.IValueOperations;
import com.github.entropyfeng.mydb.common.ops.ValueOperations;

/**
 * @author entropyfeng
 */
public class ResponseDataTemplate {


    public ResponseDataTemplate() {
        this.valueOperations = new DefaultValueOperations();
        this.listOperations=new DefaultListOperations();
    }

    private IValueOperations valueOperations;

    private IListOperations listOperations;


    public IValueOperations opsForValues(){
        return this.valueOperations;
    }

    public IListOperations opsForList(){
        return this.listOperations;
    }

}

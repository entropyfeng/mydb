package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.ops.IListOperations;
import com.github.entropyfeng.mydb.common.ops.ISetOperations;
import com.github.entropyfeng.mydb.common.ops.IValueOperations;

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

    private ISetOperations setOperations;

    public IValueOperations opsForValues(){
        return this.valueOperations;
    }

    public IListOperations opsForList(){
        return this.listOperations;
    }

    public ISetOperations opsForSet(){
        return this.setOperations;
    }

}

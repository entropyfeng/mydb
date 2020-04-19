package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.client.ops.ResponseListOperations;
import com.github.entropyfeng.mydb.client.ops.ResponseSetOperations;
import com.github.entropyfeng.mydb.client.ops.ResponseValueOperations;
import com.github.entropyfeng.mydb.common.ops.IListOperations;
import com.github.entropyfeng.mydb.common.ops.ISetOperations;
import com.github.entropyfeng.mydb.common.ops.IValueOperations;

/**
 * @author entropyfeng
 */
public class ResponseDataTemplate {


    public ResponseDataTemplate() {
        this.valueOperations = new ResponseValueOperations();
        this.listOperations=new ResponseListOperations();
        this.setOperations=new ResponseSetOperations();

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

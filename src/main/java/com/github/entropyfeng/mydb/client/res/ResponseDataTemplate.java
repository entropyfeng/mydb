package com.github.entropyfeng.mydb.client.res;

import com.github.entropyfeng.mydb.client.res.*;
import com.github.entropyfeng.mydb.common.ops.*;

/**
 * @author entropyfeng
 */
public class ResponseDataTemplate {


    public ResponseDataTemplate() {
        this.valueOperations = new ResponseValueOperations();
        this.listOperations = new ResponseListOperations();
        this.setOperations = new ResponseSetOperations();
        this.hashOperations = new ResponseHashOperations();
        this.orderSetOperations=new ResponseOrderSetOperations();
    }

    private IValueOperations valueOperations;

    private IListOperations listOperations;

    private ISetOperations setOperations;

    private IHashOperations hashOperations;

    private IOrderSetOperations orderSetOperations;

    public IValueOperations opsForValues() {
        return this.valueOperations;
    }

    public IListOperations opsForList() {
        return this.listOperations;
    }

    public ISetOperations opsForSet() {
        return this.setOperations;
    }

    public IHashOperations opsForHash() {
        return this.hashOperations;
    }

    public IOrderSetOperations opsForOrderSet(){
        return this.orderSetOperations;
    }
}

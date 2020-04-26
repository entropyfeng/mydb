package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.ops.*;

/**
 * @author entropyfeng
 */
public class TurtleTemplate {

    public TurtleTemplate() {

        valueOperations = new DefaultValueOperations();
        listOperations = new DefaultListOperations();
        setOperations = new DefaultSetOperations();
        orderSetOperations = new DefaultOrderSetOperations();
        hashOperations = new DefaultHashOperations();
    }


    private ValueOperations valueOperations;

    private ListOperations listOperations;

    private SetOperations setOperations;

    private HashOperations hashOperations;

    private OrderSetOperations orderSetOperations;

    public ValueOperations opsForValues() {
        return this.valueOperations;
    }

    public ListOperations opsForList() {
        return this.listOperations;
    }

    public HashOperations opsForHash() {
        return this.hashOperations;
    }

    public SetOperations opsForSet() {
        return this.setOperations;
    }

    public OrderSetOperations opsForOrderSet() {
        return this.orderSetOperations;
    }

}

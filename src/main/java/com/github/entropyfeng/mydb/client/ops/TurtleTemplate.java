package com.github.entropyfeng.mydb.client.ops;

import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.common.ops.*;

/**
 * @author entropyfeng
 */
public class TurtleTemplate {

    public TurtleTemplate(String host,Integer port) {

        ClientExecute clientExecute=new ClientExecute(host, port);
        valueOperations = new DefaultValueOperations(clientExecute);
        listOperations = new DefaultListOperations(clientExecute);
        setOperations = new DefaultSetOperations(clientExecute);
        orderSetOperations = new DefaultOrderSetOperations(clientExecute);
        hashOperations = new DefaultHashOperations(clientExecute);
        adminOperations = new DefaultAdminOperations(clientExecute);
    }


    private ValueOperations valueOperations;

    private ListOperations listOperations;

    private SetOperations setOperations;

    private HashOperations hashOperations;

    private OrderSetOperations orderSetOperations;

    private AdminOperations adminOperations;

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

    public AdminOperations opsForAdmin() {
        return this.adminOperations;
    }

}

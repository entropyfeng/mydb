package com.github.entropyfeng.mydb.server.persistence;

import com.github.entropyfeng.mydb.server.domain.*;
import org.jetbrains.annotations.NotNull;

/**
 * @author entropyfeng
 */
public class PersistenceObjectDomain {

    public PersistenceObjectDomain(@NotNull ValuesDomain valuesDomain, @NotNull ListDomain listDomain, @NotNull SetDomain setDomain,@NotNull HashDomain hashDomain,@NotNull OrderSetDomain orderSetDomain) {
        this.valuesDomain = valuesDomain;
        this.listDomain = listDomain;
        this.setDomain = setDomain;
        this.hashDomain = hashDomain;
        this.orderSetDomain = orderSetDomain;
    }

    private ValuesDomain valuesDomain;

    private ListDomain listDomain;

    private SetDomain setDomain;

    private HashDomain hashDomain;

    private OrderSetDomain orderSetDomain;


    public ValuesDomain getValuesDomain() {
        return valuesDomain;
    }

    public void setValuesDomain(ValuesDomain valuesDomain) {
        this.valuesDomain = valuesDomain;
    }

    public ListDomain getListDomain() {
        return listDomain;
    }

    public void setListDomain(ListDomain listDomain) {
        this.listDomain = listDomain;
    }

    public SetDomain getSetDomain() {
        return setDomain;
    }

    public void setSetDomain(SetDomain setDomain) {
        this.setDomain = setDomain;
    }

    public HashDomain getHashDomain() {
        return hashDomain;
    }

    public void setHashDomain(HashDomain hashDomain) {
        this.hashDomain = hashDomain;
    }

    public OrderSetDomain getOrderSetDomain() {
        return orderSetDomain;
    }

    public void setOrderSetDomain(OrderSetDomain orderSetDomain) {
        this.orderSetDomain = orderSetDomain;
    }
}

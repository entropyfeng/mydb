package com.github.entropyfeng.mydb.server.persistence;

import com.github.entropyfeng.mydb.server.domain.*;
import com.google.common.base.Objects;
import org.jetbrains.annotations.NotNull;

/**
 * @author entropyfeng
 */
public class PersistenceObjectDomain  {

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

    public ListDomain getListDomain() {
        return listDomain;
    }

    public SetDomain getSetDomain() {
        return setDomain;
    }

    public HashDomain getHashDomain() {
        return hashDomain;
    }

    public OrderSetDomain getOrderSetDomain() {
        return orderSetDomain;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o){
            return true;
        }
        if (!(o instanceof PersistenceObjectDomain)){
            return false;
        }
        PersistenceObjectDomain that = (PersistenceObjectDomain) o;
        return  Objects.equal(getValuesDomain(), that.getValuesDomain()) &&
                Objects.equal(getListDomain(), that.getListDomain()) &&
                Objects.equal(getSetDomain(), that.getSetDomain()) &&
                Objects.equal(getHashDomain(), that.getHashDomain()) &&
                Objects.equal(getOrderSetDomain(), that.getOrderSetDomain());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getValuesDomain(), getListDomain(), getSetDomain(), getHashDomain(), getOrderSetDomain());
    }
}

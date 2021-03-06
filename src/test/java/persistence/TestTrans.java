package persistence;

import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.server.PersistenceHelper;
import com.github.entropyfeng.mydb.server.ServerDomain;
import com.github.entropyfeng.mydb.server.domain.*;
import com.github.entropyfeng.mydb.server.persistence.PersistenceObjectDomain;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;


public class TestTrans {

    @Test
    public void testSave() {


        int limit = 100;
        ValuesDomain valuesDomain = new ValuesDomain();
        for (int i = 0; i < limit; i++) {
            valuesDomain.set(i + "", new TurtleValue(i), 0L);
        }

        ListDomain listDomain = new ListDomain();
        for (int i = 0; i < limit; i++) {
            listDomain.leftPush(i + "", new TurtleValue(i));
        }

        SetDomain setDomain = new SetDomain();
        for (int i = 0; i < limit; i++) {
            setDomain.add(i + "", new TurtleValue(i));
        }

        HashDomain hashDomain = new HashDomain();
        for (int i = 0; i < limit; i++) {
            for (int j = 0; j < limit; j++) {
                hashDomain.put(i + "", new TurtleValue(0), new TurtleValue(j));
            }
        }

        OrderSetDomain orderSetDomain = new OrderSetDomain();
        for (int i = 0; i < limit; i++) {
            orderSetDomain.add(i + "", new TurtleValue(i), i);
        }

        PersistenceObjectDomain source = new PersistenceObjectDomain(valuesDomain, listDomain, setDomain, hashDomain, orderSetDomain);
        ServerDomain serverDomain = new ServerDomain(source);

        PersistenceHelper.dumpAll(serverDomain);
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> pair= PersistenceHelper.transDumpFile();

        PersistenceObjectDomain res = PersistenceHelper.loadDomain();

        Assert.assertEquals(source.getValuesDomain(), res.getValuesDomain());
        Assert.assertEquals(source.getListDomain(), res.getListDomain());
        Assert.assertEquals(source.getSetDomain(), res.getSetDomain());
        Assert.assertEquals(source.getOrderSetDomain(), res.getOrderSetDomain());
        //hash出现异常
        Assert.assertEquals(source.getHashDomain(), res.getHashDomain());

        PersistenceObjectDomain fin= PersistenceHelper.dumpAndReLoadFromPair(pair);

        Assert.assertEquals(source,fin);
    }

}

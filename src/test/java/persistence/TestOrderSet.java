package persistence;

import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.server.core.domain.OrderSetDomain;
import com.github.entropyfeng.mydb.server.core.zset.OrderSet;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;

public class TestOrderSet {

    @Test
    public void testWriteAndRead()throws IOException {

        int limit=1;
        HashMap<String, OrderSet<TurtleValue>> map=new HashMap<>();
        OrderSetDomain orderSetDomain=new OrderSetDomain(map);
        for (int i = 0; i <limit ; i++) {
           OrderSet<TurtleValue> orderSet=new OrderSet<>();
            map.put(i+"",orderSet);
            for (int j = 0; j < limit; j++) {
               orderSet.add(new TurtleValue(j),0);
            }
        }

        FileOutputStream fileOutputStream=new FileOutputStream("./testOrderSet");
        DataOutputStream dataOutputStream=new DataOutputStream(fileOutputStream);
        OrderSetDomain.write(orderSetDomain,dataOutputStream);
        //-----------------------------
        FileInputStream fileInputStream = new FileInputStream("./testOrderSet");
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);

      OrderSetDomain res=  OrderSetDomain.read(dataInputStream);
        for (int i = 0; i < limit; i++) {
           OrderSet<TurtleValue> orderSet= res.getHashMap().get(i+"");
            Assert.assertNotNull(orderSet);
            for (int j = 0; j < limit; j++) {
                Assert.assertTrue(orderSet.exists(new TurtleValue(j),j));
            }


        }
    }
}

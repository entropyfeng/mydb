package persistence;

import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.server.config.ServerConfig;
import com.github.entropyfeng.mydb.server.config.ServerConstant;
import com.github.entropyfeng.mydb.server.domain.OrderSetDomain;
import com.github.entropyfeng.mydb.server.core.zset.OrderSet;
import com.github.entropyfeng.mydb.server.persistence.dump.OrderSetDumpTask;
import com.github.entropyfeng.mydb.server.persistence.load.OrderSetLoadTask;
import com.github.entropyfeng.mydb.server.util.ServerUtil;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class TestOrderSet {


    @Test
    public void testWriteAndRead() throws Exception {
        ServerUtil.createDumpFolder();
        CountDownLatch one=new CountDownLatch(1);
        int limit=10;
        HashMap<String, OrderSet<TurtleValue>> map=new HashMap<>();
        OrderSetDomain orderSetDomain=new OrderSetDomain(map);
        for (int i = 0; i <limit ; i++) {
            OrderSet<TurtleValue> orderSet=new OrderSet<>();
            map.put(i+"",orderSet);
            for (int j = 0; j < limit; j++) {
                orderSet.add(new TurtleValue(j),0);
            }
        }
        Long timestamp=System.currentTimeMillis();
        new OrderSetDumpTask(one,orderSetDomain,timestamp).call();
        one.await();

        CountDownLatch two=new CountDownLatch(1);

        File file=new File(ServerConfig.dumpPath+timestamp+ ServerConstant.ORDER_SET_SUFFIX);
        OrderSetDomain res= new OrderSetLoadTask(file,two).call();

        for (int i = 0; i < limit; i++) {
            OrderSet<TurtleValue> orderSet= res.getHashMap().get(i+"");
            Assert.assertNotNull(orderSet);
            for (int j = 0; j < limit; j++) {
                Assert.assertTrue(orderSet.exists(new TurtleValue(j),0));
            }
        }
    }


}

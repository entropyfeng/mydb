package asy;

import com.github.entropyfeng.mydb.client.asy.res.AsyValueOperations;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.DataBody;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;
import com.github.entropyfeng.mydb.server.persistence.factory.AcceptTransThreadFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.*;

public class TestValues {

    ExecutorService service = new ThreadPoolExecutor(4, 8, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new AcceptTransThreadFactory());

    @Test
    public void testSet() throws Exception {

        int limit=1000000;
        ArrayList<CompletableFuture<Pair<ResHead, Collection<DataBody>>>> arrayList=new ArrayList<>(limit);
        AsyValueOperations operations = new AsyValueOperations("0.0.0.0", 4407);

        for (int i = 0; i <limit ; i++) {
            CompletableFuture<Pair<ResHead, Collection<DataBody>>> temp = operations.get("10086");
            arrayList.add(temp);
        }

        Assert.assertTrue(arrayList.get(limit-1).get().getKey().getSuccess());
    }


    @Test
    public void testMulti(){

        int clientNumber = 8;
        for (int i = 0; i < clientNumber; i++) {
            service.submit(() -> {
                try {
                    testSet();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        service.shutdown();
        try {
            service.awaitTermination(10000,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

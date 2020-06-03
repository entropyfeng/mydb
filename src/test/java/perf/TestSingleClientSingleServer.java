package perf;

import com.github.entropyfeng.mydb.client.asy.AsyTemplate;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.DataBody;
import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;

/**
 * 单主机单客户端性能测试
 */
public class TestSingleClientSingleServer {

    AsyTemplate template = new AsyTemplate("0.0.0.0", 4407);

    int limit = 300000;
    CountDownLatch countDownLatch=new CountDownLatch(limit*5);
    ScheduledExecutorService scheduledExecutorService=Executors.newScheduledThreadPool(1);
    @Test
    public void test() {

        int limit = 1000000;
        long begin = System.currentTimeMillis();
        ArrayList<CompletableFuture<Pair<ResHead, Collection<DataBody>>>> list = new ArrayList<>(limit * 5);
        for (int i = 0; i < limit; i++) {
            CompletableFuture<Pair<ResHead, Collection<DataBody>>> future = template.opsForValues().get("");
            list.add(future);
        }
        long end = System.currentTimeMillis();
        System.out.println((end - begin) / 1000);
        list.forEach(pairCompletableFuture -> {
            try {
                Assert.assertTrue(pairCompletableFuture.get().getKey().getSuccess());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        });

        long fin=System.currentTimeMillis();
        System.out.println((fin-begin)/1000);
    }

    @Test
    public  void testMulti(){

        scheduledExecutorService.scheduleAtFixedRate(()-> System.out.println("countdownNumber-> "+countDownLatch.getCount()),5,5,TimeUnit.SECONDS);
        BiConsumer<Pair<ResHead,Collection<DataBody>>,Throwable> biConsumer=(pair, throwable) -> countDownLatch.countDown();

        long begin = System.currentTimeMillis();

        TurtleValue value=new TurtleValue(0);
        for (int i = 0; i < limit; i++) {
            template.opsForValues().get("").whenComplete(biConsumer);
            template.opsForList().exist("").whenComplete(biConsumer);
            template.opsForSet().size().whenComplete(biConsumer);
            template.opsForHash().get("",value).whenComplete(biConsumer);
            template.opsForOrderSet().exists("",value,0d).whenComplete(biConsumer);
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long fin=System.currentTimeMillis();

        System.out.println((fin-begin));
    }

}

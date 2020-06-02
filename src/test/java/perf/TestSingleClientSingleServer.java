package perf;

import com.github.entropyfeng.mydb.client.asy.AsyTemplate;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.TurtleValue;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.*;
import java.util.function.Consumer;

import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.DataBody;
import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;

/**
 * 单主机单客户端性能测试
 */
public class TestSingleClientSingleServer {

    AsyTemplate template = new AsyTemplate("0.0.0.0", 4407);
    ThreadPoolExecutor executor=new ThreadPoolExecutor(5,5,10, TimeUnit.SECONDS,new ArrayBlockingQueue<>(5));
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
    public void testMulti(){
        int limit = 200000;
        long begin = System.currentTimeMillis();
        ArrayList<CompletableFuture<Pair<ResHead, Collection<DataBody>>>>  valueList = new ArrayList<>(limit);
        ArrayList<CompletableFuture<Pair<ResHead, Collection<DataBody>>>> listList = new ArrayList<>(limit);
        ArrayList<CompletableFuture<Pair<ResHead, Collection<DataBody>>>>  setList = new ArrayList<>(limit);
        ArrayList<CompletableFuture<Pair<ResHead, Collection<DataBody>>>>  hashList = new ArrayList<>(limit);
        ArrayList<CompletableFuture<Pair<ResHead, Collection<DataBody>>>>  orderSetList = new ArrayList<>(limit);


        TurtleValue value=new TurtleValue(0);
        for (int i = 0; i < limit; i++) {

            valueList.add(template.opsForValues().get(""));
            listList.add(template.opsForList().exist(""));
            setList.add(template.opsForSet().size());
            hashList.add(template.opsForHash().get("",value));
            orderSetList.add(template.opsForOrderSet().exists("",value,0d));
        }
        long end = System.currentTimeMillis();
        System.out.println((end - begin) / 1000);

        Consumer<CompletableFuture<Pair<ResHead, Collection<DataBody>>>> consumer= pairCompletableFuture -> {
            try {
                Assert.assertTrue(pairCompletableFuture.get().getKey().getSuccess());
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        };

        executor.execute(()->{
            valueList.forEach(consumer);

            System.out.println("valueEnd");
        });
        executor.execute(()->{
            listList.forEach(consumer);
            System.out.println("listEnd");
        });
        executor.execute(()->{
            setList.forEach(consumer);
            System.out.println("settEnd");
        });
        executor.execute(()->{
            orderSetList.forEach(consumer);
            System.out.println("oSetEnd");
        });
        executor.execute(()->{
            hashList.forEach(consumer);
            System.out.println("hashEnd");
        });

        long fin=System.currentTimeMillis();
        executor.shutdown();
        try {
            executor.awaitTermination(60,TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println((fin-begin)/1000);
    }

}

package perf;

import com.github.entropyfeng.mydb.client.asy.AsyTemplate;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import org.junit.Test;

import java.util.Collection;
import java.util.concurrent.CountDownLatch;
import java.util.function.BiConsumer;

public class TestSingleData {

    AsyTemplate template = new AsyTemplate("0.0.0.0", 4407);

    @Test
    public void test(){

        int limit=300000;
        CountDownLatch countDownLatch=new CountDownLatch(limit);

        BiConsumer<Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>>,Throwable> biConsumer=(pair, throwable) -> countDownLatch.countDown();
       long begin= System.currentTimeMillis();
        for (int i = 0; i < limit; i++) {
            template.opsForValues().get("").whenComplete(biConsumer);
        }

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        long end=System.currentTimeMillis();
        System.out.println(end-begin);

    }
}

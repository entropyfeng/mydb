package ops;

import com.github.entropyfeng.mydb.client.ops.TurtleTemplate;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.ops.ValueOperations;
import com.github.entropyfeng.mydb.server.persistence.factory.AcceptTransThreadFactory;
import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;


public class TestValues {

    private ValueOperations valueOperations = new TurtleTemplate("0.0.0.0", 4407).opsForValues();

    ExecutorService service = new ThreadPoolExecutor(4, 8, 10, TimeUnit.SECONDS, new LinkedBlockingQueue<>(), new AcceptTransThreadFactory());

    @Test
    public void testSingle() {
        TurtleValue turtleValue = new TurtleValue(10086);
        valueOperations.set("10087", turtleValue);
        valueOperations.set("10089", turtleValue);
        TurtleValue res = valueOperations.get("10087");
        Assert.assertEquals(turtleValue, res);
    }

    @Test
    public void testMulti() {
        int limit = 10000;
        for (int i = 0; i < limit; i++) {
            valueOperations.set(i + "", new TurtleValue(i));
        }
        for (int i = 0; i < limit; i++) {
            Assert.assertEquals(new TurtleValue(i), valueOperations.get("" + i));
        }
    }

    @Test
    public void testMultiQuery() {
        int limit = 100000;
        for (int i = 0; i < limit; i++) {
            valueOperations.get("");
        }
    }

    @Test
    public void testMultiThreadQuery() {
        int limit = 10000;
        int clientNumber = 8;
        for (int i = 0; i < clientNumber; i++) {
            service.submit(() -> {
                TurtleTemplate turtleTemplate = new TurtleTemplate("0.0.0.0", 4407);
                for (int j = 0; j < limit; j++) {
                    turtleTemplate.opsForValues().get("");
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

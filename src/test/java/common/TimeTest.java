package common;

import com.github.entropyfeng.mydb.server.util.EfficientSystemClock;
import org.junit.Test;


public class TimeTest {

    final long count = 1L << 34;

    @Test
    public void testSystemTime() {
        long begin = System.currentTimeMillis();
        for (long i = 0; i < count; i++) {
            System.currentTimeMillis();
        }

        long end = System.currentTimeMillis();
        System.out.println(end - begin);
    }

    @Test
    public void testCacheTime() {
        long begin = System.currentTimeMillis();
        for (long i = 0; i < count; i++) {
            EfficientSystemClock.now();
        }
        long end = System.currentTimeMillis();
        System.out.println(end - begin);
    }
}

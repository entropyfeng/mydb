package base;

import com.github.entropyfeng.mydb.core.helper.Pair;
import com.github.entropyfeng.mydb.core.zset.SkipList;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;

public class TestSkipList {



    @Test
    public void testInsert(){
        SkipList<Double> skipList = new SkipList<>();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        List<Double> res = random.doubles().limit(1000).distinct().boxed().collect(Collectors.toList());
        res.forEach(aDouble -> skipList.insertNode(aDouble, 666));
        Assert.assertArrayEquals(res.parallelStream().sorted().toArray(), skipList.allValues().stream().mapToDouble(Pair::getKey).boxed().toArray());

    }

    @Test
    public void testDelete(){

        SkipList<Double> skipList=new SkipList<>();
        ThreadLocalRandom random = ThreadLocalRandom.current();
        List<Double> res = random.doubles().limit(1000).distinct().boxed().collect(Collectors.toList());
        res.forEach(aDouble -> skipList.insertNode(aDouble, 666));
        res.forEach(aDouble -> skipList.deleteValue(aDouble,666));
        Assert.assertEquals(0,skipList.size());
    }
}

package base;
import com.github.entropyfeng.mydb.server.core.dict.ElasticMap;
import com.github.entropyfeng.mydb.common.TurtleValue;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;

/**
 * @author entropyfeng
 * @date 2020/3/8 13:33
 */
public class TestElasticMap {



    @Test
    public void test(){
        ElasticMap<TurtleValue, TurtleValue> res=new ElasticMap<>();
        res.put(new TurtleValue("12306"),new TurtleValue("12306"));
        Assert.assertEquals(new TurtleValue("12306"),res.get(new TurtleValue("12306")));
    }


    @Test
    public void testHashMap(){
        int pos=10000000;
        HashMap<TurtleValue, TurtleValue> res=new HashMap<>();
        TurtleValue common=new TurtleValue("hello world");
        for (int i=0;i<pos;i++){
            TurtleValue turtleValue=new TurtleValue(i);
            res.put(turtleValue,common);
        }
        for (int i = 0; i <pos*10 ; i++) {
            res.get(new TurtleValue(i));
        }
        Assert.assertEquals(pos,res.size());
        for (int i=0;i<pos;i++){
            Assert.assertEquals(common,res.get(new TurtleValue(i)));
        }

    }

    @Test
    public void testMulti(){
        int pos=10000000;
        ElasticMap<TurtleValue, TurtleValue> res=new ElasticMap<>();
        TurtleValue common=new TurtleValue("hello world");
        for (int i=0;i<pos;i++){
            res.put(new TurtleValue(i),common);
        }
        for (int i = 0; i <pos*10 ; i++) {
            res.get(new TurtleValue(i));
        }
        Assert.assertEquals(pos,res.size());
        for (int i=0;i<pos;i++){
            Assert.assertEquals(common,res.get(new TurtleValue(i)));
        }

    }


    @Test
    public void testRemove(){
        int pos=10000;
        ElasticMap<TurtleValue,TurtleValue> res=new ElasticMap<>();
        TurtleValue common=new TurtleValue("10086");
        for (int i = 0; i <pos ; i++) {
            res.put(new TurtleValue(i),common);
        }
        for (int i=0;i<pos;i++){
            res.remove(new TurtleValue(i));
        }

        Assert.assertEquals(0,res.size());
    }

}

package base;
import com.github.entropyfeng.mydb.server.core.dict.ElasticMap;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.server.core.dict.MapObject;
import com.github.entropyfeng.mydb.server.domain.HashDomain;
import com.google.common.hash.Hashing;
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
    public void testHashCode(){
        ElasticMap<TurtleValue, TurtleValue> res=new ElasticMap<>();
        for (int i = 0; i < 10000; i++) {
           TurtleValue temp= new TurtleValue(i);
            res.put(temp,new TurtleValue(""));
        }
        System.out.println(res.size());
    }

    @Test
    public void testStringHashCode(){
        ElasticMap<String, String> res=new ElasticMap<>();
        int negCount=0;
        for (int i = 0; i < 10000000; i++) {
            String temp=i+"";
            if (temp.hashCode()<0){
                negCount++;
            }
            res.put(temp,"");
        }
        System.out.println(negCount);
        System.out.println(res.size());
    }
    @Test
    public void testCommon(){
        ElasticMap<String,String> elasticMap=new ElasticMap<>();
        for (int i = 0; i < 10; i++) {
            elasticMap.put("","10086");
        }
        System.out.println(elasticMap.size());
    }
    @Test
    public void testMapObject(){
        MapObject<String,String> mapObject=new MapObject<>(10000);
        for (int i = 0; i < 100; i++) {
            mapObject.put("","");
        }
        System.out.println(mapObject.getUsed());
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

    @Test
    public void testEqual(){

        int limit=10;
        HashDomain source = new HashDomain();
        for (int i = 0; i < limit; i++) {
            for (int j = 0; j < limit; j++) {
                source.put(i + "", new TurtleValue(i), new TurtleValue(0));
            }
        }

        HashDomain des=new HashDomain();
        for (int i = 0; i < limit; i++) {
            for (int j = 0; j < limit; j++) {
                des.put(i + "", new TurtleValue(i), new TurtleValue(0));
            }
        }
        Assert.assertEquals(source,des);
    }

    @Test
    public void testTurtleMap(){
        int limit=10;
        HashMap<String, ElasticMap<TurtleValue, TurtleValue>> hashMap=new HashMap<>();
        for (int i = 0; i < limit; i++) {
            if (!hashMap.containsKey(i+"")){
                hashMap.put(i+"",new ElasticMap<>());
                for (int j = 0; j < limit; j++) {
                    hashMap.get(i+"").put(new TurtleValue(i),new TurtleValue("44"));
                }
            }
        }

        hashMap.forEach((s, map) -> System.out.println(s+" "+map.size()) );
        System.out.println(hashMap.size());
    }

    @Test
    public void testStringMap(){
        int limit=10;
        HashMap<String, ElasticMap<String, String>> hashMap=new HashMap<>();
        for (int i = 0; i < limit; i++) {
            if (!hashMap.containsKey(i+"")){
                hashMap.put(i+"",new ElasticMap<>());
                for (int j = 0; j < limit; j++) {
                    hashMap.get(i+"").put(j+"","");
                }
            }

        }

        hashMap.forEach((s, map) -> System.out.println(s+" "+map.size()) );
        System.out.println(hashMap.size());
    }

    @Test
    public void testSingleTurtleMap(){
        int limit=10;
        HashMap<String, ElasticMap<String, String>> hashMap=new HashMap<>();
        for (int i = 0; i < limit; i++) {
            if (!hashMap.containsKey(i+"")){
               ElasticMap<String,String> elasticMap= new ElasticMap<>();
                hashMap.put(i+"",elasticMap);
                for (int j = 0; j < limit; j++) {
                    elasticMap.put(i+"","");
                }
            }

        }

        hashMap.forEach((s, map) -> System.out.println(s+" "+map.size()) );
        System.out.println(hashMap.size());
    }
}

package base;

import com.github.entropyfeng.mydb.core.dict.ElasticMap;
import com.google.common.hash.Hashing;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author entropyfeng
 * @date 2020/3/8 13:33
 */
public class TestElasticMap {


    public static void main(String[] args) {

        HashMap<Integer,Integer> stringHashMap=new HashMap<>();
        ElasticMap<Integer,Integer> elasticMap=new ElasticMap<>();


        int count=30000000;

        long first=System.currentTimeMillis();
        for (int i=0;i<count;i++){
            stringHashMap.put(ThreadLocalRandom.current().nextInt(),i);
        }

        long second=System.currentTimeMillis();
        for (int i=0;i<count;i++){
           elasticMap.putVal(ThreadLocalRandom.current().nextInt(),i);
        }
        long third=System.currentTimeMillis();

        System.out.println(second-first);
        System.out.println(third-second);
    }

}

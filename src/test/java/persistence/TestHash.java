package persistence;

import com.github.entropyfeng.mydb.core.TurtleValue;
import com.github.entropyfeng.mydb.core.dict.ElasticMap;
import com.github.entropyfeng.mydb.core.domain.HashDomain;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;

public class TestHash {


    @Test
    public void testWriteAndRead() throws IOException {

        int limit=10;
        HashMap<String, ElasticMap<TurtleValue,TurtleValue>> map=new HashMap<>();
        HashDomain hashDomain=new HashDomain(map);
        for (int i = 0; i <limit ; i++) {
            ElasticMap<TurtleValue,TurtleValue> elasticMap=new ElasticMap<>();
            map.put(i+"",elasticMap);
            for (int j = 0; j < limit; j++) {
                elasticMap.put(new TurtleValue(j),new TurtleValue(j));
            }
        }

        FileOutputStream fileOutputStream=new FileOutputStream("./testHash");
        DataOutputStream dataOutputStream=new DataOutputStream(fileOutputStream);
        HashDomain.write(hashDomain,dataOutputStream);
        //-----------------------------
        FileInputStream fileInputStream = new FileInputStream("./testHash");
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);
        HashMap<String, ElasticMap<TurtleValue,TurtleValue>> res = HashDomain.read(dataInputStream).getHashMap();
        for (int i = 0; i <limit ; i++) {
            ElasticMap<TurtleValue,TurtleValue> elasticMap=res.get(i+"");
            for (int j = 0; j < limit; j++) {
                Assert.assertEquals(j,elasticMap.get(new TurtleValue(j)).toObject());
            }
        }


    }
}

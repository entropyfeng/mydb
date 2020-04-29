package persistence;

import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.server.core.domain.SetDomain;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;

public class TestSet {

    @Test
    public void testWriteAndRead()throws IOException {
        HashMap<String, HashSet<TurtleValue>> map = new HashMap<>();
        SetDomain setDomain = new SetDomain(map);
        for (int i = 0; i < 10; i++) {
            HashSet<TurtleValue> turtleValues = new HashSet<>();
            map.put(i + "", turtleValues);
            for (int j = 0; j < 100; j++) {
                turtleValues.add(new TurtleValue(j));
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream("./testSet");
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
        SetDomain.write(setDomain, dataOutputStream);
        //--------------------------------------------------

        FileInputStream fileInputStream = new FileInputStream("./testSet");
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);
        HashMap<String, HashSet<TurtleValue>> res = SetDomain.read(dataInputStream).getSetHashMap();

        dataOutputStream.flush();
        for (int i = 0; i < 10; i++) {
            HashSet<TurtleValue> set= res.get(i+"");
            Assert.assertNotNull(set);
            for (int j = 0; j < 100; j++) {
                Assert.assertTrue(set.contains(new TurtleValue(j)));
            }
        }
    }

    @Test
    public void testWrite()throws IOException{
        HashMap<String, HashSet<TurtleValue>> map = new HashMap<>();
        SetDomain setDomain = new SetDomain(map);
        StringBuilder stringBuilder=new StringBuilder();
        for (int i = 0; i < 1<<29; i++) {
            stringBuilder.append("h");
        }
        for (int i = 0; i < 20; i++) {
            HashSet<TurtleValue> turtleValues = new HashSet<>();
            map.put(i + "", turtleValues);
            for (int j = 0; j < 1; j++) {
                turtleValues.add(new TurtleValue(stringBuilder.toString()));
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream("./testSet");
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
        SetDomain.write(setDomain, dataOutputStream);
        //--------------------------

    }
    @Test
    public void testLog(){
        StringBuilder stringBuilder=new StringBuilder();
        for (int i = 0; i < 1<<4; i++) {
            stringBuilder.append("h");
        }
      double a=  Math.log(stringBuilder.toString().getBytes().length)/Math.log(2);

        System.out.println(a);
    }
}

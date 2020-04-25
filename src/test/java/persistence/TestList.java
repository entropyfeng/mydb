package persistence;

import com.github.entropyfeng.mydb.core.TurtleValue;
import com.github.entropyfeng.mydb.core.domain.ListDomain;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;

public class TestList {

    @Test
    public void testWriteAndRead() throws IOException {
        HashMap<String, LinkedList<TurtleValue>> map = new HashMap<>();
        ListDomain listDomain = new ListDomain(map);
        for (int i = 0; i < 10; i++) {
            LinkedList<TurtleValue> turtleValues = new LinkedList<>();
            map.put(i + "", turtleValues);
            for (int j = 0; j < 100; j++) {
                turtleValues.push(new TurtleValue(j));
            }
        }
        FileOutputStream fileOutputStream = new FileOutputStream("./testList");
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
        ListDomain.write(listDomain, dataOutputStream);
        //--------------------------------------------------

        FileInputStream fileInputStream = new FileInputStream("./testList");
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);
        HashMap<String, LinkedList<TurtleValue>> res = ListDomain.read(dataInputStream).getListMap();

        for (int i = 0; i < 10; i++) {
           LinkedList<TurtleValue> list= res.get(i+"");
           Assert.assertNotNull(list);
            for (int j = 0; j < 100; j++) {
               TurtleValue turtleValue= list.get(j);
               Assert.assertEquals(j,turtleValue.toObject());
            }
        }

    }

}

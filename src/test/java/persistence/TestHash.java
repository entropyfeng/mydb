package persistence;

import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.server.PersistenceHelper;
import com.github.entropyfeng.mydb.server.domain.HashDomain;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class TestHash {

    @Test
    public void test()throws Exception{

        File file=new File("./1-tempHash.dump");
        FileInputStream fileInputStream= new FileInputStream(file);
        DataInputStream dataInputStream=new DataInputStream(fileInputStream);
        HashDomain.read(dataInputStream);
    }

    @Test
    public void testWriteAndRead()throws Exception{
        File file=new File("./2-tempHash.dump");
        FileOutputStream fileOutputStream=new FileOutputStream(file);

        DataOutputStream dataOutputStream=new DataOutputStream(fileOutputStream);
        HashDomain hashDomain=new HashDomain();
        for (int j = 0; j < 10; j++) {
            for (int i = 0; i < 10; i++) {
                hashDomain.put(i+"",new TurtleValue(j),new TurtleValue(j));
            }
        }

        HashDomain.write(hashDomain,dataOutputStream);



        FileInputStream fileInputStream=new FileInputStream(file);
        DataInputStream dataInputStream=new DataInputStream(fileInputStream);
        HashDomain res=  HashDomain.read(dataInputStream);

        Assert.assertEquals(hashDomain, res);
    }
}

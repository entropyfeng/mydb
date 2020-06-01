package persistence;

import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.server.PersistenceHelper;
import com.github.entropyfeng.mydb.server.core.dict.ElasticMap;
import com.github.entropyfeng.mydb.server.domain.HashDomain;
import com.github.entropyfeng.mydb.server.persistence.dump.HashDumpTask;
import com.github.entropyfeng.mydb.server.persistence.load.HashLoadTask;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;
import java.util.HashMap;
import java.util.concurrent.CountDownLatch;

public class TestHash {
    @Test
    public void testSingleHash(){
        HashMap<String, ElasticMap<TurtleValue, TurtleValue>> hashMap=new HashMap<>();
        HashDomain hashDomain=new HashDomain(hashMap);

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                hashDomain.put(i+"",new TurtleValue(j),new TurtleValue("1008611"));
            }
        }


        PersistenceHelper.singleDump(new HashDumpTask(new CountDownLatch(1),hashDomain,System.currentTimeMillis()));

    }
    @Test
    public void testRead()throws Exception{
        File file= PersistenceHelper.getFiles().getHashDumpFile();
        System.out.println(file.getAbsoluteFile());
        HashLoadTask hashLoadTask=new HashLoadTask(file,new CountDownLatch(1));
        hashLoadTask.call();

    }

    @Test
    public void testHash()throws Exception{

        File file= new File("./backup/1590995471170-hash.dump");
        HashLoadTask hashLoadTask=new HashLoadTask(file,new CountDownLatch(1));
        HashDomain hashDomain=  hashLoadTask.call();

    }
    @Test
    public void finTest()throws Exception{
        File file= new File("./backup/1590993655137-hash.dump");
        FileInputStream fileInputStream=new FileInputStream(file);

        DataInputStream dataInputStream=new DataInputStream(fileInputStream);
        HashDomain.read(dataInputStream);

    }
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

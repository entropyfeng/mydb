package persistence;

import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.server.domain.ValuesDomain;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class TestValues {

    /**
     * 测试无过期时间情况
     * @throws IOException IO异常
     */
    @Test
    public void testNullExpire()throws IOException {

        ValuesDomain valuesDomain=new ValuesDomain();
        for (int i = 0; i <1000 ; i++) {
            valuesDomain.set(i+"",new TurtleValue(i),0L);
        }

        FileOutputStream fileOutputStream = new FileOutputStream("./testValues");
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
        ValuesDomain.write(valuesDomain,dataOutputStream);

        //--------------------------------------
        FileInputStream fileInputStream = new FileInputStream("./testValues");
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);
        ValuesDomain res=ValuesDomain.read(dataInputStream);
        res.getValueMap().forEach((s, value) -> {
            Assert.assertTrue(valuesDomain.getValueMap().containsKey(s));
            Assert.assertTrue(valuesDomain.getValueMap().containsValue(value));
        });
    }

    @Test
    public void testExpire()throws IOException{
        ValuesDomain valuesDomain=new ValuesDomain();
        int limit=10000000;
        for (int i = 0; i <limit ; i++) {
            if (i%2==0){
                valuesDomain.set(i+"",new TurtleValue(i),0L);
            }else {
                valuesDomain.set(i+"",new TurtleValue(i),System.currentTimeMillis());
            }
        }


        FileOutputStream fileOutputStream = new FileOutputStream("./testValues");
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
        ValuesDomain.write(valuesDomain,dataOutputStream);

        //--------------------------------------
        FileInputStream fileInputStream = new FileInputStream("./testValues");
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);
        ValuesDomain res=ValuesDomain.read(dataInputStream);
        Assert.assertEquals(limit/2, res.getValueMap().size());
        Assert.assertEquals(0,res.getExpireMap().size());

    }
}

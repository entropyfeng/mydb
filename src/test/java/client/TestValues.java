package client;

import com.github.entropyfeng.mydb.client.ResponseDataTemplate;
import com.github.entropyfeng.mydb.client.TurtleTemplate;
import com.github.entropyfeng.mydb.common.protobuf.ProtoTurtleHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

public class TestValues {
    @Test
    public void testSingle() {
        ResponseDataTemplate responseDataTemplate = new ResponseDataTemplate();

        TurtleProtoBuf.ResponseData responseData = responseDataTemplate.opsForValues().set("1008611", new TurtleValue(187));

        Assert.assertNotNull(responseData);

        Assert.assertTrue(responseData.getSuccess());

        System.out.println(responseData.getSuccess());
        TurtleProtoBuf.ResponseData res = responseDataTemplate.opsForValues().get("1008611");

        Assert.assertEquals(ProtoTurtleHelper.convertToTurtleValue(res.getTurtleValue()).toObject(), 187);

        System.out.println("end");
    }

    @Test
    public void testMulti() {

        ResponseDataTemplate responseDataTemplate = new ResponseDataTemplate();
        for (int i = 0; i < 10; i++) {
            responseDataTemplate.opsForValues().set(i + "", new TurtleValue(i));
        }
        Collection<TurtleProtoBuf.ResponseData> resList = responseDataTemplate.opsForValues().allValues();

        System.out.println(resList.size());
    }

    @Test
    public void testGetCollection() {
        ResponseDataTemplate responseDataTemplate = new ResponseDataTemplate();
        Collection<TurtleProtoBuf.ResponseData> resList = responseDataTemplate.opsForValues().allValues();

    }

    @Test
    public void testHa(){
        int pos=10;
        TurtleTemplate turtleTemplate=new TurtleTemplate();
        TurtleValue[] turtleValue =new TurtleValue[pos];
        for (int i = 0; i <pos ; i++) {
            turtleValue[i]=new TurtleValue(i);
            turtleTemplate.opsForValues().set(i+"",new TurtleValue(i));
        }

      Collection<TurtleValue> res=  turtleTemplate.opsForValues().allValues();
        System.out.println(res.size());
        Assert.assertArrayEquals(turtleValue,turtleTemplate.opsForValues().allValues().toArray());
    }
}

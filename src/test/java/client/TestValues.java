package client;

import com.github.entropyfeng.mydb.client.ResponseDataTemplate;
import com.github.entropyfeng.mydb.common.protobuf.ProtoTurtleHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

public class TestValues {
    @Test
    public void testSingle(){
        ResponseDataTemplate responseDataTemplate =new ResponseDataTemplate();

        TurtleProtoBuf.ResponseData responseData= responseDataTemplate.opsForValues().set("1008611",new TurtleValue(187));

        Assert.assertNotNull(responseData);

        Assert.assertTrue(responseData.getSuccess());

        Assert.assertTrue(responseData.getVoidable());

        TurtleProtoBuf.ResponseData res= responseDataTemplate.opsForValues().get("1008611");

        Assert.assertEquals(ProtoTurtleHelper.convertToTurtleValue(res.getTurtleValue()).toObject(), 187);

    }
    @Test
    public void testMulti(){

        ResponseDataTemplate responseDataTemplate =new ResponseDataTemplate();
        for (int i = 0; i < 10; i++) {
            responseDataTemplate.opsForValues().set(i+"",new TurtleValue(i));
        }
       Collection<TurtleProtoBuf.ResponseData> resList= responseDataTemplate.opsForValues().allValues();

        System.out.println(resList.size());
    }
    @Test
    public void testGetCollection(){
        ResponseDataTemplate responseDataTemplate =new ResponseDataTemplate();
        Collection<TurtleProtoBuf.ResponseData> resList= responseDataTemplate.opsForValues().allValues();

    }
}

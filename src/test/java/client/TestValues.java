package client;

import com.github.entropyfeng.mydb.client.TurtleTemplate;
import com.github.entropyfeng.mydb.common.protobuf.ProtoTurtleHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.obj.TurtleValue;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

public class TestValues {
    @Test
    public void testSingle(){
        TurtleTemplate turtleTemplate=new TurtleTemplate();

        TurtleProtoBuf.ResponseData responseData= turtleTemplate.opsForValues().set("1008611",new TurtleValue(187));

        Assert.assertNotNull(responseData);

        Assert.assertTrue(responseData.getSuccess());

        Assert.assertTrue(responseData.getVoidable());

        TurtleProtoBuf.ResponseData res= turtleTemplate.opsForValues().get("1008611");

        Assert.assertEquals(ProtoTurtleHelper.convertToTurtleValue(res.getTurtleValue()).toObject(), 187);

    }
    @Test
    public void testMulti(){

        TurtleTemplate turtleTemplate=new TurtleTemplate();
        for (int i = 0; i < 10; i++) {
            turtleTemplate.opsForValues().set(i+"",new TurtleValue(i));
        }
       Collection<TurtleProtoBuf.ResponseData> resList= turtleTemplate.opsForValues().allValues();

        System.out.println(resList.size());
    }
    @Test
    public void testGetCollection(){
        TurtleTemplate turtleTemplate=new TurtleTemplate();
        Collection<TurtleProtoBuf.ResponseData> resList= turtleTemplate.opsForValues().allValues();

    }
}

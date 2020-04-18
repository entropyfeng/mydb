package client;

import com.github.entropyfeng.mydb.client.TurtleTemplate;
import com.github.entropyfeng.mydb.common.protobuf.ProtoTurtleHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.obj.TurtleValue;
import org.junit.Assert;
import org.junit.Test;

public class TestValues {
    @Test
    public void test(){
        TurtleTemplate turtleTemplate=new TurtleTemplate();
        TurtleProtoBuf.ResponseData responseData= turtleTemplate.opsForValues().set("1008611",new TurtleValue(187));

        Assert.assertNotNull(responseData);

        Assert.assertTrue(responseData.getSuccess());

        Assert.assertTrue(responseData.getVoidable());

        TurtleProtoBuf.ResponseData res= turtleTemplate.opsForValues().get("1008611");

        Assert.assertEquals(ProtoTurtleHelper.convertToTurtleValue(res.getTurtleValue()).toObject(), 187);


    }
}

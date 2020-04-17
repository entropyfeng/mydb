package proto;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import org.junit.Assert;
import org.junit.Test;

public class TestResponseData {

    @Test
    public void testLast() {

        TurtleProtoBuf.ResponseData responseData = TurtleProtoBuf.ResponseData.newBuilder().setEndAble(true).build();
        byte[] arr = responseData.toByteArray();
        System.out.println(arr.length);
    }

    @Test
    public void test() {

        TurtleProtoBuf.ResponseData responseData = TurtleProtoBuf.ResponseData.newBuilder().setSuccess(true).setCollectionAble(true).build();
        byte[] arr = responseData.toByteArray();
        System.out.println(arr.length);
    }

    @Test
    public void testBuilder() {
        TurtleProtoBuf.ResponseData responseData = TurtleProtoBuf.ResponseData.newBuilder().setResponseSequence(12345).setResponseId(1008611).build();

        Assert.assertEquals(responseData.getResponseId(), 1008611);
        TurtleProtoBuf.ResponseData responseData1 = responseData.toBuilder().setResponseId(123456789).build();
        Assert.assertEquals(123456789, responseData1.getResponseId());
        Assert.assertEquals(12345, responseData1.getResponseSequence());
    }
}

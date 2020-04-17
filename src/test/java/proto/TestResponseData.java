package proto;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
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
}

package proto;

import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import org.junit.Assert;
import org.junit.Test;

public class TestResponseData {

    @Test
    public void testLast() {

        ProtoBuf.ResponseData responseData = ProtoBuf.ResponseData.newBuilder().setEndAble(true).build();
        byte[] arr = responseData.toByteArray();
        System.out.println(arr.length);
    }

    @Test
    public void test() {

    }

    @Test
    public void testBuilder() {

    }
}

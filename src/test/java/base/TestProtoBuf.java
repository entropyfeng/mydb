package base;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import org.junit.Test;

public class TestProtoBuf {
    @Test
    public void testSize(){

       byte[]bytes= TurtleProtoBuf.ResponseData.newBuilder().setStringValue("").setResponseSequence(Long.MAX_VALUE).build().toByteArray();
        System.out.println(bytes.length);
    }


}

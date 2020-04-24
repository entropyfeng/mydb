package base;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.google.common.base.Charsets;
import com.google.protobuf.ByteString;
import org.junit.Test;

import java.nio.charset.Charset;

public class TestProtoBuf {
    @Test
    public void testSize(){

       byte[]bytes= TurtleProtoBuf.ResponseData.newBuilder().setStringValue("").setResponseSequence(Long.MAX_VALUE).build().toByteArray();
        System.out.println(bytes.length);
    }
}

package proto;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import org.junit.Test;

public class TestNullPara {


    @Test(expected = NullPointerException.class)
    public void testNull(){
       TurtleProtoBuf.ResponseData res= TurtleProtoBuf.ResponseData.newBuilder().setException(null).setSuccess(true).build();

    }
}

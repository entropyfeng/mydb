package common;

import com.github.entropyfeng.mydb.common.protobuf.ProtoTurtleHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.obj.TurtleValue;
import com.github.entropyfeng.mydb.util.BytesUtil;
import com.google.protobuf.ByteString;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

public class TestTurtleValue {


    @Test
    public void testByteString(){
      BigInteger bigInteger=new BigInteger("7");
     ByteString byteString= ByteString.copyFrom(bigInteger.toByteArray());
     BigInteger bigInteger1=new BigInteger(byteString.toByteArray());
     Assert.assertEquals(BigInteger.valueOf(7),bigInteger1);
    }

    @Test
    public void test() {

        TurtleValue turtleValue = new TurtleValue(new BigInteger("7"));
        TurtleProtoBuf.TurtleValue turtleValue1 = ProtoTurtleHelper.convertToProtoTurtleValue(turtleValue);
        TurtleValue turtleValue2 = ProtoTurtleHelper.convertToTurtleValue(turtleValue1);
        Assert.assertEquals(turtleValue, turtleValue2);
    }
}

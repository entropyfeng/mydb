package common;

import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.common.protobuf.ProtoTurtleHelper;
import com.github.entropyfeng.mydb.common.TurtleValue;
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
        ProtoBuf.TurtleValue turtleValue1 = ProtoTurtleHelper.convertToProto(turtleValue);
        TurtleValue turtleValue2 = ProtoTurtleHelper.convertToDbTurtle(turtleValue1);
        Assert.assertEquals(turtleValue, turtleValue2);
    }
}

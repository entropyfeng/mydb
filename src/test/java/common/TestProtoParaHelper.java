package common;

import com.github.entropyfeng.mydb.common.TurtleParaType;
import com.github.entropyfeng.mydb.common.protobuf.ProtoParaHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class TestProtoParaHelper {

    private String[] stringArray = new String[]{"10086a", "10086b"};

    private TurtleParaType b2p(TurtleProtoBuf.TurtleParaType turtleParaType) {
        return ProtoParaHelper.convertToTurtleParaType(turtleParaType);
    }

    private TurtleProtoBuf.TurtleParaType p2b(TurtleParaType turtleParaType) {
        return ProtoParaHelper.convertToProtoParaType(turtleParaType);
    }

    @Test
    public void testP2b() {
        Assert.assertEquals(TurtleProtoBuf.TurtleParaType.STRING, p2b(TurtleParaType.STRING));
        Assert.assertEquals(TurtleProtoBuf.TurtleParaType.DOUBLE, p2b(TurtleParaType.DOUBLE));
        Assert.assertEquals(TurtleProtoBuf.TurtleParaType.INTEGER, p2b(TurtleParaType.INTEGER));
        Assert.assertEquals(TurtleProtoBuf.TurtleParaType.LONG, p2b(TurtleParaType.LONG));
        Assert.assertEquals(TurtleProtoBuf.TurtleParaType.NUMBER_INTEGER, p2b(TurtleParaType.NUMBER_INTEGER));
        Assert.assertEquals(TurtleProtoBuf.TurtleParaType.NUMBER_DECIMAL, p2b(TurtleParaType.NUMBER_DECIMAL));
        Assert.assertEquals(TurtleProtoBuf.TurtleParaType.TURTLE_VALUE, p2b(TurtleParaType.TURTLE_VALUE));
        Assert.assertEquals(TurtleProtoBuf.TurtleParaType.COLLECTION, p2b(TurtleParaType.COLLECTION));

    }

    @Test
    public void testB2p() {
        Assert.assertEquals(TurtleParaType.STRING, b2p(TurtleProtoBuf.TurtleParaType.STRING));
        Assert.assertEquals(TurtleParaType.DOUBLE, b2p(TurtleProtoBuf.TurtleParaType.DOUBLE));
        Assert.assertEquals(TurtleParaType.INTEGER, TurtleParaType.INTEGER);
        Assert.assertEquals(TurtleParaType.LONG, b2p(TurtleProtoBuf.TurtleParaType.LONG));
        Assert.assertEquals(TurtleParaType.NUMBER_INTEGER, b2p(TurtleProtoBuf.TurtleParaType.NUMBER_INTEGER));
        Assert.assertEquals(TurtleParaType.NUMBER_DECIMAL, b2p(TurtleProtoBuf.TurtleParaType.NUMBER_DECIMAL));
        Assert.assertEquals(TurtleParaType.TURTLE_VALUE, b2p(TurtleProtoBuf.TurtleParaType.TURTLE_VALUE));
        Assert.assertEquals(TurtleParaType.COLLECTION, b2p(TurtleProtoBuf.TurtleParaType.COLLECTION));

    }

    @Test(expected = UnsupportedOperationException.class)
    public void testB2pException() {
        b2p(TurtleProtoBuf.TurtleParaType.UNRECOGNIZED);
    }



    private TurtleValue add(TurtleValue first, TurtleValue second) {
        first.increment(new BigInteger(second.getValues()));
        return first;
    }

    static class Student {

    }
}

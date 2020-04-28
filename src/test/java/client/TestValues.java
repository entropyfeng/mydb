package client;

import com.github.entropyfeng.mydb.client.ResponseDataTemplate;
import com.github.entropyfeng.mydb.client.TurtleTemplate;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.common.protobuf.ProtoTurtleHelper;
import com.github.entropyfeng.mydb.core.TurtleValue;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

public class TestValues {
    @Test
    public void testSingle() {

    }

    @Test
    public void testMulti() {



    }

    @Test
    public void testGetCollection() {

    }

    @Test
    public void testHa() {
        int pos = 10;
        TurtleTemplate turtleTemplate = new TurtleTemplate();
        TurtleValue[] turtleValue = new TurtleValue[pos];
        for (int i = 0; i < pos; i++) {
            turtleValue[i] = new TurtleValue(i);
            turtleTemplate.opsForValues().set(i + "", new TurtleValue(i));
        }

        Collection<TurtleValue> res = turtleTemplate.opsForValues().allValues();
        Assert.assertArrayEquals(turtleValue, turtleTemplate.opsForValues().allValues().toArray());
    }
}

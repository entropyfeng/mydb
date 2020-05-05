package ops;

import com.github.entropyfeng.mydb.client.ops.TurtleTemplate;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.ops.ListOperations;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;

public class TestList {

    private final ListOperations listOperations = new TurtleTemplate().opsForList();

    @Test
    public void test() {
        ArrayList<TurtleValue> turtleValues = new ArrayList<>();
        int count = 10;
        for (int i = 0; i < count; i++) {
            turtleValues.add(new TurtleValue(i));
        }
        listOperations.leftPushAll("test", turtleValues);
        int size = listOperations.size();

        Assert.assertEquals(1, size);
        int res = listOperations.sizeOf("test");
        Assert.assertEquals(count, res);
    }
}

package ops;

import com.github.entropyfeng.mydb.client.ops.TurtleTemplate;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.ops.ListOperations;
import org.junit.Test;

import java.util.ArrayList;

public class TestList {

    private final ListOperations listOperations=new TurtleTemplate().opsForList();

    @Test
    public void test(){
        ArrayList<TurtleValue> turtleValues=new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            turtleValues.add(new TurtleValue(i));
        }
        listOperations.leftPushAll("test",turtleValues);
        int size= listOperations.size();

        System.out.println(size);
    }
}

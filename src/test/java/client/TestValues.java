package client;

import com.github.entropyfeng.mydb.client.TurtleTemplate;
import com.github.entropyfeng.mydb.core.obj.TurtleValue;
import org.junit.Test;

public class TestValues {
    @Test
    public void test(){
        TurtleTemplate turtleTemplate=new TurtleTemplate();
        turtleTemplate.opsForValues().set("1008611",new TurtleValue("187"));

    }
}

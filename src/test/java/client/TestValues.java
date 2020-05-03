package client;

import com.github.entropyfeng.mydb.client.ops.TurtleTemplate;
import com.github.entropyfeng.mydb.common.TurtleValue;
import org.junit.Assert;
import org.junit.Test;

public class TestValues {

    private TurtleTemplate template=new TurtleTemplate();
    @Test
    public void testSingle() {

        TurtleValue request=new TurtleValue("1008611");
        template.opsForValues().set("10086",request);

        TurtleValue response=template.opsForValues().get("10086");

        Assert.assertEquals(request,response);

    }

    @Test
    public void testSave(){
        TurtleValue request=new TurtleValue("1008611");
        template.opsForValues().set("10086",request);

        TurtleValue response=template.opsForValues().get("10086");

        Assert.assertEquals(request,response);

        template.opsForAdmin().lazyDump();
    }
    @Test
    public void testClear(){
        template.opsForValues().clear();
      TurtleValue turtleValue=  template.opsForValues().get("10086");

      Assert.assertNull(turtleValue);
    }
    @Test
    public void multiSave(){
        for (int i = 0; i < 1000; i++) {
            template.opsForValues().set(i+"",new TurtleValue(i));
        }
        template.opsForAdmin().lazyDump();
    }

    @Test
    public void multiLoad(){
        template.opsForValues().allEntries();
    }
    @Test
    public void testLoad(){
        TurtleValue request=new TurtleValue("1008611");

        TurtleValue response=template.opsForValues().get("10086");

        Assert.assertEquals(request,response);
    }
}

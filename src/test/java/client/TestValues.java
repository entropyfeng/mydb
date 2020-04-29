package client;

import com.github.entropyfeng.mydb.client.ResponseDataTemplate;
import com.github.entropyfeng.mydb.core.TurtleValue;
import com.github.entropyfeng.mydb.core.helper.Pair;
import org.junit.Assert;
import org.junit.Test;

import java.util.Collection;

import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResBody;
import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;

public class TestValues {
    @Test
    public void testSingle() {

        ResponseDataTemplate template = new ResponseDataTemplate();
        Pair<ResHead, Collection<ResBody>> pair = template.opsForValues().set("10086", new TurtleValue(1008611));

        Assert.assertTrue(pair.getKey().getSuccess());
    }


    @Test
    public void testGetCollection() {


    }

}

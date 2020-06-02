package perf;

import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.client.ops.TurtleTemplate;
import com.github.entropyfeng.mydb.server.TurtleServer;

/**
 * 单主机单客户端性能测试
 */
public class TestSingleClientSingleServer {


    public void test(){
        TurtleTemplate template=new TurtleTemplate("0.0.0.0",4407);
        int limit=1000000;
        for (int i = 0; i <limit ; i++) {
            template.opsForValues().get("test");

        }
    }

}

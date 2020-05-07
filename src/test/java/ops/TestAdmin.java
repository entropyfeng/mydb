package ops;

import com.github.entropyfeng.mydb.client.ops.DefaultAdminOperations;
import com.github.entropyfeng.mydb.common.ops.AdminOperations;
import org.junit.Test;

public class TestAdmin {
    private AdminOperations adminOperations=new DefaultAdminOperations();

    @Test
    public void test(){
        adminOperations.slaveOf("0.0.0.0",4407);
    }
}

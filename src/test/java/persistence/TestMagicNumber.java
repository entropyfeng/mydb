package persistence;

import com.github.entropyfeng.mydb.server.config.Constant;
import org.junit.Assert;
import org.junit.Test;

public class TestMagicNumber {

    @Test
    public void test(){
        Assert.assertEquals(6, Constant.MAGIC_NUMBER.length);

    }
}

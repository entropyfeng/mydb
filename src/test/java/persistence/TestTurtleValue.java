package persistence;

import com.github.entropyfeng.mydb.core.TurtleValue;
import org.junit.Assert;
import org.junit.Test;

import java.io.*;

public class TestTurtleValue {


    @Test
    public void testWriteAndRead() throws IOException {


        FileOutputStream fileOutputStream = new FileOutputStream("./testTurtle");
        DataOutputStream dataOutputStream = new DataOutputStream(fileOutputStream);
        TurtleValue turtleValue = new TurtleValue("1008611");
        TurtleValue.write(turtleValue, dataOutputStream);


        FileInputStream fileInputStream = new FileInputStream("./testTurtle");
        DataInputStream dataInputStream = new DataInputStream(fileInputStream);
        TurtleValue res = TurtleValue.read(dataInputStream);
        Assert.assertEquals(turtleValue, res);
    }
}

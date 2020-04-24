package server;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;
import com.github.entropyfeng.mydb.core.domain.ValuesDomain;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.time.Instant;

public class TestBackUp {

    @Test
    public void test()throws Exception{

        TurtleProtoBuf.TurtleValue value= TurtleProtoBuf.TurtleValue.newBuilder().setTurtleParaType(TurtleProtoBuf.TurtleParaType.INTEGER).build();
        System.out.println(value.toByteArray().length);

    }
}

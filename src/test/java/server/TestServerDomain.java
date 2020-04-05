package server;

import com.github.entropyfeng.mydb.common.CommonCommand;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.server.ServerDomain;
import com.github.entropyfeng.mydb.server.TurtleServer;
import com.github.entropyfeng.mydb.server.command.ValuesCommand;
import org.junit.Test;

public class TestServerDomain {


    @Test
    public void x() {
        TurtleServer turtleServer = new TurtleServer("127.0.0.1", 4407);
        ServerDomain serverDomain = new ServerDomain(turtleServer);
        TurtleProtoBuf.ClientCommand clientCommand = CommonCommand.insertValue();





    }
}

package asy;

import com.github.entropyfeng.mydb.client.asy.res.AsyValueOperations;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.DataBody;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;
import org.junit.Test;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class TestValues {


    @Test
    public void testSet() throws Exception {

        AsyValueOperations operations = new AsyValueOperations("0.0.0.0", 4407);

        CompletableFuture<Pair<ResHead, Collection<DataBody>>> future = operations.set("10086", new TurtleValue(4478), 0L);

      //  CompletableFuture<Pair<ResHead, Collection<DataBody>>> future = operations.get("10086");

        Pair<ResHead, Collection<DataBody>> pair= future.get();

        System.out.println(pair.getKey().getSuccess());
    }

}

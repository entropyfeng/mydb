package asy;

import com.github.entropyfeng.mydb.client.asy.res.AsyValueOperations;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.DataBody;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class TestValues {


    @Test
    public void testSet() throws Exception {

        int limit=1000000;
        ArrayList<CompletableFuture<Pair<ResHead, Collection<DataBody>>>> arrayList=new ArrayList<>(limit);
        AsyValueOperations operations = new AsyValueOperations("0.0.0.0", 4407);

        CompletableFuture<Pair<ResHead, Collection<DataBody>>> future = operations.set("10086", new TurtleValue(4478), 0L);

        for (int i = 0; i <limit ; i++) {
            CompletableFuture<Pair<ResHead, Collection<DataBody>>> temp = operations.get("10086");
            arrayList.add(temp);
        }

        Assert.assertTrue(arrayList.get(limit-1).get().getKey().getSuccess());
    }

}

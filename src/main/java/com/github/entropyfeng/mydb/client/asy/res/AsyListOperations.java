package com.github.entropyfeng.mydb.client.asy.res;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.client.asy.AsyClientExecute;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.*;

/**
 * @author entropyfeng
 */
public class AsyListOperations {
    public AsyListOperations(AsyClientExecute execute){
        this.execute=execute;
    }
    private final AsyClientExecute execute;
    public @NotNull CompletableFuture<Pair<ResHead, Collection<DataBody>>> exist(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.LIST, "exist");
        builder.addStringPara(key);
        return execute.execute(builder);
    }
}

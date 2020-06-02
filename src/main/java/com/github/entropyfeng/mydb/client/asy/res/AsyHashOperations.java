package com.github.entropyfeng.mydb.client.asy.res;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.client.asy.AsyClientExecute;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

/**
 * @author entropyfeng
 */
public class AsyHashOperations {
    public AsyHashOperations(AsyClientExecute execute){
        this.execute=execute;
    }
    private final AsyClientExecute execute;


    public @NotNull CompletableFuture<Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>>> get(@NotNull String key, @NotNull TurtleValue tKey) {

        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.HASH, "get");
        builder.addStringPara(key);
        builder.addTurtlePara(tKey);
        return execute.execute(builder);
    }
}

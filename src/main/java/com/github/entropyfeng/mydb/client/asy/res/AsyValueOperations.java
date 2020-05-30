package com.github.entropyfeng.mydb.client.asy.res;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.client.asy.AsyClientExecute;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.TurtleValue;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.DataBody;
import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;

/**
 * @author entropyfeng
 */
public class AsyValueOperations {

    public AsyValueOperations(String host, Integer port) {
        execute = new AsyClientExecute(host, port);
    }

    private AsyClientExecute execute;

    public CompletableFuture<Pair<ResHead, Collection<DataBody>>> set(@NotNull String key, @NotNull TurtleValue value, @NotNull Long time) {

        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "set");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.addLongPara(time);
        builder.setModifyAble(true);
        return execute.execute(builder);
    }

    public CompletableFuture<Pair<ResHead, Collection<DataBody>>> get(@NotNull String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "get");
        builder.addStringPara(key);
        return execute.execute(builder);
    }
}

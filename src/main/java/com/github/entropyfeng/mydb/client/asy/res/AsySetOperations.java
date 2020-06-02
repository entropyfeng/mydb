package com.github.entropyfeng.mydb.client.asy.res;

import com.github.entropyfeng.mydb.client.ClientCommandBuilder;
import com.github.entropyfeng.mydb.client.asy.AsyClientExecute;
import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;
import com.github.entropyfeng.mydb.common.protobuf.ProtoExceptionHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.*;

/**
 * @author entropyfeng
 */
public class AsySetOperations {


    public AsySetOperations(AsyClientExecute execute){
        this.execute=execute;
    }
    private final AsyClientExecute execute;

    public @NotNull CompletableFuture<Pair<ResHead, Collection<DataBody>>> add(String key, TurtleValue value) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "add");
        builder.addStringPara(key);
        builder.addTurtlePara(value);
        builder.setModifyAble(true);
        execute.execute(builder).thenApply(pair -> pair.getKey().getSuccess());
        return execute.execute(builder);
    }
    public @NotNull CompletableFuture<Pair<ResHead, Collection<DataBody>>> size() {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "size");
        return execute.execute(builder);
    }


    public @NotNull CompletableFuture<Pair<ResHead, Collection<DataBody>>> sizeOf(String key) {
        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.SET, "sizeOf");
        builder.addStringPara(key);
        return execute.execute(builder);
    }
}

package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.client.ops.ResponseHashOperations;
import com.github.entropyfeng.mydb.common.ops.HashOperations;
import com.github.entropyfeng.mydb.common.ops.IHashOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoExceptionHelper;
import com.github.entropyfeng.mydb.common.protobuf.ProtoTurtleHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public class DefaultHashOperations implements HashOperations {
    
    private IHashOperations hashOperations=new ResponseHashOperations();
    @Override
    public TurtleValue get(@NotNull String key, @NotNull TurtleValue tKey) {
       TurtleProtoBuf.ResponseData res= hashOperations.get(key,tKey);
       if (res.getSuccess()){
           return ProtoTurtleHelper.convertToTurtleValue(res.getTurtleValue());
       }
        ProtoExceptionHelper.handler(res.getExceptionType(),res.getException());
        return null;
    }

    @Override
    public @NotNull Collection<TurtleValue> get(@NotNull String key) {


        return null;
    }

    @Override
    public @NotNull Boolean exists(@NotNull String key, @NotNull TurtleValue tKey, TurtleValue tValue) {
        return null;
    }

    @Override
    public @NotNull Boolean exists(@NotNull String key) {
        return null;
    }

    @Override
    public @NotNull Void put(@NotNull String key, @NotNull TurtleValue tKey, @NotNull TurtleValue tValue) {
        return null;
    }

    @Override
    public @NotNull Boolean delete(@NotNull String key) {
        return null;
    }

    @Override
    public @NotNull Boolean delete(@NotNull String key, @NotNull TurtleValue tKey) {
        return null;
    }

    @Override
    public @NotNull Integer sizeOf(@NotNull String key) {
        return null;
    }
}

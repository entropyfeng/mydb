package com.github.entropyfeng.mydb.client.conn;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author entropyfeng
 */
public class TurtleTask implements Future<TurtleProtoBuf.ResponseData> {
    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public TurtleProtoBuf.ResponseData get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public TurtleProtoBuf.ResponseData get(long timeout, @NotNull TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }


}

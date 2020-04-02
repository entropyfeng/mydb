package com.github.entropyfeng.mydb.server.command;

import io.netty.channel.Channel;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author entropyfeng
 */
public class ValuesCommand implements ICommand {


    private final Method method;
    private final List<Object> values;
    private final Channel channel;
    private final Long requestId;

    public ValuesCommand(Method method, List<Object> values, Channel channel, Long requestId) {
        this.method = method;
        this.values = values;
        this.channel = channel;
        this.requestId = requestId;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public List<Object> getValues() {
        return values;
    }

    @Override
    public Long getRequestId() {
        return requestId;
    }
}

package com.github.entropyfeng.mydb.server.command;

import io.netty.channel.Channel;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author entropyfeng
 */
public class ClientCommand implements ICommand {
    public ClientCommand(Method method, List<Object> values, Channel channel, Long requestId) {
        this.method = method;
        this.values = values;
        this.channel = channel;
        this.requestId = requestId;
    }

    private final Method method;
    private final List<Object> values;
    private final Channel channel;
    private final Long requestId;

    @Override
    public Long getRequestId() {
        return requestId;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public List<Object> getValues() {
        return values;
    }


}

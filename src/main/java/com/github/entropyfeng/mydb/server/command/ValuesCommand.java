package com.github.entropyfeng.mydb.server.command;

import io.netty.channel.Channel;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author entropyfeng
 */
public class ValuesCommand  {


    private final Method method;
    private final List<Object> values;
    private final Channel channel;
    public ValuesCommand(Method method, List<Object> values,Channel channel) {
        this.method = method;
        this.values = values;
        this.channel=channel;
    }

    public Channel getChannel() {
        return channel;
    }

    public Method getMethod() {
        return method;
    }

    public List<Object> getValues() {
        return values;
    }
}

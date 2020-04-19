package com.github.entropyfeng.mydb.server.command;

import io.netty.channel.Channel;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author entropyfeng
 */
public class ListCommand implements ICommand {
    @Override
    public Long getRequestId() {
        return null;
    }

    @Override
    public Method getMethod() {
        return null;
    }

    @Override
    public Channel getChannel() {
        return null;
    }

    @Override
    public List<Object> getValues() {
        return null;
    }

    @Override
    public Long getRequestSequence() {
        return null;
    }
}

package com.github.entropyfeng.mydb.server.command;

import io.netty.channel.Channel;

import java.lang.reflect.Method;
import java.util.List;

public class Command {

    public Method method;
    public List<Object> values;
    public Long requestId;
    public Channel channel;

}

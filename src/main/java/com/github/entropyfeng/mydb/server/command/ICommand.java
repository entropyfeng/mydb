package com.github.entropyfeng.mydb.server.command;

import io.netty.channel.Channel;

import java.lang.reflect.Method;

import java.util.List;

public interface ICommand {
    public Long getRequestId();

    public Method getMethod();

    public Channel getChannel();

    List<Object> getValues();

}

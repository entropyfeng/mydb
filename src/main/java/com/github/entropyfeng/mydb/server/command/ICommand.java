package com.github.entropyfeng.mydb.server.command;

import io.netty.channel.Channel;

import java.lang.reflect.Method;
import java.util.List;

/**
 * @author entropyfeng
 */
public interface ICommand {

    Long getRequestId();

    Method getMethod();

    Channel getChannel();

    List<Object> getValues();

}

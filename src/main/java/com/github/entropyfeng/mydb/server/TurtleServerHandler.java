package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.server.command.AdminCommand;
import com.github.entropyfeng.mydb.server.command.ConcreteCommand;
import com.github.entropyfeng.mydb.server.command.IClientCommand;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author entropyfeng
 */
public class TurtleServerHandler extends SimpleChannelInboundHandler<TurtleProtoBuf.ClientCommand> {
    private static final Logger logger= LoggerFactory.getLogger(TurtleServerHandler.class);

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        logger.info("channel in {} is register",ctx.channel().remoteAddress());
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("channel in {} is active",ctx.channel().remoteAddress());
    }



    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TurtleProtoBuf.ClientCommand msg) throws Exception {

      IClientCommand clientCommand= ClientCommandHelper.parseCommand(msg);

    }
}

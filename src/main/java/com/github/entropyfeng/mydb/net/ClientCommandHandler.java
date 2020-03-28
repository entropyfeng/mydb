package com.github.entropyfeng.mydb.net;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author entropyfeng
 */
public class ClientCommandHandler extends SimpleChannelInboundHandler<TurtleProtoBuf.ClientCommand> {
    private static final Logger logger= LoggerFactory.getLogger(ClientCommandHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("channel in {} is active",ctx.channel().remoteAddress());
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        logger.info("channel in {} is register",ctx.channel().remoteAddress());
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TurtleProtoBuf.ClientCommand msg) throws Exception {
        System.out.println(msg.getOperationName());
    }
}

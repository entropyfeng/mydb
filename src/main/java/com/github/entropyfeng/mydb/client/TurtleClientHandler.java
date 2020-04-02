package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.CommonConstant;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author entropyfeng
 */
public class TurtleClientHandler extends SimpleChannelInboundHandler<TurtleProtoBuf.ResponseData> {

    private static final Logger logger= LoggerFactory.getLogger(TurtleClientHandler.class);

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("channel active");

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        cause.printStackTrace();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TurtleProtoBuf.ResponseData msg) throws Exception {
        System.out.println(msg.getSuccess());
    }

}

package com.github.entropyfeng.mydb.net;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;

/**
 * @author entropyfeng
 * @date 2020/3/5 18:47
 */

public class TurtleServerHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger=LoggerFactory.getLogger(TurtleServerHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        Unpooled a;
        Channel channel=ctx.channel();

        ByteBuf byteBuf=Unpooled.copiedBuffer("you data",CharsetUtil.UTF_8);


        ChannelFuture channelFuture=channel.writeAndFlush(byteBuf);
        channelFuture.addListener((ChannelFutureListener) future -> {
            if(future.isSuccess()){
                System.out.println("success");
            }else {
                System.out.println("error");
                future.cause().printStackTrace();
            }
        });

    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        super.channelReadComplete(ctx);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

}

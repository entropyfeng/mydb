package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.client.conn.TurtleClientChannelFactory;
import com.github.entropyfeng.mydb.common.CommonCommand;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @author entropyfeng
 */
public class TurtleClient {

    public void start() throws InterruptedException {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap client = new Bootstrap();
            client.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(ClientConstant.HOST, ClientConstant.PORT)
                    .option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(1 << 10, 1 << 20, 1 << 30))
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new TurtleClientChannelInitializer());

            ChannelFuture channelFuture = client.connect().sync();
            System.out.println("client start and bind on " + channelFuture.channel().localAddress() + " and connect to" + channelFuture.channel().remoteAddress());
            if (channelFuture.isSuccess()) {
                TurtleClientChannelFactory.setChannel(channelFuture.channel());
                channelFuture.channel().writeAndFlush(CommonCommand.sayHelloCommand());
                channelFuture.channel().closeFuture().sync();
            }
        } finally {

            // 如果连接不成功则直接关闭
            eventLoopGroup.shutdownGracefully().sync();
        }
    }
}

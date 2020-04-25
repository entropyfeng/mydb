package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.client.conn.TurtleClientChannelFactory;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author entropyfeng
 */
public class TurtleClient {

    private static Logger logger= LoggerFactory.getLogger(TurtleClient.class);
    private volatile Channel channel;
    private Bootstrap client;
    public void start() throws InterruptedException {
       NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        client = new Bootstrap();
        client.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress(ClientConstant.HOST, ClientConstant.PORT)
                .option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(1 << 10, 1 << 20, 1 << 30))
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new TurtleClientChannelInitializer());
        /* try {
            ChannelFuture channelFuture = client.connect().sync();
            System.out.println("client start and bind on " + channelFuture.channel().localAddress() + " and connect to" + channelFuture.channel().remoteAddress());
            if (channelFuture.isSuccess()) {
                TurtleClientChannelFactory.setChannel(channelFuture.channel());
                TurtleClientChannelFactory.setReady(true);
                System.out.println("setReady");

                channelFuture.sync();
               // channelFuture.channel().closeFuture().sync();
               // channelFuture.channel().writeAndFlush(CommonCommand.sayHelloCommand());
                System.out.println("endSet");
            }

        } finally {


            // 如果连接不成功则直接关闭
            eventLoopGroup.shutdownGracefully().sync();
        }*/
      doConnect();
    }

    private void doConnect(){


        if (channel!=null&&channel.isActive()){
            return;
        }

        ChannelFuture connect=client.connect();
        connect.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()){
                this.channel=future.channel();
                TurtleClientChannelFactory.setReady(true);
                TurtleClientChannelFactory.setChannel(channel);
                System.out.println("success connect...");
            }else {
                System.out.println("reConnect...");
                TurtleClientChannelFactory.setReady(false);
                TurtleClientChannelFactory.setChannel(null);
                future.channel().eventLoop().schedule(this::doConnect, 3, TimeUnit.SECONDS);
            }
        });
    }
}

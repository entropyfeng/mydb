package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.client.handler.TurtleClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author entropyfeng
 */
public class TurtleClient {

    private static Logger logger = LoggerFactory.getLogger(TurtleClient.class);
    private volatile Channel channel;
    private volatile Bootstrap client;
    private String host;
    private Integer port;
    private volatile CountDownLatch countDownLatch;

    public TurtleClient(String host, Integer port) {
        this.host = host;
        this.port = port;
    }

    public TurtleClient() {
        host = ClientConstant.HOST;
        port = ClientConstant.PORT;
    }

    public void start() throws InterruptedException {
        countDownLatch=new CountDownLatch(1);
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        client = new Bootstrap();
        client.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .remoteAddress(host, port)
                .option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(1 << 10, 1 << 20, 1 << 30))
                .option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new TurtleClientChannelInitializer());

        ChannelFuture channelFuture = client.connect().sync();

        System.out.println("do");
        countDownLatch.countDown();
        channel = channelFuture.channel();
        System.out.println("sds");
        channel.closeFuture().sync();
        //doConnect();
    }

    private void doConnect() {

        if (channel != null && channel.isActive()) {
            return;
        }
        countDownLatch = new CountDownLatch(1);
        ChannelFuture connect = client.connect();
        connect.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                this.channel = future.channel();
                countDownLatch.countDown();
                System.out.println("success connect...");
            } else {
                System.out.println("reConnect...");
                future.channel().eventLoop().schedule(this::doConnect, 3, TimeUnit.SECONDS);
            }
        });
    }

    public Channel getChannel() {

        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return channel;
    }
}

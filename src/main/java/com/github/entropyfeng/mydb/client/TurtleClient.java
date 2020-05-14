package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.client.conn.ClientThreadFactory;
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
 * {@link TurtleClient}的生命周期与调用它的程序的线程一致
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
        host = ClientConfig.desHost;
        port = ClientConfig.desPort;
    }


    /**
     * create a new thread as a daemon thread to handle client command
     */
    private void start(){
        new ClientThreadFactory().newThread(() -> {
            logger.info("client start at host-> {}, port->{}",host,port);
            NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
            client = new Bootstrap();
            client.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(host, port)
                    .option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(1 << 10, 1 << 20, 1 << 30))
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new TurtleClientChannelInitializer());

            doConnect();
        }).start();
    }


    private void doConnect() {
        countDownLatch = new CountDownLatch(1);
        if (channel != null && channel.isActive()) {
            return;
        }
        ChannelFuture connect = client.connect();
        connect.addListener((ChannelFutureListener) future -> {
            if (future.isSuccess()) {
                this.channel = future.channel();
                countDownLatch.countDown();
                logger.info("success connected...");
                //it call may throw exception
                future.channel().closeFuture().sync();
                logger.info("client closed");
            } else {
                logger.info("reConnect....");
                future.channel().eventLoop().schedule(this::doConnect, 3, TimeUnit.SECONDS);
            }
        });
    }

    public Channel getChannel() {

        start();
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return channel;
    }
}

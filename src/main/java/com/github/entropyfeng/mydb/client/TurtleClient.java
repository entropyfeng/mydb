package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.config.SupportModel;
import com.github.entropyfeng.mydb.config.SupportObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

/**
 * @author entropyfeng
 */
public class TurtleClient {
    private static final Logger logger = LoggerFactory.getLogger(TurtleClient.class);
    private final int port;
    private final String host;

    /**
     * assume the param is correct
     *
     * @param port 端口
     * @param host 主机地址
     */
    public TurtleClient(String host, int port) {
        this.port = port;
        this.host = host;
    }

    /**
     * assume the param is correct
     *
     * @param port 端口
     * @param host 主机地址
     */
    TurtleClient(String host, String port) {
        this.port = Integer.parseInt(port);
        this.host = host;
    }

    public void start() throws Exception {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap client = new Bootstrap();
            client.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(host, port)
                    .option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(1 << 10, 1 << 20, 1 << 30))
                    .option(ChannelOption.SO_KEEPALIVE, true)
            .handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    logger.info("insert channel");
                    ch.pipeline().addLast(new CommandToByteEncoder());
                }
            });

            ClientCommand clientCommand = new ClientCommand(SupportModel.COMMON, SupportObject.VALUE, "helloWorld");
            ChannelFuture channelFuture = client.connect().channel().writeAndFlush(clientCommand).sync();
            logger.info("{} start and bind on {} and connect to {}", this.getClass().getName(), channelFuture.channel().localAddress(), channelFuture.channel().remoteAddress());

            channelFuture.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }

}

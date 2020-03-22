package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.config.SupportModel;
import com.github.entropyfeng.mydb.config.SupportObject;
import com.github.entropyfeng.mydb.net.ByteToCommandDecoder;
import com.github.entropyfeng.mydb.net.ClientCommandHandler;
import com.github.entropyfeng.mydb.net.TurtleServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
                    .remoteAddress(host, port)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(1 << 10, 1 << 20, 1 << 30))
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new CommandToByteEncoder());

                            //socketChannel.pipeline().addLast(new RequestResultHandler());
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

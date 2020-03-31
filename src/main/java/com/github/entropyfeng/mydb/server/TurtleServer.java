package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author entropyfeng
 */
public class TurtleServer {


    private static final Logger logger = LoggerFactory.getLogger(TurtleServer.class);
    private final int port;
    private final String host;
    private ServerDomain serverDomain;

    /**
     * assume the param is correct
     *
     * @param port 端口
     * @param host 主机地址
     */
    public TurtleServer(String host, String port) {

        this.port = Integer.parseInt(port);
        this.host = host;
    }

    /**
     * assume the param is correct
     *
     * @param port 端口
     * @param host 主机地址
     */
    public TurtleServer(String host, int port) {
        this.port = port;
        this.host = host;
    }


    public void start() throws Exception {

        serverDomain=new ServerDomain(this);
        serverDomain.start();
        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        NioEventLoopGroup worker = new NioEventLoopGroup(1);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(1 << 10, 1 << 20, 1 << 30))
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .localAddress(host, port)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());//入站
                            ch.pipeline().addLast(new ProtobufDecoder(TurtleProtoBuf.ClientCommand.getDefaultInstance()));//入站
                            ch.pipeline().addLast(new TurtleServerHandler(serverDomain));//入站
                            ch.pipeline().addLast(new TurtleServerProtoEncoder());//出站
                        }
                    });

            ChannelFuture channelFuture = serverBootstrap.bind().sync();

            logger.info("server start and listen on {}", channelFuture.channel().localAddress());
            channelFuture.channel().closeFuture().sync();
            logger.info("server will close");
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully().sync();
            logger.info("server close gracefully");
        }
    }

}

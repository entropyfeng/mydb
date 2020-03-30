package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.CommonConstant;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.server.TurtleServer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
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
                    .channel(NioSocketChannel.class)
                    .remoteAddress(host, port)
                    .option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(1 << 10, 1 << 20, 1 << 30))
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ch.pipeline().addLast("encoder", new TurtleClientProtoEncoder());//出站
                            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());//入站
                            ch.pipeline().addLast(new ProtobufDecoder(TurtleProtoBuf.ResponseData.getDefaultInstance()));//入站
                            ch.pipeline().addLast("handler", new TurtleClientHandler());//入站
                        }
                    });

            ChannelFuture channelFuture = client.connect().sync();
            logger.info("client start and bind on {} and connect to {}", channelFuture.channel().localAddress(), channelFuture.channel().remoteAddress());
            if(channelFuture.isSuccess()) {
                TurtleClientChannelFactory.setChannel(channelFuture.channel());
            }
            channelFuture.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }

}

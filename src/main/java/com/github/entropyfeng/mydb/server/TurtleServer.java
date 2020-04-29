package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.server.handler.TurtleServerChannelInitializer;
import com.github.entropyfeng.mydb.server.handler.TurtleServerHandler;
import com.github.entropyfeng.mydb.server.handler.TurtleServerProtoEncoder;
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
     * @param port 端口
     * @param host 主机地址
     */
    public TurtleServer(String host, String port) {
        this.port = Integer.parseInt(port);
        this.host = host;
    }

    /**
     * assume the param is correct
     * @param port 端口
     * @param host 主机地址
     */
    public TurtleServer(String host, int port) {
        this.port = port;
        this.host = host;
        logger.info("server at host-> {} ,port ->{}",host,port);
    }


    public void start() throws Exception {

        //从dump加载实例
        logger.info("begin load dump file.");
        serverDomain=PersistenceHelper.load();
        logger.info("complete load dump file");

        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        //IO密集型 2n+1
        NioEventLoopGroup worker = new NioEventLoopGroup(2*Runtime.getRuntime().availableProcessors()+1);
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(1 << 10, 1 << 20, 1 << 30))
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .localAddress(host, port)
                    .childHandler(new TurtleServerChannelInitializer(serverDomain));

            ChannelFuture channelFuture = serverBootstrap.bind().sync();

            logger.info("server start and listen on {}", channelFuture.channel().localAddress());
            channelFuture.channel().closeFuture().sync();
            logger.info("server will close");
        } finally {
            boss.shutdownGracefully().sync();
            worker.shutdownGracefully().sync();
            logger.info("server close gracefully");
        }
    }

}

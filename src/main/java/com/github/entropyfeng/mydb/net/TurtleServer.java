package com.github.entropyfeng.mydb.net;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author entropyfeng
 */
public class TurtleServer {


    private static final Logger logger= LoggerFactory.getLogger(TurtleServer.class);
    private final int port;
    private final String host;

    /**
     * assume the param is correct
     * @param port 端口
     * @param host 主机地址
     */
   public TurtleServer(String host, String port ){

        this.port=Integer.parseInt(port);
        this.host=host;
    }
    /**
     * assume the param is correct
     * @param port 端口
     * @param host 主机地址
     */
   public TurtleServer(String host, int port ){
        this.port=port;
        this.host=host;
    }

    public void start()throws Exception{
        NioEventLoopGroup eventLoopGroup=new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .childOption(ChannelOption.RCVBUF_ALLOCATOR,new AdaptiveRecvByteBufAllocator(1<<10,1<<20,1<<30))
                    .childOption(ChannelOption.SO_KEEPALIVE,true)
                    .localAddress(host,port)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                           /*ch.pipeline().addLast(new ProtobufVarint32FrameDecoder());
                           ch.pipeline().addLast(new ClientCommandHandler());*/
                           ch.pipeline().addLast(new TurtleServerHandler());
                        }
                    });

            ChannelFuture channelFuture=serverBootstrap.bind().sync();

            logger.info("{} start and listen on {}" ,this.getClass().getName(),channelFuture.channel().localAddress());
            channelFuture.channel().closeFuture().sync();
        }finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }

}

package com.github.entropyfeng.mydb.net;

import com.github.entropyfeng.mydb.config.CommonConfig;
import com.github.entropyfeng.mydb.config.Constant;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.checkerframework.checker.units.qual.C;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * @author entropyfeng
 * @date 2020/3/5 14:21
 */
public class EchoServer {

    private static final Logger logger=LoggerFactory.getLogger(EchoServer.class);
    private final int port;

    private final String host;

    /**
     * assume the param is correct
     * @param port 端口
     * @param host 主机地址
     */
    EchoServer(String port, String host){

        this.port=Integer.parseInt(port);
        this.host=host;
    }
    /**
     * assume the param is correct
     * @param port 端口
     * @param host 主机地址
     */
    EchoServer(int port, String host){
        this.port=port;
        this.host=host;
    }

    public void start()throws Exception{
        NioEventLoopGroup eventLoopGroup=new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap=new ServerBootstrap();
            serverBootstrap.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .localAddress(host,port)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            
                            ch.pipeline().addLast(new EchoServerHandler());
                        }
                    });

            ChannelFuture channelFuture=serverBootstrap.bind().sync();

            logger.info("{} start and listen on {}" ,this.getClass().getName(),channelFuture.channel().localAddress());
            channelFuture.channel().closeFuture().sync();
        }finally {
            eventLoopGroup.shutdownGracefully().sync();
        }
    }

    public static void main(String[] args)throws Exception {
        String port=CommonConfig.getProperties().getProperty(Constant.PORT);
        String host=CommonConfig.getProperties().getProperty(Constant.HOST);
        new EchoServer(port,host).start();
    }

}

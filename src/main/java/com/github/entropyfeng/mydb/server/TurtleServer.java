package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.server.config.ServerConfig;
import com.github.entropyfeng.mydb.server.handler.TurtleServerChannelInitializer;
import com.github.entropyfeng.mydb.server.persistence.dump.CircleDumpTask;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author entropyfeng
 */
public class TurtleServer {


    private static final Logger logger = LoggerFactory.getLogger(TurtleServer.class);
    private final int port;
    private final String host;

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
        logger.info("server at host-> {} ,port ->{}", host, port);
    }


    public void start() throws Exception {



        //从dump加载实例
        logger.info("begin load dump file.");
        ServerDomain serverDomain = PersistenceHelper.load();
        logger.info("complete load dump file");

        NioEventLoopGroup boss = new NioEventLoopGroup(1);
        //设置定时转储周期
        boss.scheduleAtFixedRate(new CircleDumpTask(serverDomain), ServerConfig.dumpCircle, ServerConfig.dumpCircle, TimeUnit.SECONDS);
        //IO密集型 2n+1
        NioEventLoopGroup worker = new NioEventLoopGroup(2 * Runtime.getRuntime().availableProcessors() + 1);


        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, worker)
                .channel(NioServerSocketChannel.class)
                .childOption(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(1 << 10, 1 << 20, 1 << 30))
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .localAddress(host, port)
                .childHandler(new TurtleServerChannelInitializer(serverDomain));

        ChannelFuture channelFuture = serverBootstrap.bind().sync();
        logger.info("server start and listen on {}", channelFuture.channel().localAddress());
        //优雅非阻塞地退出Netty服务端
        channelFuture.channel().closeFuture().addListener((ChannelFutureListener) future -> {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
            logger.info("server is closed");
        });
        //create shutdown hook
        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        },"shutdownThread"));
    }

}

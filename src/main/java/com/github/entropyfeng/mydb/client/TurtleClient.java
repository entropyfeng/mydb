package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.client.conn.ClientThreadFactory;
import com.github.entropyfeng.mydb.client.conn.IClientExecute;
import com.github.entropyfeng.mydb.client.handler.TurtleClientChannelInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

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
    private IClientExecute clientExecute;
    public TurtleClient(String host, Integer port, IClientExecute clientExecute) {
        this.host = host;
        this.port = port;
        this.clientExecute=clientExecute;
    }




    /**
     * create a new thread as a daemon thread to handle client command
     */
    private void start(){

        countDownLatch=new CountDownLatch(1);
        new ClientThreadFactory().newThread(() -> {
            logger.info("client start at host-> {}, port->{}",host,port);
            NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
            client = new Bootstrap();
            client.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(host, port)
                    .option(ChannelOption.RCVBUF_ALLOCATOR, new AdaptiveRecvByteBufAllocator(1 << 10, 1 << 20, 1 << 30))
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .handler(new TurtleClientChannelInitializer(clientExecute));

            doConnect();
        }).start();
    }


    private void doConnect() {
        if (countDownLatch==null){
            countDownLatch = new CountDownLatch(1);
        }
        if (channel != null && channel.isActive()) {
            return;
        }
        ChannelFuture connect = client.connect().awaitUninterruptibly();
        channel=connect.channel();
        countDownLatch.countDown();
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

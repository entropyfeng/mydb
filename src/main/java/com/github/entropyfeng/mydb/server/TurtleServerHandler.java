package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.server.command.ClientRequest;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author entropyfeng
 */
public class TurtleServerHandler extends SimpleChannelInboundHandler<TurtleProtoBuf.ClientCommand> {
    private static final Logger logger = LoggerFactory.getLogger(TurtleServerHandler.class);

    private final ServerDomain serverDomain;



    private static volatile AtomicBoolean interrupted = new AtomicBoolean(false);

    public static ConcurrentHashMap<ChannelId, Channel> clientMap = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<ChannelId, Channel> serverMap = new ConcurrentHashMap<>();

    private static ConcurrentLinkedDeque<TurtleProtoBuf.ClientCommand> blockingDeque = new ConcurrentLinkedDeque<>();

    private ConcurrentHashMap<Long,ClientRequest> requestMap=new ConcurrentHashMap<>();
    public TurtleServerHandler(ServerDomain serverDomain) {

        this.serverDomain = serverDomain;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        logger.info("channel in {} is register", ctx.channel().remoteAddress());
        clientMap.put(ctx.channel().id(), ctx.channel());
    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("channel in {} is active", ctx.channel().remoteAddress());
    }


    /**
     * netty 可以保证同一个客户端的请求顺序发送
     * @param ctx {@link ChannelHandlerContext}
     * @param msg {@link com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf.ClientCommand}
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TurtleProtoBuf.ClientCommand msg) {
        if (msg.getEndAble()){
            serverDomain.accept(requestMap.remove(msg.getRequestId()),ctx.channel());
            return;
        }
        if (msg.getBeginAble()){
          TurtleProtoBuf.RequestHeaderPayload header= msg.getHeader();
          requestMap.put(msg.getRequestId(),new ClientRequest(header,msg.getRequestId()));
        }else {
            requestMap.get(msg.getRequestId()).put(msg.getBody());
        }


    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
        ctx.flush();
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        clientMap.remove(ctx.channel().id());
    }
}

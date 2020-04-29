package com.github.entropyfeng.mydb.server.handler;

import com.github.entropyfeng.mydb.common.exception.TurtleValueElementOutBoundsException;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.server.ResServerHelper;
import com.github.entropyfeng.mydb.server.ServerDomain;
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
public class TurtleServerHandler extends SimpleChannelInboundHandler<ProtoBuf.ClientCommand> {
    private static final Logger logger = LoggerFactory.getLogger(TurtleServerHandler.class);

    private final ServerDomain serverDomain;


    private static volatile AtomicBoolean interrupted = new AtomicBoolean(false);

    public static ConcurrentHashMap<ChannelId, Channel> clientMap = new ConcurrentHashMap<>();

    public static ConcurrentHashMap<ChannelId, Channel> serverMap = new ConcurrentHashMap<>();

    private static ConcurrentLinkedDeque<ProtoBuf.ClientCommand> blockingDeque = new ConcurrentLinkedDeque<>();

    private ConcurrentHashMap<Long, ClientRequest> requestMap = new ConcurrentHashMap<>();

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
     *
     * @param ctx {@link ChannelHandlerContext}
     * @param msg {@link ProtoBuf.ClientCommand}
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtoBuf.ClientCommand msg) {

        if (msg.getEndAble()) {
            ClientRequest clientRequest = requestMap.remove(msg.getRequestId());
            if (clientRequest != null) {
                serverDomain.accept(clientRequest, ctx.channel());
            }
            return;
        }
        if (msg.getBeginAble()) {
            ProtoBuf.RequestHeaderPayload header = msg.getHeader();
            ClientRequest clientRequest=new ClientRequest(header, msg.getRequestId());
            requestMap.put(msg.getRequestId(), clientRequest);
        } else {
            ClientRequest clientRequest = requestMap.get(msg.getRequestId());
            if (clientRequest != null) {
                try {
                    clientRequest.put(msg.getBody());
                }catch (TurtleValueElementOutBoundsException e){
                    logger.info("turtleValue length too long at requestId ->{}",msg.getRequestId());
                    ResServerHelper.writeOuterException(msg.getRequestId(),ctx.channel(), ProtoBuf.ExceptionType.TurtleValueElementOutBoundsException,"");
                    requestMap.remove(msg.getRequestId());
                }
            }
        }
    }

    @Override
    public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
        super.channelWritabilityChanged(ctx);
        ctx.flush();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        logger.info("channel InActive");
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        clientMap.remove(ctx.channel().id());
        logger.info("channel unRegister");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        requestMap.clear();
        logger.info("exceptionCaught");
    }
}

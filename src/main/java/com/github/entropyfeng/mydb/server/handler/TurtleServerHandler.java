package com.github.entropyfeng.mydb.server.handler;

import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.common.exception.TurtleValueElementOutBoundsException;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.server.ResServerHelper;
import com.github.entropyfeng.mydb.server.ServerDomain;
import com.github.entropyfeng.mydb.server.command.ClientRequest;
import com.github.entropyfeng.mydb.server.ms.CommandTransFactory;
import com.github.entropyfeng.mydb.server.ms.CommandTransTask;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;


/**
 * @author entropyfeng
 */
public class TurtleServerHandler extends SimpleChannelInboundHandler<ProtoBuf.TurtleData> {
    private static final Logger logger = LoggerFactory.getLogger(TurtleServerHandler.class);


    /**
     * 所有客户端的集合
     */
    public static ConcurrentHashMap<InetSocketAddress, Channel> clientMap = new ConcurrentHashMap<>();

    public static Set<InetSocketAddress> slaveSet = ConcurrentHashMap.newKeySet();

    private static Thread commandTransThread;

    /**
     * 主从同步队列
     */
    public static ConcurrentLinkedQueue<ClientRequest> masterQueue = new ConcurrentLinkedQueue<>();

    private final ServerDomain serverDomain;

    private ConcurrentHashMap<Long, ClientRequest> requestMap = new ConcurrentHashMap<>();

    private static ConcurrentHashMap<InetSocketAddress, ClientExecute> exeMap = new ConcurrentHashMap<>();

    public TurtleServerHandler(ServerDomain serverDomain) {

        this.serverDomain = serverDomain;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        logger.info("channel in {} is register", ctx.channel().remoteAddress());
        clientMap.put((InetSocketAddress) (ctx.channel().remoteAddress()), ctx.channel());

    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("channel in {} is active", ctx.channel().remoteAddress());
    }


    /**
     * requests sent by the  same clients ,
     * netty can assure these command be handled by server orderly
     *
     * @param ctx {@link ChannelHandlerContext}
     * @param msg {@link ProtoBuf.TurtleData}
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ProtoBuf.TurtleData msg) {

        if (msg.getEndAble()) {
            ClientRequest clientRequest = requestMap.remove(msg.getRequestId());
            if (clientRequest != null) {
                clientRequest = clientRequest.build();
                if (clientRequest != null) {
                    serverDomain.accept(clientRequest);
                }
            }
            return;
        }

        if (msg.getBeginAble()) {
            ProtoBuf.ReqHead header = msg.getReqHead();
            ClientRequest clientRequest = new ClientRequest(header, msg.getRequestId(), ctx.channel());
            requestMap.put(msg.getRequestId(), clientRequest);
        } else {
            ClientRequest clientRequest = requestMap.get(msg.getRequestId());
            if (clientRequest != null) {
                try {
                    clientRequest.put(msg.getDataBody());
                } catch (TurtleValueElementOutBoundsException e) {
                    logger.info("turtleValue length too long at requestId ->{}", msg.getRequestId());
                    ResServerHelper.writeOuterException(msg.getRequestId(), ctx.channel(), ProtoBuf.ExceptionType.TurtleValueElementOutBoundsException, "turtleValueElementOutBound");
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
        logger.info("channel at {} InActive", ctx.channel().remoteAddress());
    }

    @Override
    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception {
        super.channelUnregistered(ctx);
        InetSocketAddress inetSocketAddress = (InetSocketAddress) (ctx.channel().remoteAddress());
        clientMap.remove(inetSocketAddress);
        slaveSet.remove(inetSocketAddress);
        logger.info("channel at {} unRegister", ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        requestMap.clear();
        logger.info("channel at  {} exceptionCaught ->{}", ctx.channel().remoteAddress(), cause);
    }

    public static void registerSlaveServer(String host, Integer port) {
        slaveSet.add(new InetSocketAddress(host, port));
        if (commandTransThread == null) {
            CommandTransTask task = new CommandTransTask(exeMap, masterQueue);
            commandTransThread = new CommandTransFactory().newThread(task);
        }
        ClientExecute clientExecute = new ClientExecute(host, port);
        exeMap.put(new InetSocketAddress(host, port), clientExecute);
    }
}

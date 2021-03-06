package com.github.entropyfeng.mydb.client.handler;


import com.github.entropyfeng.mydb.client.conn.IClientExecute;
import com.github.entropyfeng.mydb.common.Pair;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.*;


/**
 * @author entropyfeng
 */
public class TurtleClientHandler extends SimpleChannelInboundHandler<TurtleData> {

    private static final Logger logger = LoggerFactory.getLogger(TurtleClientHandler.class);

    private  HashMap<Long, Pair<ResHead,Collection<DataBody>>> res = new HashMap<>();
    private IClientExecute clientExecute;
    public TurtleClientHandler(IClientExecute clientExecute){
        this.clientExecute=clientExecute;
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        logger.info("client channel registered at {},to {}!",ctx.channel().localAddress(),ctx.channel().remoteAddress());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("client channel active at {},to {}!",ctx.channel().localAddress(),ctx.channel().remoteAddress());
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TurtleData msg) {

        if (msg.getEndAble()){
            dispatchRes(msg.getRequestId());
            return;
        }
        if (msg.getBeginAble()){
            ResHead head=msg.getResHead();
            res.put(msg.getRequestId(),new Pair<>(head,new ArrayList<>(head.getResSize())));
        }else {
            Pair<ResHead,Collection<DataBody>> pair=res.get(msg.getRequestId());
            if (pair!=null){
                pair.getValue().add(msg.getDataBody());
            }
        }
    }

    /**
     * it means a completed response have been handled
     * @param responseId responseId
     */
    private void dispatchRes(Long responseId) {

        Pair<ResHead,Collection<DataBody>> pair=res.remove(responseId);
        if (pair!=null){
            clientExecute.dispatch(responseId,pair);
        }
    }

}

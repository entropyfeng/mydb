package com.github.entropyfeng.mydb.client.handler;


import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.common.Pair;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.*;


/**
 * @author entropyfeng
 */
public class TurtleClientHandler extends SimpleChannelInboundHandler<TurtleData> {

    private static final Logger logger = LoggerFactory.getLogger(TurtleClientHandler.class);

    private  HashMap<Long, Pair<ResHead,Collection<DataBody>>> res = new HashMap<>();

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        logger.info("channel registered !");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("channel active !");
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
            ClientExecute.resMap.put(responseId, pair);
        }
    }

}

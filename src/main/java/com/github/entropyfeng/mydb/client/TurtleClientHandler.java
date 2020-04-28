package com.github.entropyfeng.mydb.client;


import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.client.conn.TurtleClientChannelFactory;
import com.github.entropyfeng.mydb.core.helper.Pair;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.*;


/**
 * @author entropyfeng
 */
public class TurtleClientHandler extends SimpleChannelInboundHandler<ResponseData> {

    private static final Logger logger = LoggerFactory.getLogger(TurtleClientHandler.class);

    private static HashMap<Long, Pair<ResHead,Collection<ResBody>>> res = new HashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        logger.info("channel active");

    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        TurtleClientChannelFactory.setChannel(null);
        cause.printStackTrace();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResponseData msg) throws Exception {

        if (msg.getEndAble()){
            dispatchRes(msg.getRequestId());
            return;
        }
        if (msg.getBeginAble()){
            ResHead head=msg.getHeader();
            res.put(msg.getRequestId(),new Pair<>(head,new ArrayList<>(head.getResSize())));
        }else {
            Pair<ResHead,Collection<ResBody>> pair=res.get(msg.getRequestId());
            if (pair!=null){
                pair.getValue().add(msg.getBody());
            }
        }
    }

    /**
     * 将完全接受到的集合转移
     *
     * @param responseId responseId
     */
    private void dispatchRes(Long responseId) {

        Pair<ResHead,Collection<ResBody>> pair=res.remove(responseId);
        if (pair!=null){
            ClientExecute.resMap.put(responseId, res.remove(responseId));
        }
    }

}

package com.github.entropyfeng.mydb.client;


import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.client.conn.TurtleClientChannelFactory;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


/**
 * @author entropyfeng
 */
public class TurtleClientHandler extends SimpleChannelInboundHandler<TurtleProtoBuf.ResponseData> {

    private static final Logger logger = LoggerFactory.getLogger(TurtleClientHandler.class);

    private static HashMap<Long, Collection<TurtleProtoBuf.ResponseData>> res = new HashMap<>();

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
    protected void channelRead0(ChannelHandlerContext ctx, TurtleProtoBuf.ResponseData msg) throws Exception {

        //若存在id->TurtleResponse
        //意味着这是一个集合,之前已经处理过responseId对应的节点
        if (res.containsKey(msg.getResponseId())) {
            res.get(msg.getResponseId()).add(msg);
            if (msg.getEndAble()) {
                dispatchResCollection(msg.getResponseId());
            }
            //第一次出现responseId对应的节点
        } else if (msg.getCollectionAble()) {

            ArrayList<TurtleProtoBuf.ResponseData> responseData=new ArrayList<>();
            responseData.add(msg);
            res.put(msg.getResponseId(), responseData);
            if (msg.getEndAble()) {
                dispatchResCollection(msg.getResponseId());
            }
        } else {
            //single response
            ClientExecute.resMap.put(msg.getResponseId(), msg);
        }
    }

    /**
     * 将完全接受到的集合转移
     *
     * @param responseId responseId
     */
    private void dispatchResCollection(Long responseId) {
        //按照sequenceId排序
        //List<TurtleProtoBuf.ResponseData> temp = res.remove(responseId).parallelStream().sorted(Comparator.comparingLong(TurtleProtoBuf.ResponseData::getResponseSequence)).collect(Collectors.toList());

        ClientExecute.collectionResMap.put(responseId, res.remove(responseId));

    }


}

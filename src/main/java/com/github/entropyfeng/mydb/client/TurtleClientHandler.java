package com.github.entropyfeng.mydb.client;


import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import io.netty.channel.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;


/**
 * @author entropyfeng
 */
public class TurtleClientHandler extends SimpleChannelInboundHandler<TurtleProtoBuf.ResponseData> {

    private static final Logger logger= LoggerFactory.getLogger(TurtleClientHandler.class);

    private static  HashMap<Long, TurtleResponse> res=new HashMap<>();
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

        cause.printStackTrace();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TurtleProtoBuf.ResponseData msg) throws Exception {

        //若存在id->TurtleResponse
        //意味着这是一个集合
        if (res.containsKey(msg.getResponseId())){

          TurtleResponse response= res.get(msg.getResponseId());



        }else {
            if (msg.getSuccess()){


                if (msg.getCollection()){

                }
            }
        }
    }

    /**
     * @// TODO: 2020/4/14 未处理 bigInteger
     * @param responseData
     * @return
     */
    private Object parse(TurtleProtoBuf.ResponseData responseData){

        switch (responseData.getType()){

            case VOID:return Void.TYPE;
            case STRING:return responseData.getStringValue();
            case BOOL:return responseData.getBoolValue();
            case DOUBLE:return responseData.getDoubleValue();
            case LONG:return responseData.getLongValue();
            case INTEGER:return responseData.getIntValue();
            case NUMBER_DECIMAL:return new BigDecimal(responseData.getStringValue());
            case NUMBER_INTEGER:return new BigInteger(responseData.getStringValue());
            case TURTLE_VALUE:
            default:throw new UnsupportedOperationException();
        }
    }

    private void handlerFirst(TurtleProtoBuf.ResponseData responseData){
        //为第一个包
        if(responseData.getResponseSequence()==0L){
            if(responseData.getSuccess()){
            }else {
                responseData.getException();
                responseData.getExceptionType();
            }
        }

    }

}

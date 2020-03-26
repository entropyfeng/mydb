package com.github.entropyfeng.mydb.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author entropyfeng
 */
public class ByteToCommandDecoder extends ByteToMessageDecoder {
    /**
     * 固定头部15个字节
     */
    private static final int HEAD_LENGTH = 15;
    private static final Logger logger= LoggerFactory.getLogger(ByteToCommandDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {




    }
}

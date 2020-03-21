package com.github.entropyfeng.mydb.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author entropyfeng
 */
public class ByteToCommandDecoder extends ByteToMessageDecoder {
    /**
     * 固定头部15个字节
     */
    private static final int HEAD_LENGTH = 15;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> list) throws Exception {


        if(in.readableBytes()< HEAD_LENGTH){
            return;
        }
        byte model= in.readByte();
        byte obj=in.readByte();
        byte paraNumber=in.readByte();
        int  paraNameLength=in.readInt();
        long dataLength=in.readLong();

    }
}

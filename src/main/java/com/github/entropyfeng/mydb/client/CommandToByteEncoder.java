package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author entropyfeng
 */
public class CommandToByteEncoder extends MessageToByteEncoder<TurtleProtoBuf.ClientCommand> {

    private static final Logger logger= LoggerFactory.getLogger(CommandToByteEncoder.class);



    @Override
    protected void encode(ChannelHandlerContext ctx, TurtleProtoBuf.ClientCommand msg, ByteBuf out) throws Exception {

    }
}

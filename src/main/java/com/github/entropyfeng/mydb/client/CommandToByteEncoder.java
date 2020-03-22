package com.github.entropyfeng.mydb.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author entropyfeng
 */
public class CommandToByteEncoder extends MessageToByteEncoder<ClientCommand> {

    private static final Logger logger= LoggerFactory.getLogger(CommandToByteEncoder.class);


    @Override
    protected void encode(ChannelHandlerContext ctx, ClientCommand clientCommand, ByteBuf out) throws Exception {

        System.out.println("byte buf->"+out.capacity());
        logger.info("begin encode..");
        byte model= clientCommand.supportModel.toType();
        byte obj=clientCommand.supportObject.toType();
        byte paraNumber=clientCommand.operationParaNumber;
        int  paraNameLength=clientCommand.operationName.length();
        long dataLength=10086L;
        logger.info("end encode");
    }
}

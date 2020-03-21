package com.github.entropyfeng.mydb.net;

import com.github.entropyfeng.mydb.client.ClientCommand;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.Headers;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;

/**
 * @author entropyfeng
 */
public class CommandToByteEncoder extends MessageToByteEncoder<ClientCommand> {

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, ClientCommand clientCommand, ByteBuf byteBuf) throws Exception {

    }
}

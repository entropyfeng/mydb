package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * @author entropyfeng
 */
public class TurtleProtoEncoder extends MessageToByteEncoder<TurtleProtoBuf.ClientCommand> {
    private ProtobufEncoder protobufEncoder=new ProtobufEncoder();

    @Override
    protected void encode(ChannelHandlerContext ctx, TurtleProtoBuf.ClientCommand msg, ByteBuf out) throws Exception {

        System.out.println("encoding           --");

    }

}

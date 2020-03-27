package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.google.protobuf.CodedOutputStream;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import io.netty.handler.codec.protobuf.ProtobufDecoderNano;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufEncoderNano;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.ByteBuffer;

/**
 * @author entropyfeng
 */
public class CommandToByteEncoder extends MessageToByteEncoder<TurtleProtoBuf.ClientCommand> {

    private static final Logger logger= LoggerFactory.getLogger(CommandToByteEncoder.class);


    @Override
    protected void encode(ChannelHandlerContext ctx, TurtleProtoBuf.ClientCommand msg, ByteBuf out) throws Exception {

        ByteBuffer byteBuffer=ByteBuffer.allocate(1024*1024);
        CodedOutputStream codedOutputStream=CodedOutputStream.newInstance(byteBuffer);

    }
}

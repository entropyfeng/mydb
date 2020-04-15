package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

/**
 * @author entropyfeng
 */
public class TurtleClientChannelInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        //出站
        ch.pipeline().addLast("TurtleClientProtoEncoder", new TurtleClientProtoEncoder());
        //入站
        ch.pipeline().addLast("ProtobufVarint32FrameDecoder",new ProtobufVarint32FrameDecoder());
        //入站
        ch.pipeline().addLast("ProtobufDecoder",new ProtobufDecoder(TurtleProtoBuf.ResponseData.getDefaultInstance()));
        //入站
        ch.pipeline().addLast("TurtleClientHandler", new TurtleClientHandler());
    }
}

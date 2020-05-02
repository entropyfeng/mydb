package com.github.entropyfeng.mydb.server.handler;

import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.server.ServerDomain;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

/**
 * @author entropyfeng
 */
public class TurtleServerChannelInitializer extends ChannelInitializer<Channel> {

    private final ServerDomain serverDomain;

    public TurtleServerChannelInitializer(ServerDomain serverDomain) {
        this.serverDomain = serverDomain;
    }

    @Override
    protected void initChannel(Channel ch){
        //入站
        ch.pipeline().addLast("protobufVarint32FrameDecoder",new ProtobufVarint32FrameDecoder());
        //入站
        ch.pipeline().addLast("protobufDecoder",new ProtobufDecoder(ProtoBuf.TurtleData.getDefaultInstance()));
        //入站
        ch.pipeline().addLast("turtleServerHandler",new TurtleServerHandler(serverDomain));
        //出站
        ch.pipeline().addLast("turtleServerProtoEncoder",new TurtleServerProtoEncoder());
    }
}

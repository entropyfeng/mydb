package com.github.entropyfeng.mydb.client.handler;

import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author entropyfeng
 */
public class TurtleClientChannelInitializer extends ChannelInitializer<Channel> {

    public TurtleClientChannelInitializer(ConcurrentHashMap<Long, Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>>> globalRes) {
        this.globalRes = globalRes;
    }

    private ConcurrentHashMap<Long, Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>>> globalRes;


    @Override
    protected void initChannel(Channel ch) {

        //出站
        ch.pipeline().addLast("TurtleClientProtoEncoder", new TurtleClientProtoEncoder());
        //入站
        ch.pipeline().addLast("ProtobufVarint32FrameDecoder",new ProtobufVarint32FrameDecoder());
        //入站
        ch.pipeline().addLast("ProtobufDecoder",new ProtobufDecoder(ProtoBuf.TurtleData.getDefaultInstance()));
        //入站
        ch.pipeline().addLast("TurtleClientHandler", new TurtleClientHandler(globalRes));
    }
}

package com.github.entropyfeng.mydb.client.handler;

import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

/**
 * @author entropyfeng
 */
public class TurtleClientChannelInitializer extends ChannelInitializer<Channel> {

    public TurtleClientChannelInitializer(ClientExecute clientExecute) {
        this.clientExecute=clientExecute;
    }

    private ClientExecute clientExecute;


    @Override
    protected void initChannel(Channel ch) {

        //出站
        ch.pipeline().addLast("TurtleClientProtoEncoder", new TurtleClientProtoEncoder());
        //入站
        ch.pipeline().addLast("ProtobufVarint32FrameDecoder",new ProtobufVarint32FrameDecoder());
        //入站
        ch.pipeline().addLast("ProtobufDecoder",new ProtobufDecoder(ProtoBuf.TurtleData.getDefaultInstance()));
        //入站
        ch.pipeline().addLast("TurtleClientHandler", new TurtleClientHandler(clientExecute));
    }
}

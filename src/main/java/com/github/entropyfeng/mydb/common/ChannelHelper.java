package com.github.entropyfeng.mydb.common;

import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.DataBody;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ReqHead;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.TurtleData;
import io.netty.channel.Channel;

import java.util.ArrayList;

/**
 * @author entropyfeng
 */
public class ChannelHelper {

    public static void writeChannel(Long requestId,Channel channel, ReqHead reqHead, ArrayList<DataBody>bodies)  {
        TurtleData.Builder resBuilder= TurtleData.newBuilder();
        resBuilder.setRequestId(requestId);
        resBuilder.setBeginAble(true);
        resBuilder.setEndAble(false);
        resBuilder.setReqHead(reqHead);
        channel.write(resBuilder.build());


        bodies.forEach(dataBody -> {

            resBuilder.clear();
            resBuilder.setRequestId(requestId);
            resBuilder.setDataBody(dataBody);
            resBuilder.setBeginAble(false);
            resBuilder.setEndAble(false);
            channel.write(resBuilder.build());
        });

        resBuilder.clear();
        resBuilder.setRequestId(requestId);
        resBuilder.setBeginAble(false);
        resBuilder.setEndAble(true);
        channel.writeAndFlush(resBuilder.build());

    }

}

package com.github.entropyfeng.mydb.common;

import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.DataBody;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ReqHead;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.TurtleData;
import com.github.entropyfeng.mydb.common.protobuf.ProtoTurtleHelper;
import com.google.protobuf.ByteString;
import io.netty.channel.Channel;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author entropyfeng
 */
public class ChannelHelper {



    public static void writeChannel(Long requestId,Channel channel, ReqHead reqHead, ArrayList<DataBody>bodies){
        TurtleData.Builder resBuilder= TurtleData.newBuilder();
        resBuilder.setRequestId(requestId);
        resBuilder.setBeginAble(true);
        resBuilder.setEndAble(false);
        resBuilder.setReqHead(reqHead);
        channel.write(resBuilder);


        bodies.forEach(dataBody -> {

            resBuilder.clear();
            resBuilder.setRequestId(requestId);
            resBuilder.setDataBody(dataBody);
            resBuilder.setBeginAble(false);
            resBuilder.setEndAble(false);
            channel.write(resBuilder);
        });

        resBuilder.clear();
        resBuilder.setRequestId(requestId);
        resBuilder.setBeginAble(false);
        resBuilder.setEndAble(true);
        channel.writeAndFlush(resBuilder);

    }


    @SuppressWarnings("all")
    private static void handleSingle(Channel channel, ProtoBuf.TurtleParaType type, Object object, int location, Long requestId, DataBody.Builder bodyBuilder, TurtleData.Builder resBuilder){

        resBuilder.clear();
        resBuilder.setRequestId(requestId);
        resBuilder.setBeginAble(false);
        resBuilder.setEndAble(false);

        bodyBuilder.clear();
        bodyBuilder.setLocation(location);
        switch (type){
            case INTEGER:
                bodyBuilder.setIntValue((Integer)object);
                resBuilder.setDataBody(bodyBuilder.build());
                channel.write(resBuilder.build());
                break;
            case DOUBLE:
                bodyBuilder.setDoubleValue((Double)object);
                resBuilder.setDataBody(bodyBuilder.build());
                channel.write(resBuilder.build());
                break;
            case STRING:
                bodyBuilder.setStringValue((String)object);
                resBuilder.setDataBody(bodyBuilder.build());
                channel.write(resBuilder.build());
                break;
            case LONG:
                bodyBuilder.setLongValue((Long)object);
                resBuilder.setDataBody(bodyBuilder.build());
                channel.write(resBuilder.build());
                break;
            case BOOL:
                bodyBuilder.setBoolValue((Boolean)object);
                resBuilder.setDataBody(bodyBuilder.build());
                channel.write(resBuilder.build());
                break;
            case TURTLE_VALUE:
                bodyBuilder.setTurtleValue(ProtoTurtleHelper.convertToProto((TurtleValue)object));
                resBuilder.setDataBody(bodyBuilder.build());
                channel.write(resBuilder.build());
                break;
            case BYTES:
                bodyBuilder.setBytesValue(ByteString.copyFrom((byte[])object));
                resBuilder.setDataBody(bodyBuilder.build());
                channel.write(resBuilder.build());
                break;
            case NUMBER_INTEGER:
                bodyBuilder.setStringValue(object.toString());
                resBuilder.setDataBody(bodyBuilder.build());
                channel.write(resBuilder.build());
                break;
            case NUMBER_DECIMAL:
                bodyBuilder.setStringValue(((BigDecimal)object).toPlainString());
                resBuilder.setDataBody(bodyBuilder.build());
                channel.write(resBuilder.build());
                break;
            case COLLECTION_DOUBLE:
                ((Collection<Double>)object).forEach(aDouble -> {
                    bodyBuilder.setDoubleValue(aDouble);
                    bodyBuilder.setLocation(location);
                    resBuilder.setDataBody(bodyBuilder.build());
                    channel.write(resBuilder.build());
                });
                channel.flush();
                break;
            case COLLECTION_LONG:
                ((Collection<Long>)object).forEach(aLong -> {
                    bodyBuilder.setLongValue(aLong);
                    bodyBuilder.setLocation(location);
                    resBuilder.setDataBody(bodyBuilder.build());
                    channel.write(resBuilder.build());
                });
                channel.flush();
                break;
            case COLLECTION_INTEGER:
                ((Collection<Integer>)object).forEach(integer -> {
                    bodyBuilder.setIntValue(integer);
                    bodyBuilder.setLocation(location);
                    resBuilder.setDataBody(bodyBuilder.build());
                    channel.write(resBuilder.build());
                });
                channel.flush();
                break;
            case COLLECTION_NUMBER_DECIMAL:
                ((Collection<BigDecimal>)object).forEach(bigDecimal -> {
                    bodyBuilder.setStringValue(bigDecimal.toPlainString());
                    bodyBuilder.setLocation(location);
                    resBuilder.setDataBody(bodyBuilder.build());
                    channel.write(resBuilder.build());
                });
                channel.flush();
                break;

            case COLLECTION_NUMBER_INTEGER:
                ((Collection<BigInteger>)object).forEach(bigInteger -> {
                    bodyBuilder.setLocation(location);
                    bodyBuilder.setStringValue(bigInteger.toString());
                    resBuilder.setDataBody(bodyBuilder.build());
                    channel.write(resBuilder.build());
                });
                channel.flush();
                break;
            case COLLECTION_BYTES:
                ((Collection<byte[]>)object).forEach(bytes -> {
                    bodyBuilder.setLocation(location);
                    bodyBuilder.setBytesValue(ByteString.copyFrom(bytes));
                    resBuilder.setDataBody(bodyBuilder.build());
                    channel.write(resBuilder.build());
                });
                channel.flush();
                break;
            case COLLECTION_STRING:
                ((Collection<String>)object).forEach(s -> {
                    bodyBuilder.setLocation(location);
                    bodyBuilder.setStringValue(s);
                    resBuilder.setDataBody(bodyBuilder.build());
                    channel.write(resBuilder.build());
                });
                channel.flush();
                break;
            case COLLECTION_TURTLE_VALUE:
                ((Collection<TurtleValue>)object).forEach(turtleValue -> {
                    bodyBuilder.setLocation(location);
                    bodyBuilder.setTurtleValue(ProtoTurtleHelper.convertToProto(turtleValue));
                    resBuilder.setDataBody(bodyBuilder.build());
                    channel.write(resBuilder.build());
                });
                channel.flush();
                break;
            default:throw new UnsupportedOperationException("un support para");

        }
    }

}

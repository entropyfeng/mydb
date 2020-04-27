package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.protobuf.ProtoModelHelper;
import com.github.entropyfeng.mydb.common.protobuf.ProtoTurtleHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;
import com.google.protobuf.ByteString;
import io.netty.channel.Channel;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author etropyfeng
 */
public class ClientCommandBuilder {

    private ArrayList<Object> objects = new ArrayList<>();
    private TurtleProtoBuf.RequestHeaderPayload.Builder headBuilder = TurtleProtoBuf.RequestHeaderPayload.newBuilder();

    public ClientCommandBuilder(TurtleModel turtleModel, String operationName) {
        headBuilder.setModel(ProtoModelHelper.convertToProtoTurtleModel(turtleModel));
        headBuilder.setOperationName(operationName);
    }


    public void addStringPara(String string) {

        headBuilder.addKeys(TurtleProtoBuf.TurtleParaType.STRING);
        objects.add(string);
    }

    public void addLongPara(Long longValue) {
        headBuilder.addKeys(TurtleProtoBuf.TurtleParaType.LONG);
        objects.add(longValue);
    }

    public void addDoublePara(Double doubleValue) {
        headBuilder.addKeys(TurtleProtoBuf.TurtleParaType.DOUBLE);
        objects.add(doubleValue);

    }

    public void addIntegerValue(Integer integer) {
        headBuilder.addKeys(TurtleProtoBuf.TurtleParaType.INTEGER);
        objects.add(integer);
    }

    public void addTurtlePara(TurtleValue turtleValue) {

        headBuilder.addKeys(TurtleProtoBuf.TurtleParaType.TURTLE_VALUE);
        objects.add(turtleValue);
    }

    public void addBigIntegerPara(BigInteger bigInteger) {
        headBuilder.addKeys(TurtleProtoBuf.TurtleParaType.NUMBER_INTEGER);
        objects.add(bigInteger);

    }

    public void addBigDecimalPara(BigDecimal bigDecimal) {
        headBuilder.addKeys(TurtleProtoBuf.TurtleParaType.NUMBER_DECIMAL);
        objects.add(bigDecimal);
    }

    public void addTurtleCollectionPara(Collection<TurtleValue> turtleValues) {

        headBuilder.addKeys(TurtleProtoBuf.TurtleParaType.COLLECTION_TURTLE_VALUE);
        objects.add(turtleValues);
    }

    public void addDoubleCollectionPara(Collection<Double> doubles) {
        headBuilder.addKeys(TurtleProtoBuf.TurtleParaType.COLLECTION_DOUBLE);
        objects.add(doubles);
    }

    public void addStringCollectionPara(Collection<String> strings){
        headBuilder.addKeys(TurtleProtoBuf.TurtleParaType.COLLECTION_STRING);
        objects.add(strings);
    }

    public void setModifyAble(boolean modifyAble) {

        headBuilder.setModify(modifyAble);
    }


    public ClientCommandBuilder build() {
        objects.trimToSize();
        return this;
    }


    public void writeChannel(Channel channel, Long requestId) {
        TurtleProtoBuf.ClientCommand.Builder resBuilder = TurtleProtoBuf.ClientCommand.newBuilder();

        //header
        resBuilder.setBeginAble(true);
        resBuilder.setHeader(headBuilder.build());
        resBuilder.setRequestId(requestId);
        channel.writeAndFlush(resBuilder.build());



        //body,if body is empty ,skip it
        TurtleProtoBuf.RequestBodyPayload.Builder bodyBuilder = TurtleProtoBuf.RequestBodyPayload.newBuilder();
        List<TurtleProtoBuf.TurtleParaType> list = headBuilder.getKeysList();

        for (int i = 0; i < list.size(); i++) {
            handleSingle(channel,list.get(i),objects.get(i),i,requestId,bodyBuilder,resBuilder);
        }

        //end
        resBuilder.clear();
        resBuilder.setEndAble(true);
        resBuilder.setRequestId(requestId);
        channel.writeAndFlush(resBuilder.build());

    }


    public static void handleSingle(Channel channel, TurtleProtoBuf.TurtleParaType type, Object object, int location, Long requestId, TurtleProtoBuf.RequestBodyPayload.Builder bodyBuilder, TurtleProtoBuf.ClientCommand.Builder resBuilder){
        resBuilder.clear();
        resBuilder.setRequestId(requestId);
        resBuilder.setBeginAble(false);
        resBuilder.setEndAble(false);

        bodyBuilder.clear();
        bodyBuilder.setLocation(location);
        switch (type){
            case INTEGER:
                bodyBuilder.setIntValue((Integer)object);
                resBuilder.setBody(bodyBuilder.build());
                channel.write(resBuilder.build());
                break;
            case DOUBLE:
                bodyBuilder.setDoubleValue((Double)object);
                resBuilder.setBody(bodyBuilder.build());
                channel.write(resBuilder.build());
                break;
            case STRING:
                bodyBuilder.setStringValue((String)object);
                resBuilder.setBody(bodyBuilder.build());
                channel.write(resBuilder.build());
                break;
            case LONG:
                bodyBuilder.setLongValue((Long)object);
                resBuilder.setBody(bodyBuilder.build());
                channel.write(resBuilder.build());
                break;
            case BOOL:
                bodyBuilder.setBoolValue((Boolean)object);
                resBuilder.setBody(bodyBuilder.build());
                channel.write(resBuilder.build());
                break;
            case TURTLE_VALUE:
                bodyBuilder.setTurtleValue(ProtoTurtleHelper.convertToProto((TurtleValue)object));
                resBuilder.setBody(bodyBuilder.build());
                channel.write(resBuilder.build());
                break;
            case BYTES:
                bodyBuilder.setBytesValue(ByteString.copyFrom((byte[])object));
                resBuilder.setBody(bodyBuilder.build());
                channel.write(resBuilder.build());
                break;
            case NUMBER_INTEGER:
                bodyBuilder.setStringValue(object.toString());
                resBuilder.setBody(bodyBuilder.build());
                channel.write(resBuilder.build());
                break;
            case NUMBER_DECIMAL:
                bodyBuilder.setStringValue(((BigDecimal)object).toPlainString());
                resBuilder.setBody(bodyBuilder.build());
                channel.write(resBuilder.build());
                break;
            case COLLECTION_DOUBLE:
                ((Collection<Double>)object).forEach(aDouble -> {
                    bodyBuilder.setDoubleValue(aDouble);
                    bodyBuilder.setLocation(location);
                    resBuilder.setBody(bodyBuilder.build());
                    channel.write(resBuilder.build());
                });
                channel.flush();
                break;
            case COLLECTION_LONG:
                ((Collection<Long>)object).forEach(aLong -> {
                    bodyBuilder.setLongValue(aLong);
                    bodyBuilder.setLocation(location);
                    resBuilder.setBody(bodyBuilder.build());
                    channel.write(resBuilder.build());
                });
                channel.flush();
                break;
            case COLLECTION_INTEGER:
                ((Collection<Integer>)object).forEach(integer -> {
                    bodyBuilder.setIntValue(integer);
                    bodyBuilder.setLocation(location);
                    resBuilder.setBody(bodyBuilder.build());
                    channel.write(resBuilder.build());
                });
                channel.flush();
                break;
            case COLLECTION_NUMBER_DECIMAL:
                ((Collection<BigDecimal>)object).forEach(bigDecimal -> {
                    bodyBuilder.setStringValue(bigDecimal.toPlainString());
                    bodyBuilder.setLocation(location);
                    resBuilder.setBody(bodyBuilder.build());
                    channel.write(resBuilder.build());
                });
                channel.flush();
                break;

            case COLLECTION_NUMBER_INTEGER:
                ((Collection<BigInteger>)object).forEach(bigInteger -> {
                    bodyBuilder.setLocation(location);
                    bodyBuilder.setStringValue(bigInteger.toString());
                    resBuilder.setBody(bodyBuilder.build());
                    channel.write(resBuilder.build());
                });
                channel.flush();
                break;
            case COLLECTION_BYTES:
                ((Collection<byte[]>)object).forEach(bytes -> {
                    bodyBuilder.setLocation(location);
                    bodyBuilder.setBytesValue(ByteString.copyFrom(bytes));
                    resBuilder.setBody(bodyBuilder.build());
                    channel.write(resBuilder.build());
                });
                channel.flush();
                break;
            case COLLECTION_STRING:
                ((Collection<String>)object).forEach(s -> {
                    bodyBuilder.setLocation(location);
                    bodyBuilder.setStringValue(s);
                    resBuilder.setBody(bodyBuilder.build());
                    channel.write(resBuilder.build());
                });
                channel.flush();
                break;
            case COLLECTION_TURTLE_VALUE:
                ((Collection<TurtleValue>)object).forEach(turtleValue -> {
                    bodyBuilder.setLocation(location);
                    bodyBuilder.setTurtleValue(ProtoTurtleHelper.convertToProto(turtleValue));
                    resBuilder.setBody(bodyBuilder.build());
                    channel.write(resBuilder.build());
                });
                channel.flush();
                break;
            default:throw new UnsupportedOperationException("un support para");

        }
    }


}

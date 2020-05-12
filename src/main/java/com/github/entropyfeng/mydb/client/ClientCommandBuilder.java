package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.common.protobuf.ProtoModelHelper;
import com.github.entropyfeng.mydb.common.protobuf.ProtoTurtleHelper;
import com.github.entropyfeng.mydb.common.TurtleValue;
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
    private ProtoBuf.ReqHead.Builder headBuilder = ProtoBuf.ReqHead.newBuilder();
    private ArrayList<Class<?>> classesTypes=new ArrayList<>();

    public ClientCommandBuilder(TurtleModel turtleModel, String operationName) {
        headBuilder.setModel(ProtoModelHelper.convertToProtoTurtleModel(turtleModel));
        headBuilder.setOperationName(operationName);
    }


    public void addStringPara(String string) {

        headBuilder.addKeys(ProtoBuf.TurtleParaType.STRING);
        classesTypes.add(String.class);
        objects.add(string);
    }

    public void addLongPara(Long longValue) {
        headBuilder.addKeys(ProtoBuf.TurtleParaType.LONG);
        classesTypes.add(Long.class);
        objects.add(longValue);
    }

    public void addDoublePara(Double doubleValue) {
        headBuilder.addKeys(ProtoBuf.TurtleParaType.DOUBLE);
        classesTypes.add(Double.class);
        objects.add(doubleValue);

    }

    public void addIntegerPara(Integer integer) {
        headBuilder.addKeys(ProtoBuf.TurtleParaType.INTEGER);
        classesTypes.add(Integer.class);
        objects.add(integer);
    }

    public void addTurtlePara(TurtleValue turtleValue) {

        headBuilder.addKeys(ProtoBuf.TurtleParaType.TURTLE_VALUE);
        classesTypes.add(TurtleValue.class);
        objects.add(turtleValue);
    }

    public void addBigIntegerPara(BigInteger bigInteger) {
        headBuilder.addKeys(ProtoBuf.TurtleParaType.NUMBER_INTEGER);
        objects.add(bigInteger);
        classesTypes.add(BigInteger.class);

    }

    public void addBigDecimalPara(BigDecimal bigDecimal) {
        headBuilder.addKeys(ProtoBuf.TurtleParaType.NUMBER_DECIMAL);
        objects.add(bigDecimal);
        classesTypes.add(BigDecimal.class);
    }

    public void addTurtleCollectionPara(Collection<TurtleValue> turtleValues) {

        headBuilder.addKeys(ProtoBuf.TurtleParaType.COLLECTION_TURTLE_VALUE);
        objects.add(turtleValues);
        classesTypes.add(Collection.class);
    }

    public void addDoubleCollectionPara(Collection<Double> doubles) {
        headBuilder.addKeys(ProtoBuf.TurtleParaType.COLLECTION_DOUBLE);
        objects.add(doubles);
        classesTypes.add(Collection.class);
    }

    @SuppressWarnings("unused")
    public void addStringCollectionPara(Collection<String> strings){
        headBuilder.addKeys(ProtoBuf.TurtleParaType.COLLECTION_STRING);
        objects.add(strings);
        classesTypes.add(Collection.class);
    }

    public void setModifyAble(boolean modifyAble) {

        headBuilder.setModify(modifyAble);
    }



    public void writeChannel(Channel channel,Long requestId) {


        ProtoBuf.TurtleData.Builder resBuilder = ProtoBuf.TurtleData.newBuilder();

        //header
        resBuilder.setBeginAble(true);

        resBuilder.setReqHead(headBuilder.build());
        resBuilder.setRequestId(requestId);
        channel.writeAndFlush(resBuilder.build());


        //body,if body is empty ,skip it
        ProtoBuf.DataBody.Builder bodyBuilder = ProtoBuf.DataBody.newBuilder();
        List<ProtoBuf.TurtleParaType> list = headBuilder.getKeysList();

        for (int i = 0; i < list.size(); i++) {
            handleSingle(channel,list.get(i),objects.get(i),i,requestId,bodyBuilder,resBuilder);
        }

        //end
        resBuilder.clear();
        resBuilder.setEndAble(true);
        resBuilder.setRequestId(requestId);
        channel.writeAndFlush(resBuilder.build());

    }



    @SuppressWarnings("all")
    private static void handleSingle(Channel channel, ProtoBuf.TurtleParaType type, Object object, int location, Long requestId, ProtoBuf.DataBody.Builder bodyBuilder, ProtoBuf.TurtleData.Builder resBuilder){

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

package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.ChannelHelper;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.DataBody;
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

    public ClientCommandBuilder(TurtleModel turtleModel, String operationName) {
        headBuilder.setModel(ProtoModelHelper.convertToProtoTurtleModel(turtleModel));
        headBuilder.setOperationName(operationName);
    }


    public void addStringPara(String string) {

        headBuilder.addKeys(ProtoBuf.TurtleParaType.STRING);
        objects.add(string);
    }

    public void addLongPara(Long longValue) {
        headBuilder.addKeys(ProtoBuf.TurtleParaType.LONG);
        objects.add(longValue);
    }

    public void addDoublePara(Double doubleValue) {
        headBuilder.addKeys(ProtoBuf.TurtleParaType.DOUBLE);
        objects.add(doubleValue);

    }

    public void addIntegerPara(Integer integer) {
        headBuilder.addKeys(ProtoBuf.TurtleParaType.INTEGER);
        objects.add(integer);
    }

    public void addTurtlePara(TurtleValue turtleValue) {

        headBuilder.addKeys(ProtoBuf.TurtleParaType.TURTLE_VALUE);
        objects.add(turtleValue);
    }

    public void addBigIntegerPara(BigInteger bigInteger) {
        headBuilder.addKeys(ProtoBuf.TurtleParaType.NUMBER_INTEGER);
        objects.add(bigInteger);

    }

    public void addBigDecimalPara(BigDecimal bigDecimal) {
        headBuilder.addKeys(ProtoBuf.TurtleParaType.NUMBER_DECIMAL);
        objects.add(bigDecimal);
    }

    public void addTurtleCollectionPara(Collection<TurtleValue> turtleValues) {

        headBuilder.addKeys(ProtoBuf.TurtleParaType.COLLECTION_TURTLE_VALUE);
        objects.add(turtleValues);
    }

    public void addDoubleCollectionPara(Collection<Double> doubles) {
        headBuilder.addKeys(ProtoBuf.TurtleParaType.COLLECTION_DOUBLE);
        objects.add(doubles);
    }

    @SuppressWarnings("unused")
    public void addStringCollectionPara(Collection<String> strings){
        headBuilder.addKeys(ProtoBuf.TurtleParaType.COLLECTION_STRING);
        objects.add(strings);
    }

    public void setModifyAble(boolean modifyAble) {

        headBuilder.setModify(modifyAble);
    }


    public void writeChanelX(Channel channel,Long requestId){

      ArrayList<DataBody> bodies=  constructBodies();
      ChannelHelper.writeChannel(requestId,channel,headBuilder.build(),bodies);

    }

    public ArrayList<DataBody> constructBodies(){

        ArrayList<DataBody> bodies=new ArrayList<>();
        if (!objects.isEmpty()){
            DataBody.Builder bodyBuilder=DataBody.newBuilder();
            List<ProtoBuf.TurtleParaType> list = headBuilder.getKeysList();

            for (int i = 0; i < objects.size(); i++) {

                handleSingle(list.get(i),objects.get(i),i,bodyBuilder,bodies);
            }
        }
  return bodies;

    }




    @SuppressWarnings("all")
    public static void handleSingle(ProtoBuf.TurtleParaType type, Object object, int location, DataBody.Builder bodyBuilder, ArrayList<DataBody> bodies){

        bodyBuilder.clear();
        switch (type){
            case INTEGER:
                bodyBuilder.setIntValue((Integer)object);
                bodies.add(bodyBuilder.build());
                break;
            case DOUBLE:
                bodyBuilder.setDoubleValue((Double)object);
                bodies.add(bodyBuilder.build());
                break;
            case STRING:
                bodyBuilder.setStringValue((String)object);
                bodies.add(bodyBuilder.build());
                break;
            case LONG:
                bodyBuilder.setLongValue((Long)object);
                bodies.add(bodyBuilder.build());
                break;
            case BOOL:
                bodyBuilder.setBoolValue((Boolean)object);
                bodies.add(bodyBuilder.build());
                break;
            case TURTLE_VALUE:
                bodyBuilder.setTurtleValue(ProtoTurtleHelper.convertToProto((TurtleValue)object));
                bodies.add(bodyBuilder.build());
                break;
            case BYTES:
                bodyBuilder.setBytesValue(ByteString.copyFrom((byte[])object));
                bodies.add(bodyBuilder.build());
                break;
            case NUMBER_INTEGER:
                bodyBuilder.setStringValue(object.toString());
                bodies.add(bodyBuilder.build());
                break;
            case NUMBER_DECIMAL:
                bodyBuilder.setStringValue(((BigDecimal)object).toPlainString());
                bodies.add(bodyBuilder.build());
                break;
            case COLLECTION_DOUBLE:
                ((Collection<Double>)object).forEach(aDouble -> {
                    bodyBuilder.setDoubleValue(aDouble);
                    bodyBuilder.setLocation(location);
                    bodies.add(bodyBuilder.build());
                });
                break;
            case COLLECTION_LONG:
                ((Collection<Long>)object).forEach(aLong -> {
                    bodyBuilder.setLongValue(aLong);
                    bodyBuilder.setLocation(location);
                    bodies.add(bodyBuilder.build());
                });
                break;
            case COLLECTION_INTEGER:

                ((Collection<Integer>)object).forEach(integer -> {
                    bodyBuilder.setIntValue(integer);
                    bodyBuilder.setLocation(location);
                    bodies.add(bodyBuilder.build());
                });
                break;
            case COLLECTION_NUMBER_DECIMAL:
                ((Collection<BigDecimal>)object).forEach(bigDecimal -> {
                    bodyBuilder.setStringValue(bigDecimal.toPlainString());
                    bodyBuilder.setLocation(location);
                    bodies.add(bodyBuilder.build());
                });
                break;

            case COLLECTION_NUMBER_INTEGER:
                ((Collection<BigInteger>)object).forEach(bigInteger -> {
                    bodyBuilder.setLocation(location);
                    bodyBuilder.setStringValue(bigInteger.toString());
                    bodies.add(bodyBuilder.build());
                });
                break;
            case COLLECTION_BYTES:
                ((Collection<byte[]>)object).forEach(bytes -> {
                    bodyBuilder.setLocation(location);
                    bodyBuilder.setBytesValue(ByteString.copyFrom(bytes));
                    bodies.add(bodyBuilder.build());
                });
                break;
            case COLLECTION_STRING:
                ((Collection<String>)object).forEach(s -> {
                    bodyBuilder.setLocation(location);
                    bodyBuilder.setStringValue(s);
                    bodies.add(bodyBuilder.build());
                });

                break;
            case COLLECTION_TURTLE_VALUE:
                ((Collection<TurtleValue>)object).forEach(turtleValue -> {
                    bodyBuilder.setLocation(location);
                    bodyBuilder.setTurtleValue(ProtoTurtleHelper.convertToProto(turtleValue));
                    bodies.add(bodyBuilder.build());
                });
                break;
            default:throw new UnsupportedOperationException("un support para");

        }
    }


}

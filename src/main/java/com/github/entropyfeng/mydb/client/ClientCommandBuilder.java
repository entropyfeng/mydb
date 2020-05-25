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

        
    }


    public void writeChannel(Channel channel,Long requestId) {


        ProtoBuf.TurtleData.Builder resBuilder = ProtoBuf.TurtleData.newBuilder();

        //header
        resBuilder.setBeginAble(true);

        resBuilder.setReqHead(headBuilder.build());
        resBuilder.setRequestId(requestId);
        channel.write(resBuilder.build());


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





}

package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.protobuf.ProtoCommonValueHelper;
import com.github.entropyfeng.mydb.common.protobuf.ProtoModelHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;

/**
 * @author etropyfeng
 */
public class ClientCommandBuilder {

    /**
     * 很可能导致内存泄漏
     */
    public static ThreadLocal<TurtleProtoBuf.ClientCommand.Builder> threadLocal=ThreadLocal.withInitial(TurtleProtoBuf.ClientCommand::newBuilder);

    private TurtleProtoBuf.ClientCommand.Builder builder ;
    public ClientCommandBuilder(TurtleModel turtleModel, String operationName) {

        this.builder=threadLocal.get();
        builder.clear();
        builder.setModel(ProtoModelHelper.convertToProtoTurtleModel(turtleModel));
        builder.setOperationName(operationName);
    }


    public void addStringPara(String string){
        builder.addKeys(TurtleProtoBuf.TurtleParaType.STRING);
        builder.addValues(ProtoCommonValueHelper.stringValue(string));

    }

    public void addLongPara(Long longValue){
        builder.addKeys(TurtleProtoBuf.TurtleParaType.LONG);
        builder.addValues(ProtoCommonValueHelper.longValue(longValue));
    }
    public void addDoublePara(Double doubleValue){
        builder.addKeys(TurtleProtoBuf.TurtleParaType.DOUBLE);
        builder.addValues(ProtoCommonValueHelper.doubleValue(doubleValue));
    }
    public void addIntegerValue(Integer integer){
        builder.addKeys(TurtleProtoBuf.TurtleParaType.INTEGER);
        builder.addValues(ProtoCommonValueHelper.integerValue(integer));
    }
    public void addTurtlePara(TurtleValue turtleValue) {

        builder.addKeys(TurtleProtoBuf.TurtleParaType.TURTLE_VALUE);
        builder.addValues(ProtoCommonValueHelper.turtleValue(turtleValue));
    }

    public void addBigIntegerPara(BigInteger bigInteger){
        builder.addKeys(TurtleProtoBuf.TurtleParaType.NUMBER_INTEGER);
        builder.addValues(ProtoCommonValueHelper.stringValue(bigInteger.toString()));
    }
    public void addBigDecimalPara(BigDecimal bigDecimal){
        builder.addKeys(TurtleProtoBuf.TurtleParaType.NUMBER_DECIMAL);
        builder.addValues(ProtoCommonValueHelper.stringValue(bigDecimal.toPlainString()));
    }
    public void addTurtleCollectionPara(Collection<TurtleValue> turtleValues) {

        builder.addKeys(TurtleProtoBuf.TurtleParaType.COLLECTION);
        builder.addValues(ProtoCommonValueHelper.turtleValueCollection(turtleValues));
    }

    public void addDoubleCollectionPara(Collection<Double> doubles){
        builder.addKeys(TurtleProtoBuf.TurtleParaType.COLLECTION);
        builder.addValues(ProtoCommonValueHelper.doublesCollection(doubles));
    }
    public void setModifyAble(boolean modifyAble) {

        builder.setModify(modifyAble);
    }

    public TurtleProtoBuf.ClientCommand build() {
        return builder.build();
    }

}

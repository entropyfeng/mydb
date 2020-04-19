package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.protobuf.ProtoCommonValueHelper;
import com.github.entropyfeng.mydb.common.protobuf.ProtoModelHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.obj.TurtleValue;

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


    public ClientCommandBuilder addStringPara(String string){
        builder.addKeys(TurtleProtoBuf.TurtleParaType.STRING);
        builder.addValues(ProtoCommonValueHelper.stringValue(string));
        return this;

    }

    public ClientCommandBuilder addLongPara(Long longValue){
        builder.addKeys(TurtleProtoBuf.TurtleParaType.LONG);
        builder.addValues(ProtoCommonValueHelper.longValue(longValue));
        return this;
    }
    public ClientCommandBuilder addDoublePara(Double doubleValue){
        builder.addKeys(TurtleProtoBuf.TurtleParaType.DOUBLE);
        builder.addValues(ProtoCommonValueHelper.doubleValue(doubleValue));
        return this;
    }
    public ClientCommandBuilder addIntegerValue(Integer integer){
        builder.addKeys(TurtleProtoBuf.TurtleParaType.INTEGER);
        builder.addValues(ProtoCommonValueHelper.integerValue(integer));
        return this;
    }
    public ClientCommandBuilder addTurtlePara(TurtleValue turtleValue) {

        builder.addKeys(TurtleProtoBuf.TurtleParaType.TURTLE_VALUE);
        builder.addValues(ProtoCommonValueHelper.turtleValue(turtleValue));
        return this;
    }

    public ClientCommandBuilder addBigIntegerPara(BigInteger bigInteger){
        builder.addKeys(TurtleProtoBuf.TurtleParaType.NUMBER_INTEGER);
        builder.addValues(ProtoCommonValueHelper.stringValue(bigInteger.toString()));
        return this;
    }
    public ClientCommandBuilder addBigDecimalPara(BigDecimal bigDecimal){
        builder.addKeys(TurtleProtoBuf.TurtleParaType.NUMBER_DECIMAL);
        builder.addValues(ProtoCommonValueHelper.stringValue(bigDecimal.toPlainString()));
        return this;
    }
    public ClientCommandBuilder addTurtleCollectionPara(Collection<TurtleValue> turtleValues) {

        builder.addKeys(TurtleProtoBuf.TurtleParaType.COLLECTION);
        builder.addValues(ProtoCommonValueHelper.turtleValueCollection(turtleValues));
        return this;
    }

    public ClientCommandBuilder setModifyAble(boolean modifyAble) {

        builder.setModify(true);
        return this;
    }

    public TurtleProtoBuf.ClientCommand build() {
        return builder.build();
    }

}

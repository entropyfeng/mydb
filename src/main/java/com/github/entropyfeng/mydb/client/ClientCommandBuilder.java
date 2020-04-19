package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.TurtleParaType;
import com.github.entropyfeng.mydb.common.protobuf.CommonValueHelper;
import com.github.entropyfeng.mydb.common.protobuf.ProtoModelHelper;
import com.github.entropyfeng.mydb.common.protobuf.ProtoParaHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.obj.TurtleValue;

import java.util.Collection;

/**
 * @author etropyfeng
 */
public class ClientCommandBuilder {


    private TurtleProtoBuf.ClientCommand.Builder builder = TurtleProtoBuf.ClientCommand.newBuilder();

    public ClientCommandBuilder(TurtleModel turtleModel, String operationName) {

        builder.setModel(ProtoModelHelper.convertToProtoTurtleModel(turtleModel));
        builder.setOperationName(operationName);
    }

    public ClientCommandBuilder addTurtlePara(TurtleValue turtleValue) {

        builder.addKeys(TurtleProtoBuf.TurtleParaType.TURTLE_VALUE);
        builder.addValues(CommonValueHelper.turtleValue(turtleValue));

        return this;
    }

    public ClientCommandBuilder addTurtleCollectionPara(Collection<TurtleValue> turtleValues) {
        
        builder.addKeys(TurtleProtoBuf.TurtleParaType.COLLECTION);
        builder.addValues(CommonValueHelper.turtleValueCollection(turtleValues));
        return this;
    }


    public ClientCommandBuilder addPara(TurtleParaType paraType, Object value) {
        TurtleProtoBuf.TurtleParaType type = ProtoParaHelper.convertToProtoParaType(paraType);
        builder.addKeys(type).addValues(ProtoParaHelper.convertToCommonValue(paraType, value));
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

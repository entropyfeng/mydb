package com.github.entropyfeng.mydb.common.protobuf;

import com.github.entropyfeng.mydb.common.TurtleModel;

/**
 * @author entropyfeng
 */
public class ProtoModelHelper {

    public static ProtoBuf.TurtleModel convertToProtoTurtleModel(TurtleModel turtleModel){
        switch (turtleModel){
            case SET:return ProtoBuf.TurtleModel.SET;
            case HASH:return ProtoBuf.TurtleModel.HASH;
            case LIST:return ProtoBuf.TurtleModel.LIST;
            case ZSET:return ProtoBuf.TurtleModel.ZSET;
            case ADMIN:return ProtoBuf.TurtleModel.ADMIN;
            case VALUE:return ProtoBuf.TurtleModel.VALUE;
            default:throw new UnsupportedOperationException("unSupport model"+turtleModel);
        }
    }
    public static TurtleModel convertToTurtleModel(ProtoBuf.TurtleModel protoTurtleModel){
        switch (protoTurtleModel){
            case VALUE:return TurtleModel.VALUE;
            case ADMIN:return TurtleModel.ADMIN;
            case ZSET:return TurtleModel.ZSET;
            case LIST:return TurtleModel.LIST;
            case HASH:return TurtleModel.HASH;
            case SET:return TurtleModel.SET;
            default:throw new UnsupportedOperationException("unSupport model "+protoTurtleModel);
        }

    }
}

package com.github.entropyfeng.mydb.common.protobuf;

import com.github.entropyfeng.mydb.common.TurtleModel;

import java.util.List;

/**
 * @author entropyfeng
 */
public class ProtoModelHelper {

    public static TurtleProtoBuf.TurtleModel convertToProtoTurtleModel(TurtleModel turtleModel){
        switch (turtleModel){
            case SET:return TurtleProtoBuf.TurtleModel.SET;
            case HASH:return TurtleProtoBuf.TurtleModel.HASH;
            case LIST:return TurtleProtoBuf.TurtleModel.LIST;
            case ZSET:return TurtleProtoBuf.TurtleModel.ZSET;
            case ADMIN:return TurtleProtoBuf.TurtleModel.ADMIN;
            case VALUE:return TurtleProtoBuf.TurtleModel.VALUE;
            default:throw new UnsupportedOperationException("unSupport model"+turtleModel);
        }
    }
    public static TurtleModel convertToTurtleModel(TurtleProtoBuf.TurtleModel protoTurtleModel){
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

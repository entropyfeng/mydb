package com.github.entropyfeng.mydb.client.conn;

import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.protobuf.ProtoExceptionHelper;
import com.github.entropyfeng.mydb.common.protobuf.ProtoTurtleHelper;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;

import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResBody;
import static com.github.entropyfeng.mydb.common.protobuf.ProtoBuf.ResHead;

/**
 * @author entropyfeng
 */
public class ClientResHelper {

    public static @Nullable TurtleValue turtleValueRes(Pair<ResHead, Collection<ResBody>> pair) {
        ResHead resHead = pair.getKey();
        if (resHead.getSuccess()) {
            ArrayList<ResBody> collection = ((ArrayList<ResBody>) pair.getValue());
            if (collection.isEmpty()) {
                return null;
            } else {
                return ProtoTurtleHelper.convertToDbTurtle(collection.get(0).getTurtleValue());
            }

        } else {
            ProtoExceptionHelper.handler(resHead.getInnerExceptionType(), resHead.getInnerException());
            return null;
        }
    }

    public static @Nullable Integer integerRes(Pair<ResHead, Collection<ResBody>> pair) {
        ResHead resHead = pair.getKey();
        if (resHead.getSuccess()) {
            ArrayList<ResBody> collection = ((ArrayList<ResBody>) pair.getValue());
            if (collection.isEmpty()){
                return null;
            }else {
                return collection.get(0).getIntValue();
            }

        } else {
            ProtoExceptionHelper.handler(resHead.getInnerExceptionType(), resHead.getInnerException());
            return null;
        }
    }


    public static @Nullable Boolean boolRes(Pair<ResHead, Collection<ResBody>> pair) {
        ResHead resHead = pair.getKey();
        if (resHead.getSuccess()) {
            ArrayList<ResBody> collection = ((ArrayList<ResBody>) pair.getValue());
            if (collection.isEmpty()){
                return null;
            }else {
                return collection.get(0).getBoolValue();
            }

        } else {
            ProtoExceptionHelper.handler(resHead.getInnerExceptionType(), resHead.getInnerException());
            return null;
        }
    }

    public static void voidRes(Pair<ResHead, Collection<ResBody>> pair) {
        ResHead resHead = pair.getKey();
        if (!resHead.getSuccess()) {
            ProtoExceptionHelper.handler(resHead.getInnerExceptionType(), resHead.getInnerException());
        }
    }

    public static @Nullable Collection<TurtleValue> turtleCollection(Pair<ResHead, Collection<ResBody>> pair) {

        ResHead resHead = pair.getKey();
        if (resHead.getSuccess()) {
            ArrayList<TurtleValue> res = new ArrayList<>(resHead.getResSize());
            pair.getValue().forEach(resBody -> res.add(ProtoTurtleHelper.convertToDbTurtle(resBody.getTurtleValue())));
            if (res.size() == resHead.getResSize()) {
                return res;
            }
        } else {
            ProtoExceptionHelper.handler(resHead.getInnerExceptionType(), resHead.getInnerException());
        }
        return null;
    }

    public static @Nullable Collection<String> stringCollection(Pair<ResHead, Collection<ResBody>> pair) {

        ResHead resHead = pair.getKey();
        if (resHead.getSuccess()) {
            ArrayList<String> res = new ArrayList<>(resHead.getResSize());
            pair.getValue().forEach(resBody -> res.add(resBody.getStringValue()));
            if (res.size() == resHead.getResSize()) {
                return res;
            }
        } else {
            ProtoExceptionHelper.handler(resHead.getInnerExceptionType(), resHead.getInnerException());
        }
        return null;
    }

    public static @Nullable Collection<Pair<String, TurtleValue>> stringTurtleCollection(Pair<ResHead, Collection<ResBody>> pair) {

        ResHead resHead = pair.getKey();
        if (resHead.getSuccess()) {
            ArrayList<Pair<String, TurtleValue>> res = new ArrayList<>(resHead.getResSize());
            pair.getValue().forEach(resBody -> {
                String key = resBody.getStringValue();
                TurtleValue value = ProtoTurtleHelper.convertToDbTurtle(resBody.getStringTurtleValueEntry().getValue());
                res.add(new Pair<>(key, value));

            });
            if (res.size() == resHead.getResSize()) {
                return res;
            }
        } else {
            ProtoExceptionHelper.handler(resHead.getInnerExceptionType(), resHead.getInnerException());
        }
        return null;
    }
}

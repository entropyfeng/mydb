package com.github.entropyfeng.mydb.client.conn;

import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.common.protobuf.ProtoExceptionHelper;
import com.github.entropyfeng.mydb.common.protobuf.ProtoTurtleHelper;
import com.github.entropyfeng.mydb.core.TurtleValue;
import com.github.entropyfeng.mydb.core.helper.Pair;
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
            ProtoBuf.TurtleValue turtleValue = ((ArrayList<ResBody>) pair.getValue()).get(0).getTurtleValue();
            return ProtoTurtleHelper.convertToTurtleValue(turtleValue);
        } else {
            ProtoExceptionHelper.handler(resHead.getInnerExceptionType(), resHead.getInnerException());
            return null;
        }
    }
    public static @Nullable Integer integerRes(Pair<ResHead, Collection<ResBody>> pair) {
        ResHead resHead = pair.getKey();
        if (resHead.getSuccess()) {
            return ((ArrayList<ResBody>) pair.getValue()).get(0).getIntValue();
        } else {
            ProtoExceptionHelper.handler(resHead.getInnerExceptionType(), resHead.getInnerException());
            return null;
        }
    }


    public static @Nullable Boolean boolRes(Pair<ResHead, Collection<ResBody>> pair) {
        ResHead resHead = pair.getKey();
        if (resHead.getSuccess()) {
            return ((ArrayList<ResBody>) pair.getValue()).get(0).getBoolValue();
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
            pair.getValue().forEach(resBody -> res.add(ProtoTurtleHelper.convertToTurtleValue(resBody.getTurtleValue())));
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
                TurtleValue value = ProtoTurtleHelper.convertToTurtleValue(resBody.getStringTurtleValueEntry().getValue());
                 res.add(new Pair<>(key,value));

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

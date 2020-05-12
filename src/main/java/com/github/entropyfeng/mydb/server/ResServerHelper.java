package com.github.entropyfeng.mydb.server;

import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.common.protobuf.ProtoTurtleHelper;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.Pair;
import io.netty.channel.Channel;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author entropyfeng
 */
public class ResServerHelper {


    public static final ProtoBuf.ResHead SUCCESS_SINGLE_HEAD = ProtoBuf.ResHead.newBuilder().setSuccess(true).setResSize(1).build();

    public static final ProtoBuf.ResHead EMPTY_HEAD = ProtoBuf.ResHead.newBuilder().setSuccess(true).setResSize(0).build();

    public static final Collection<ProtoBuf.DataBody> EMPTY_COLLECTION=new ArrayList<>(0);

    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> boolRes(boolean value) {

        ProtoBuf.DataBody.Builder bodyBuilder = ProtoBuf.DataBody.newBuilder();
        bodyBuilder.setBoolValue(value);
        ArrayList<ProtoBuf.DataBody> resBodies = new ArrayList<>(1);
        resBodies.add(bodyBuilder.build());
        return new Pair<>(SUCCESS_SINGLE_HEAD, resBodies);

    }

    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> emptyRes() {

        return new Pair<>(EMPTY_HEAD, EMPTY_COLLECTION);
    }

    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> intRes(int intValue) {

        ProtoBuf.DataBody.Builder bodyBuilder = ProtoBuf.DataBody.newBuilder();
        bodyBuilder.setIntValue(intValue);
        ArrayList<ProtoBuf.DataBody> resBodies = new ArrayList<>(1);
        resBodies.add(bodyBuilder.build());
        return new Pair<>(SUCCESS_SINGLE_HEAD, resBodies);
    }
    
    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> longRes(long longValue) {

        ProtoBuf.DataBody.Builder bodyBuilder = ProtoBuf.DataBody.newBuilder();
        bodyBuilder.setLongValue(longValue);
        ArrayList<ProtoBuf.DataBody> resBodies = new ArrayList<>(1);
        resBodies.add(bodyBuilder.build());
        return new Pair<>(SUCCESS_SINGLE_HEAD, resBodies);
    }

    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> stringRes(String string) {

        ProtoBuf.DataBody.Builder bodyBuilder = ProtoBuf.DataBody.newBuilder();
        bodyBuilder.setStringValue(string);
        ArrayList<ProtoBuf.DataBody> resBodies = new ArrayList<>(1);
        resBodies.add(bodyBuilder.build());
        return new Pair<>(SUCCESS_SINGLE_HEAD, resBodies);
    }

    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> turtleRes(TurtleValue turtleValue) {

        ProtoBuf.DataBody.Builder bodyBuilder = ProtoBuf.DataBody.newBuilder();
        bodyBuilder.setTurtleValue(ProtoTurtleHelper.convertToProto(turtleValue));
        ArrayList<ProtoBuf.DataBody> resBodies = new ArrayList<>(1);
        resBodies.add(bodyBuilder.build());
        return new Pair<>(SUCCESS_SINGLE_HEAD, resBodies);
    }

    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> turtleCollectionRes(Collection<TurtleValue> turtleValues) {

        ProtoBuf.ResHead head = ProtoBuf.ResHead.newBuilder().setResSize(turtleValues.size()).setSuccess(true).build();

        ProtoBuf.DataBody.Builder bodyBuilder = ProtoBuf.DataBody.newBuilder();

        ArrayList<ProtoBuf.DataBody> resBodies = new ArrayList<>(turtleValues.size());
        turtleValues.forEach(turtleValue -> resBodies.add(bodyBuilder.setTurtleValue(ProtoTurtleHelper.convertToProto(turtleValue)).build()));

        return new Pair<>(head, resBodies);
    }

    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> stringCollectionRes(Collection<String> strings) {

        ProtoBuf.ResHead head = ProtoBuf.ResHead.newBuilder().setResSize(strings.size()).setSuccess(true).build();

        ProtoBuf.DataBody.Builder bodyBuilder = ProtoBuf.DataBody.newBuilder();

        ArrayList<ProtoBuf.DataBody> resBodies = new ArrayList<>(strings.size());
        strings.forEach(string -> resBodies.add(bodyBuilder.setStringValue(string).build()));

        return new Pair<>(head, resBodies);
    }

    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> stringTurtleCollectionRes(Set<Map.Entry<String, TurtleValue>> entrySet) {

        ProtoBuf.ResHead head = ProtoBuf.ResHead.newBuilder().setResSize(entrySet.size()).setSuccess(true).build();

        ProtoBuf.DataBody.Builder bodyBuilder = ProtoBuf.DataBody.newBuilder();

        ArrayList<ProtoBuf.DataBody> resBodies = new ArrayList<>(entrySet.size());
        ProtoBuf.StringTurtleValueEntry.Builder entryBuilder = ProtoBuf.StringTurtleValueEntry.newBuilder();
        entrySet.forEach(entry -> {
            entryBuilder.setKey(entry.getKey());
            entryBuilder.setValue(ProtoTurtleHelper.convertToProto(entry.getValue()));
            bodyBuilder.setStringTurtleValueEntry(entryBuilder.build());
        });

        return new Pair<>(head, resBodies);
    }

    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> turtleTurtleCollectionRes(Set<Map.Entry<TurtleValue, TurtleValue>> entries) {

        ProtoBuf.ResHead head = ProtoBuf.ResHead.newBuilder().setResSize(entries.size()).setSuccess(true).build();

        ProtoBuf.DataBody.Builder bodyBuilder = ProtoBuf.DataBody.newBuilder();

        ArrayList<ProtoBuf.DataBody> resBodies = new ArrayList<>(entries.size());
        ProtoBuf.TurtleTurtleValueEntry.Builder entryBuilder = ProtoBuf.TurtleTurtleValueEntry.newBuilder();
        entries.forEach(entry -> {
            entryBuilder.setKey(ProtoTurtleHelper.convertToProto(entry.getKey()));
            entryBuilder.setValue(ProtoTurtleHelper.convertToProto(entry.getValue()));
            bodyBuilder.setTurtleTurtleValueEntry(entryBuilder.build());
        });

        return new Pair<>(head, resBodies);
    }


    //--------------------exception---------------
    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> elementOutOfBoundException(String exception) {

        ProtoBuf.ResHead.Builder headBuilder = ProtoBuf.ResHead.newBuilder();
        headBuilder.setSuccess(false);
        headBuilder.setInnerException(exception);
        headBuilder.setInnerExceptionType(ProtoBuf.ExceptionType.ElementOutOfBoundException);

        ArrayList<ProtoBuf.DataBody> resBodies = new ArrayList<>(0);
        return new Pair<>(headBuilder.build(), resBodies);
    }


    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> unsupportedOperationException(String exception) {

        ProtoBuf.ResHead.Builder headBuilder = ProtoBuf.ResHead.newBuilder();
        headBuilder.setSuccess(false);
        headBuilder.setInnerException(exception);
        headBuilder.setInnerExceptionType(ProtoBuf.ExceptionType.UnsupportedOperationException);

        ArrayList<ProtoBuf.DataBody> resBodies = new ArrayList<>(0);
        return new Pair<>(headBuilder.build(), resBodies);
    }


    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> illegalArgumentException(String exception) {

        ProtoBuf.ResHead.Builder headBuilder = ProtoBuf.ResHead.newBuilder();
        headBuilder.setSuccess(false);
        headBuilder.setInnerException(exception);
        headBuilder.setInnerExceptionType(ProtoBuf.ExceptionType.IllegalArgumentException);

        ArrayList<ProtoBuf.DataBody> resBodies = new ArrayList<>(0);
        return new Pair<>(headBuilder.build(), resBodies);
    }



    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.DataBody>> turtleValueElementOutBoundsException(String exception) {

        ProtoBuf.ResHead.Builder headBuilder = ProtoBuf.ResHead.newBuilder();
        headBuilder.setSuccess(false);
        headBuilder.setInnerException(exception);
        headBuilder.setInnerExceptionType(ProtoBuf.ExceptionType.TurtleValueElementOutBoundsException);

        ArrayList<ProtoBuf.DataBody> resBodies = new ArrayList<>(0);
        return new Pair<>(headBuilder.build(), resBodies);
    }


    public static void writeOuterException(Long requestId, Channel channel, ProtoBuf.ExceptionType exceptionType, String msg) {
        ProtoBuf.ResHead.Builder headBuilder = ProtoBuf.ResHead.newBuilder();
        headBuilder.setSuccess(false);
        headBuilder.setInnerExceptionType(exceptionType);
        headBuilder.setInnerException(msg);

        //-------------writeHead--------------------
        ProtoBuf.TurtleData.Builder resBuilder = ProtoBuf.TurtleData.newBuilder();
        resBuilder.setBeginAble(true);
        resBuilder.setRequestId(requestId);
        resBuilder.setEndAble(false);
        resBuilder.setResHead(headBuilder.build());
        channel.write(resBuilder.build());

        //--------------writeEnd-------------------
        resBuilder.clear();
        resBuilder.setRequestId(requestId);
        resBuilder.setEndAble(true);
        channel.write(resBuilder.build());
        channel.flush();
    }


}

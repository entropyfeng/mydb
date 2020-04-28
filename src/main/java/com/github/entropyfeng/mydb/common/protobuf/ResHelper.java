package com.github.entropyfeng.mydb.common.protobuf;

import com.github.entropyfeng.mydb.core.TurtleValue;
import com.github.entropyfeng.mydb.core.helper.Pair;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * @author entropyfeng
 */
public class ResHelper {


    public static final ProtoBuf.ResHead SUCCESS_SINGLE_HEAD = ProtoBuf.ResHead.newBuilder().setSuccess(true).setResSize(1).build();

    public static final ProtoBuf.ResHead EMPTY_HEAD=ProtoBuf.ResHead.newBuilder().setSuccess(true).setResSize(0).build();

    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> boolRes(boolean value) {

        ProtoBuf.ResBody.Builder bodyBuilder = ProtoBuf.ResBody.newBuilder();
        bodyBuilder.setBoolValue(value);
        ArrayList<ProtoBuf.ResBody> resBodies = new ArrayList<>(1);
        resBodies.add(bodyBuilder.build());
        return new Pair<>(SUCCESS_SINGLE_HEAD, resBodies);

    }

    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> emptyRes() {

        ProtoBuf.ResBody.Builder bodyBuilder = ProtoBuf.ResBody.newBuilder();
        ArrayList<ProtoBuf.ResBody> resBodies = new ArrayList<>(0);
        return new Pair<>(EMPTY_HEAD, resBodies);

    }
    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> intRes(int intValue) {

        ProtoBuf.ResBody.Builder bodyBuilder = ProtoBuf.ResBody.newBuilder();
        bodyBuilder.setIntValue(intValue);
        ArrayList<ProtoBuf.ResBody> resBodies = new ArrayList<>(1);
        resBodies.add(bodyBuilder.build());
        return new Pair<>(SUCCESS_SINGLE_HEAD, resBodies);
    }

    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> longRes(long longValue) {

        ProtoBuf.ResBody.Builder bodyBuilder = ProtoBuf.ResBody.newBuilder();
        bodyBuilder.setLongValue(longValue);
        ArrayList<ProtoBuf.ResBody> resBodies = new ArrayList<>(1);
        resBodies.add(bodyBuilder.build());
        return new Pair<>(SUCCESS_SINGLE_HEAD, resBodies);
    }

    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> stringRes(String string) {

        ProtoBuf.ResBody.Builder bodyBuilder = ProtoBuf.ResBody.newBuilder();
        bodyBuilder.setStringValue(string);
        ArrayList<ProtoBuf.ResBody> resBodies = new ArrayList<>(1);
        resBodies.add(bodyBuilder.build());
        return new Pair<>(SUCCESS_SINGLE_HEAD, resBodies);
    }

    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> turtleRes(TurtleValue turtleValue) {

        ProtoBuf.ResBody.Builder bodyBuilder = ProtoBuf.ResBody.newBuilder();
        bodyBuilder.setTurtleValue(ProtoTurtleHelper.convertToProto(turtleValue));
        ArrayList<ProtoBuf.ResBody> resBodies = new ArrayList<>(1);
        resBodies.add(bodyBuilder.build());
        return new Pair<>(SUCCESS_SINGLE_HEAD, resBodies);
    }

    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> turtleCollectionRes(Collection<TurtleValue> turtleValues) {

        ProtoBuf.ResHead head = ProtoBuf.ResHead.newBuilder().setResSize(turtleValues.size()).setSuccess(true).build();

        ProtoBuf.ResBody.Builder bodyBuilder = ProtoBuf.ResBody.newBuilder();

        ArrayList<ProtoBuf.ResBody> resBodies = new ArrayList<>(turtleValues.size());
        turtleValues.forEach(turtleValue -> resBodies.add(bodyBuilder.setTurtleValue(ProtoTurtleHelper.convertToProto(turtleValue)).build()));

        return new Pair<>(head, resBodies);
    }

    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> stringCollectionRes(Collection<String> strings) {

        ProtoBuf.ResHead head = ProtoBuf.ResHead.newBuilder().setResSize(strings.size()).setSuccess(true).build();

        ProtoBuf.ResBody.Builder bodyBuilder = ProtoBuf.ResBody.newBuilder();

        ArrayList<ProtoBuf.ResBody> resBodies = new ArrayList<>(strings.size());
        strings.forEach(string -> resBodies.add(bodyBuilder.setStringValue(string).build()));

        return new Pair<>(head, resBodies);
    }

    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> stringTurtleCollectionRes(Set<Map.Entry<String, TurtleValue>> entrySet) {

        ProtoBuf.ResHead head = ProtoBuf.ResHead.newBuilder().setResSize(entrySet.size()).setSuccess(true).build();

        ProtoBuf.ResBody.Builder bodyBuilder = ProtoBuf.ResBody.newBuilder();

        ArrayList<ProtoBuf.ResBody> resBodies = new ArrayList<>(entrySet.size());
        ProtoBuf.StringTurtleValueEntry.Builder entryBuilder = ProtoBuf.StringTurtleValueEntry.newBuilder();
        entrySet.forEach(entry -> {
            entryBuilder.setKey(entry.getKey());
            entryBuilder.setValue(ProtoTurtleHelper.convertToProto(entry.getValue()));
            bodyBuilder.setStringTurtleValueEntry(entryBuilder.build());
        });

        return new Pair<>(head, resBodies);
    }

    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> turtleTurtleCollectionRes(Set<Map.Entry<TurtleValue, TurtleValue>> entries) {

        ProtoBuf.ResHead head = ProtoBuf.ResHead.newBuilder().setResSize(entries.size()).setSuccess(true).build();

        ProtoBuf.ResBody.Builder bodyBuilder = ProtoBuf.ResBody.newBuilder();

        ArrayList<ProtoBuf.ResBody> resBodies = new ArrayList<>(entries.size());
        ProtoBuf.TurtleTurtleValueEntry.Builder entryBuilder = ProtoBuf.TurtleTurtleValueEntry.newBuilder();
        entries.forEach(entry -> {
            entryBuilder.setKey(ProtoTurtleHelper.convertToProto(entry.getKey()));
            entryBuilder.setValue(ProtoTurtleHelper.convertToProto(entry.getValue()));
            bodyBuilder.setTurtleTurtleValueEntry(entryBuilder.build());
        });

        return new Pair<>(head, resBodies);
    }


    //--------------------exception---------------
    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>>  elementOutOfBoundException(String exception) {

        ProtoBuf.ResHead.Builder headBuilder= ProtoBuf.ResHead.newBuilder();
        headBuilder.setSuccess(false);
        headBuilder.setInnerException(exception);
        headBuilder.setInnerExceptionType(ProtoBuf.ExceptionType.ElementOutOfBoundException);

        ArrayList<ProtoBuf.ResBody> resBodies = new ArrayList<>(0);
        return new Pair<>(headBuilder.build(), resBodies);
    }



    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>>  unsupportedOperationException(String exception) {

        ProtoBuf.ResHead.Builder headBuilder= ProtoBuf.ResHead.newBuilder();
        headBuilder.setSuccess(false);
        headBuilder.setInnerException(exception);
        headBuilder.setInnerExceptionType(ProtoBuf.ExceptionType.UnsupportedOperationException);

        ArrayList<ProtoBuf.ResBody> resBodies = new ArrayList<>(0);
        return new Pair<>(headBuilder.build(), resBodies);
    }


    public static @NotNull Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>>  illegalArgumentException(String exception) {

        ProtoBuf.ResHead.Builder headBuilder= ProtoBuf.ResHead.newBuilder();
        headBuilder.setSuccess(false);
        headBuilder.setInnerException(exception);
        headBuilder.setInnerExceptionType(ProtoBuf.ExceptionType.IllegalArgumentException);

        ArrayList<ProtoBuf.ResBody> resBodies = new ArrayList<>(0);
        return new Pair<>(headBuilder.build(), resBodies);
    }
}

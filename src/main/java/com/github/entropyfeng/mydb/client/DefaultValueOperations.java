package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.client.ops.ResponseValueOperations;
import com.github.entropyfeng.mydb.common.exception.ElementOutOfBoundException;
import com.github.entropyfeng.mydb.common.ops.IValueOperations;
import com.github.entropyfeng.mydb.common.ops.ValueOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoExceptionHelper;
import com.github.entropyfeng.mydb.common.protobuf.ProtoTurtleHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;
import com.github.entropyfeng.mydb.core.helper.Pair;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * @author entropyfeng
 */
public class DefaultValueOperations implements ValueOperations {
    private IValueOperations valueOperations = new ResponseValueOperations();

    @Override
    public void set(String key, TurtleValue value, Long time) throws ElementOutOfBoundException {
        TurtleProtoBuf.ResponseData res = valueOperations.set(key, value, time);
        if (!res.getSuccess()) {
            ProtoExceptionHelper.handler(res.getExceptionType(), res.getException());
        }
    }

    @Override
    public Boolean setIfAbsent(String key, TurtleValue value, Long time) throws ElementOutOfBoundException {
        TurtleProtoBuf.ResponseData res = valueOperations.setIfAbsent(key, value, time);
        if (res.getSuccess()) {
            return res.getBoolValue();
        } else {
            ProtoExceptionHelper.handler(res.getExceptionType(), res.getException());
            return null;
        }

    }

    @Override
    public Boolean setIfPresent(String key, TurtleValue value, Long time) {
        TurtleProtoBuf.ResponseData res = valueOperations.setIfPresent(key, value, time);
        if (res.getSuccess()) {
            return res.getBoolValue();
        } else {
            ProtoExceptionHelper.handler(res.getExceptionType(), res.getException());
            return null;
        }
    }

    @Override
    public TurtleValue get(String key) {
        TurtleProtoBuf.ResponseData res = valueOperations.get(key);
        if (res.getSuccess()) {
            return ProtoTurtleHelper.convertToTurtleValue(res.getTurtleValue());
        } else {
            ProtoExceptionHelper.handler(res.getExceptionType(), res.getException());
            return null;
        }
    }

    @Override
    public TurtleValue increment(String key, Integer intValue) throws UnsupportedOperationException, NoSuchElementException {
        TurtleProtoBuf.ResponseData res = valueOperations.increment(key, intValue);
        if (res.getSuccess()) {
            return ProtoTurtleHelper.convertToTurtleValue(res.getTurtleValue());
        } else {
            ProtoExceptionHelper.handler(res.getExceptionType(), res.getException());
            return null;
        }
    }

    @Override
    public TurtleValue increment(String key, Long longValue) throws UnsupportedOperationException, NoSuchElementException {
        TurtleProtoBuf.ResponseData res = valueOperations.increment(key, longValue);
        if (res.getSuccess()) {
            return ProtoTurtleHelper.convertToTurtleValue(res.getTurtleValue());
        } else {
            ProtoExceptionHelper.handler(res.getExceptionType(), res.getException());
            return null;
        }
    }

    @Override
    public TurtleValue increment(String key, Double doubleValue) throws UnsupportedOperationException, NoSuchElementException {
        TurtleProtoBuf.ResponseData res = valueOperations.increment(key, doubleValue);
        if (res.getSuccess()) {
            return ProtoTurtleHelper.convertToTurtleValue(res.getTurtleValue());
        } else {
            ProtoExceptionHelper.handler(res.getExceptionType(), res.getException());
            return null;
        }
    }

    @Override
    public TurtleValue increment(String key, BigInteger bigInteger) throws UnsupportedOperationException, NoSuchElementException {
        TurtleProtoBuf.ResponseData res = valueOperations.increment(key, bigInteger);
        if (res.getSuccess()) {
            return ProtoTurtleHelper.convertToTurtleValue(res.getTurtleValue());
        } else {
            ProtoExceptionHelper.handler(res.getExceptionType(), res.getException());
            return null;
        }
    }

    @Override
    public TurtleValue increment(String key, BigDecimal bigDecimal) throws UnsupportedOperationException, NoSuchElementException {
        TurtleProtoBuf.ResponseData res = valueOperations.increment(key, bigDecimal);
        if (res.getSuccess()) {
            return ProtoTurtleHelper.convertToTurtleValue(res.getTurtleValue());
        } else {
            ProtoExceptionHelper.handler(res.getExceptionType(), res.getException());
            return null;
        }
    }

    @Override
    public void append(String key, String appendValue) throws UnsupportedOperationException, NoSuchElementException {
        TurtleProtoBuf.ResponseData res = valueOperations.append(key, appendValue);
        if (!res.getSuccess()) {
            ProtoExceptionHelper.handler(res.getExceptionType(), res.getException());
        }
    }

    @Override
    public Collection<TurtleValue> allValues() {
        Collection<TurtleProtoBuf.ResponseData> resCollection = valueOperations.allValues();
        TurtleProtoBuf.ResponseData[] resArr =  resCollection.toArray(new TurtleProtoBuf.ResponseData[0]);
        TurtleProtoBuf.ResponseData first=resArr[0];
        ArrayList<TurtleValue> turtleValues;

        if (first.getSuccess()){
            if (first.getCollectionSize()+1==resCollection.size()){
                turtleValues=new ArrayList<>((int)first.getCollectionSize());
                for (int i = 1; i <resArr.length ; i++) {
                    turtleValues.add(ProtoTurtleHelper.convertToTurtleValue(resArr[i].getTurtleValue()));
                }
                return turtleValues;
            }
            throw new IllegalArgumentException(" except "+first.getCollectionSize()+" find "+(resArr.length-1));
        }
        ProtoExceptionHelper.handler(first.getExceptionType(),first.getException());
        return null;
    }

    @Override
    public Collection<String> allKeys() {
        Collection<TurtleProtoBuf.ResponseData> resCollection = valueOperations.allValues();
        TurtleProtoBuf.ResponseData[] resArr =  resCollection.toArray(new TurtleProtoBuf.ResponseData[0]);
        TurtleProtoBuf.ResponseData first=resArr[0];
        ArrayList<String> stringsValues;
        if (first.getSuccess()){
            if (first.getCollectionSize()+1==resCollection.size()){
               stringsValues=new ArrayList<>((int)first.getCollectionSize());
                for (int i = 1; i <resArr.length ; i++) {
                   stringsValues.add(resArr[i].getStringValue());
                }
                return stringsValues;
            }
            throw new IllegalArgumentException(" except "+first.getCollectionSize()+" find "+(resArr.length-1));
        }
        ProtoExceptionHelper.handler(first.getExceptionType(),first.getException());
        return null;
    }

    @Override
    public Collection<Pair<String, TurtleValue>> allEntries() {

        ArrayList<TurtleProtoBuf.ResponseData> resCollection =(ArrayList<TurtleProtoBuf.ResponseData>) valueOperations.allEntries();
        TurtleProtoBuf.ResponseData first=resCollection.get(0);
        ArrayList<Pair<String,TurtleValue>> entries;
        if (first.getSuccess()){
            if (first.getCollectionSize()+1==resCollection.size()){
                entries=new ArrayList<>((int)first.getCollectionSize());
                for (int i = 1; i <resCollection.size() ; i++) {

                  TurtleProtoBuf.StringTurtleValueEntry entry= resCollection.get(i).getStringTurtleValueEntry();
                  entries.add(new Pair<>(entry.getKey(),ProtoTurtleHelper.convertToTurtleValue(entry.getValue())));
                }
                return entries;
            }
            throw new IllegalArgumentException(" except "+first.getCollectionSize()+" find "+(resCollection.size()-1));
        }
        ProtoExceptionHelper.handler(first.getExceptionType(),first.getException());
        return null;
    }
}

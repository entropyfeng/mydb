package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.client.conn.ClientExecute;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.TurtleParaType;
import com.github.entropyfeng.mydb.common.ops.ValueOperations;
import com.github.entropyfeng.mydb.core.obj.TurtleValue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Collection;
import java.util.NoSuchElementException;

/**
 * @author entropyfeng
 */
public class DefaultValueOperations implements ValueOperations {


    @Override
    public Void set(String key, TurtleValue value, Long time) {

        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "set");
        builder.addPara(TurtleParaType.STRING, key);
        builder.addPara(TurtleParaType.TURTLE_VALUE, value);
        builder.addPara(TurtleParaType.LONG, (Long) time);
        ClientExecute.execute(builder.build());
        return null;
    }

    @Override
    public Boolean setIfAbsent(String key, TurtleValue value, Long time) {
        ClientCommandBuilder builder=new ClientCommandBuilder(TurtleModel.VALUE,"setIfAbsent");
        builder.addPara(TurtleParaType.STRING, key);
        builder.addPara(TurtleParaType.TURTLE_VALUE, value);
        builder.addPara(TurtleParaType.LONG, (Long) time);
        ClientExecute.execute(builder.build());

        return null;
    }

    @Override
    public Boolean setIfPresent(String key, TurtleValue value, Long time) {
        ClientCommandBuilder builder=new ClientCommandBuilder(TurtleModel.VALUE,"setIfPresent");
        builder.addPara(TurtleParaType.STRING, key);
        builder.addPara(TurtleParaType.TURTLE_VALUE, value);
        builder.addPara(TurtleParaType.LONG, (Long) time);
        ClientExecute.execute(builder.build());
        return null;
    }

    @Override
    public TurtleValue get(String key) {
        return null;
    }

    @Override
    public TurtleValue increment(String key, Integer intValue) throws UnsupportedOperationException, NoSuchElementException {
        return null;
    }

    @Override
    public TurtleValue increment(String key, Long longValue) throws UnsupportedOperationException, NoSuchElementException {
        return null;
    }

    @Override
    public TurtleValue increment(String key, Double doubleValue) throws UnsupportedOperationException, NoSuchElementException {
        return null;
    }

    @Override
    public TurtleValue increment(String key, BigInteger bigInteger) throws UnsupportedOperationException, NoSuchElementException {
        return null;
    }

    @Override
    public TurtleValue increment(String key, BigDecimal bigDecimal) throws UnsupportedOperationException, NoSuchElementException {
        return null;
    }

    @Override
    public Void append(String key, String appendValue) throws UnsupportedOperationException, NoSuchElementException {


        return null;
    }

    @Override
    public Collection<TurtleValue> allValues() {
        return null;
    }


}

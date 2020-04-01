package com.github.entropyfeng.mydb.client;

import com.github.entropyfeng.mydb.client.conn.TurtleClientChannelFactory;
import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.TurtleParaType;
import com.github.entropyfeng.mydb.common.TurtleValueType;
import com.github.entropyfeng.mydb.common.ops.ValueOperations;
import com.github.entropyfeng.mydb.common.protobuf.ProtoParaHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.obj.TurtleValue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

/**
 * @author entropyfeng
 */
public class DefaultValueOperations implements ValueOperations {


    @Override
    public void set(String key, TurtleValue value, long time) {

        ClientCommandBuilder builder = new ClientCommandBuilder(TurtleModel.VALUE, "set");
        builder.addPara(TurtleParaType.STRING, key);
        builder.addPara(TurtleParaType.TURTLE_VALUE, value);
        builder.addPara(TurtleParaType.LONG, (Long) time);

        TurtleClientChannelFactory.getChannel().writeAndFlush(builder.build());
    }

    @Override
    public Boolean setIfAbsent(String key, TurtleValue value, long time) {
        return null;
    }

    @Override
    public Boolean setIfPresent(String key, TurtleValue value, long time) {
        return null;
    }

    @Override
    public TurtleValue get(String key) {
        return null;
    }

    @Override
    public TurtleValue increment(String key, int intValue) throws UnsupportedOperationException {
        return null;
    }

    @Override
    public TurtleValue increment(String key, long longValue) throws UnsupportedOperationException {
        return null;
    }

    @Override
    public TurtleValue increment(String key, double doubleValue) throws UnsupportedOperationException {
        return null;
    }

    @Override
    public TurtleValue increment(String key, BigInteger bigInteger) throws UnsupportedOperationException {
        return null;
    }

    @Override
    public TurtleValue increment(String key, BigDecimal bigDecimal) throws UnsupportedOperationException {
        return null;
    }

    @Override
    public Boolean append(String key, String appendValue) throws UnsupportedOperationException {
        return null;
    }
}

package com.github.entropyfeng.mydb.server.command;

import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.TurtleValue;
import com.github.entropyfeng.mydb.common.exception.TurtleValueElementOutBoundsException;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import com.github.entropyfeng.mydb.common.protobuf.ProtoModelHelper;
import com.github.entropyfeng.mydb.common.protobuf.ProtoTurtleHelper;
import com.github.entropyfeng.mydb.server.AdminObject;
import com.github.entropyfeng.mydb.server.domain.*;
import io.netty.channel.Channel;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.github.entropyfeng.mydb.server.command.ServerExecute.exceptionWrite;

/**
 * @author entropyfeng
 */
public class ClientRequest implements ICommand {

    private static final Logger logger = LoggerFactory.getLogger(ClientRequest.class);

    private final String operationName;
    private final TurtleModel model;
    private final List<ProtoBuf.TurtleParaType> typeList;
    private final Class<?>[] types;
    private final ArrayList<Object> objects;
    private final Long requestId;
    private final Channel channel;
    private final boolean modify;

    private Method method;

    @SuppressWarnings("all")
    public ClientRequest(@NotNull ProtoBuf.ReqHead header, @NotNull Long requestId, @NotNull Channel channel) {
        this.operationName = header.getOperationName();
        this.typeList = header.getKeysList();
        this.requestId = requestId;
        this.model = ProtoModelHelper.convertToTurtleModel(header.getModel());
        this.modify = header.getModify();
        this.channel = channel;

        final int size = typeList.size();
        types = new Class<?>[size];
        objects = new ArrayList<>(size);

        //construct typeList
        for (int i = 0; i < size; i++) {

            switch (typeList.get(i)) {
                case INTEGER:
                    types[i] = Integer.class;
                    break;
                case BOOL:
                    types[i] = Boolean.class;
                    break;
                case LONG:
                    types[i] = Long.class;
                    break;
                case STRING:
                    types[i] = String.class;
                    break;
                case DOUBLE:
                    types[i] = Double.class;
                    break;
                case TURTLE_VALUE:
                    types[i] = TurtleValue.class;
                    break;
                case BYTES:
                    types[i] = byte[].class;
                    break;
                case NUMBER_DECIMAL:
                    types[i] = BigDecimal.class;
                    break;
                case NUMBER_INTEGER:
                    types[i] = BigInteger.class;
                    break;
                case COLLECTION_INTEGER:
                    objects.add(i, new ArrayList<Integer>());
                    types[i] = Collection.class;
                    break;
                case COLLECTION_TURTLE_VALUE:
                    objects.add(new ArrayList<TurtleValue>());
                    types[i] = Collection.class;
                    break;
                case COLLECTION_NUMBER_INTEGER:
                    objects.add(new ArrayList<BigInteger>());
                    types[i] = Collection.class;
                    break;
                case COLLECTION_LONG:
                    objects.add(new ArrayList<Long>());
                    types[i] = Collection.class;
                    break;
                case COLLECTION_DOUBLE:
                    objects.add(new ArrayList<Double>());
                    types[i] = Collection.class;
                    break;
                case COLLECTION_NUMBER_DECIMAL:
                    objects.add(new ArrayList<BigDecimal>());
                    types[i] = Collection.class;
                    break;
                case COLLECTION_BYTES:
                    objects.add(new ArrayList<byte[]>());
                    types[i] = Collection.class;
                    break;
                case COLLECTION_STRING:
                    objects.add(new ArrayList<String>());
                    types[i] = Collection.class;
                    break;
                default:
                    throw new UnsupportedOperationException();
            }
        }

    }

    public ClientRequest(@NotNull Method method){
        this.operationName = null;
        this.typeList = null;
        this.types=null;
        this.requestId =null;
        this.model = null;
        this.modify = false;
        this.channel = null;
        this.method=method;
        this.objects=new ArrayList<>(0);

    }

    @SuppressWarnings("unchecked")
    public void put(ProtoBuf.ReqBody body) throws TurtleValueElementOutBoundsException {

        int location = body.getLocation();
        switch (typeList.get(location)) {
            case DOUBLE:
                objects.add(location, body.getDoubleValue());
                break;
            case BOOL:
                objects.add(location, body.getBoolValue());
                break;
            case INTEGER:
                objects.add(location, body.getIntValue());
                break;
            case LONG:
                objects.add(location, body.getLongValue());
                break;
            case STRING:
                objects.add(location, body.getStringValue());
                break;
            case BYTES:
                objects.add(location, body.getBytesValue().toByteArray());
                break;
            case NUMBER_INTEGER:
                objects.add(location, new BigInteger(body.getStringValue()));
                break;
            case NUMBER_DECIMAL:
                objects.add(location, new BigDecimal(body.getStringValue()));
                break;
            case TURTLE_VALUE:
                //turtleValue's byte array length may too long,this method may cause exception
                objects.add(location, ProtoTurtleHelper.convertToDbTurtle(body.getTurtleValue()));
                break;
            case COLLECTION_INTEGER:
                ((ArrayList<Integer>) objects.get(location)).add(body.getIntValue());
                break;
            case COLLECTION_LONG:
                ((ArrayList<Long>) objects.get(location)).add(body.getLongValue());
                break;
            case COLLECTION_DOUBLE:
                ((ArrayList<Double>) objects.get(location)).add(body.getDoubleValue());
                break;
            case COLLECTION_BYTES:
                ((ArrayList<byte[]>) objects.get(location)).add(body.getBytesValue().toByteArray());
                break;
            case COLLECTION_NUMBER_INTEGER:
                ((ArrayList<BigInteger>) objects.get(location)).add(new BigInteger(body.getStringValue()));
                break;
            case COLLECTION_NUMBER_DECIMAL:
                ((ArrayList<BigDecimal>) objects.get(location)).add(new BigDecimal(body.getStringValue()));
                break;
            case COLLECTION_TURTLE_VALUE:
                ((ArrayList<TurtleValue>) objects.get(location)).add(ProtoTurtleHelper.convertToDbTurtle(body.getTurtleValue()));
                break;
            default:
                throw new UnsupportedOperationException();

        }
    }

    //------getter--------------

    @Override
    public Long getRequestId() {
        return requestId;
    }

    @Override
    public Method getMethod() {
        return method;
    }

    @Override
    public Channel getChannel() {
        return channel;
    }

    @Override
    public List<Object> getValues() {
        return objects;
    }

    public ClientRequest build() {
        Class<?> target;
        switch (model) {
            case VALUE:
                target = ValuesDomain.class;
                break;
            case LIST:
                target = ListDomain.class;
                break;
            case SET:
                target = SetDomain.class;
                break;
            case HASH:
                target = HashDomain.class;
                break;
            case ZSET:
                target = OrderSetDomain.class;
                break;
            default:
                target = AdminObject.class;
                break;
        }
        try {
            if (types.length == 0) {
                method = target.getDeclaredMethod(operationName);
            } else {
                method = target.getDeclaredMethod(operationName, types);
            }
            return this;
        } catch (NoSuchMethodException e) {
            logger.error(e.getMessage());
            exceptionWrite(channel, requestId, ProtoBuf.ExceptionType.NoSuchMethodException, e.getMessage());
            return null;
        }
    }

    public String getOperationName() {
        return operationName;
    }

    public TurtleModel getModel() {
        return model;
    }

    public boolean getModify() {
        return this.modify;
    }
}

package com.github.entropyfeng.mydb.server.command;

import com.github.entropyfeng.mydb.common.TurtleModel;
import com.github.entropyfeng.mydb.common.protobuf.ProtoModelHelper;
import com.github.entropyfeng.mydb.common.protobuf.ProtoTurtleHelper;
import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author entropyfeng
 */
public class ClientRequest {


    private final String operationName;
    private final TurtleModel model;
    private List<TurtleProtoBuf.TurtleParaType> typeList;
    private Class<?>[] types;
    private ArrayList<Object> objects;
    private final Long requestId;
    private boolean modify;
    @SuppressWarnings("all")
    public ClientRequest(TurtleProtoBuf.RequestHeaderPayload header,Long requestId){
        this.operationName=header.getOperationName();
        this.typeList=header.getKeysList();
        this.requestId=requestId;
        this.model= ProtoModelHelper.convertToTurtleModel(header.getModel());
        this.modify=header.getModify();

        final int size=typeList.size();
        types=new Class<?>[size];
        objects=new ArrayList<>(size);
        for (int i = 0; i < size; i++) {

            switch (typeList.get(i)){
                case INTEGER:
                    types[i]=Integer.class;
                    break;
                case BOOL:
                    types[i]=Boolean.class;
                    break;
                case LONG:
                    types[i]=Long.class;
                    break;
                case STRING:
                    types[i]=String.class;
                    break;
                case DOUBLE:
                    types[i]=Double.class;
                    break;
                case TURTLE_VALUE:
                    types[i]= TurtleValue.class;
                    break;
                case BYTES:
                    types[i]=byte[].class;
                    break;
                case NUMBER_DECIMAL:
                    types[i]= BigDecimal.class;
                    break;
                case NUMBER_INTEGER:
                    types[i]= BigInteger.class;
                    break;
                case COLLECTION_INTEGER:
                    objects.add(i,new ArrayList<Integer>());
                    types[i]= Collection.class;
                    break;
                case COLLECTION_TURTLE_VALUE:
                    objects.add(i,new ArrayList<TurtleValue>());
                    types[i]= Collection.class;
                    break;
                case COLLECTION_NUMBER_INTEGER:
                    objects.add(i,new ArrayList<BigInteger>());
                    types[i]= Collection.class;
                    break;
                case COLLECTION_LONG:
                    objects.add(i,new ArrayList<Long>());
                    types[i]= Collection.class;
                    break;
                case COLLECTION_DOUBLE:
                    objects.add(i,new ArrayList<Double>());
                    types[i]= Collection.class;
                    break;
                case COLLECTION_NUMBER_DECIMAL:
                    objects.add(i,new ArrayList<BigDecimal>());
                    types[i]=Collection.class;
                    break;
                case COLLECTION_BYTES:
                    objects.add(i,new ArrayList<byte[]>());
                    types[i]= Collection.class;
                    break;
                case COLLECTION_STRING:
                    objects.add(i,new ArrayList<String>());
                    types[i]=Collection.class;
                default:throw new UnsupportedOperationException();
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void put(TurtleProtoBuf.RequestBodyPayload body){

        int location=body.getLocation();
        switch (typeList.get(location)){
            case DOUBLE:
                objects.add(location,body.getDoubleValue());
                break;
            case BOOL:
                objects.add(location,body.getBoolValue());
                break;
            case INTEGER:
                objects.add(location,body.getIntValue());
                break;
            case LONG:
                objects.add(location,body.getLongValue());
                break;
            case STRING:
                objects.add(location,body.getStringValue());
                break;
            case BYTES:
                objects.add(location,body.getBytesValue().toByteArray());
                break;
            case NUMBER_INTEGER:
                objects.add(location,new BigInteger(body.getStringValue()));
                break;
            case NUMBER_DECIMAL:
                objects.add(location,new BigDecimal(body.getStringValue()));
                break;
            case TURTLE_VALUE:
                objects.add(location, ProtoTurtleHelper.convertToTurtleValue(body.getTurtleValue()));
                break;
            case COLLECTION_INTEGER:
                ((ArrayList<Integer>)objects.get(location)).add(body.getIntValue());
                break;
            case COLLECTION_LONG:
                ((ArrayList<Long>)objects.get(location)).add(body.getLongValue());
                break;
            case COLLECTION_DOUBLE:
                ((ArrayList<Double>)objects.get(location)).add(body.getDoubleValue());
                break;
            case COLLECTION_BYTES:
                ((ArrayList<byte[]>)objects.get(location)).add(body.getBytesValue().toByteArray());
                break;
            case COLLECTION_NUMBER_INTEGER:
                ((ArrayList<BigInteger>)objects.get(location)).add(new BigInteger(body.getStringValue()));
                break;
            case COLLECTION_NUMBER_DECIMAL:
                ((ArrayList<BigDecimal>)objects.get(location)).add(new BigDecimal(body.getStringValue()));
                break;
            case COLLECTION_TURTLE_VALUE:
                ((ArrayList<TurtleValue>)objects.get(location)).add(ProtoTurtleHelper.convertToTurtleValue(body.getTurtleValue()));
                break;
            default:throw  new UnsupportedOperationException();

        }
    }

    //------getter--------------

    public Long getRequestId() {
        return requestId;
    }

    public String getOperationName() {
        return operationName;
    }

    public TurtleModel getModel() {
        return model;
    }

    public Class<?>[] getTypes() {
        return types;
    }

    public ArrayList<Object> getObjects() {
        return objects;
    }

    public boolean getModify(){
        return this.modify;
    }
}

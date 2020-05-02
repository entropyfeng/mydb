package com.github.entropyfeng.mydb.server.command;

import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;

/**
 *
 * @author entropyfeng
 */
public class ServerExecute {


    public static Logger logger= LoggerFactory.getLogger(ServerExecute.class);


    private static void writeChannel(Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> pair, Channel channel, Long requestId) {

        //-----------header-------------------------
        ProtoBuf.TurtleData.Builder responseBuilder = ProtoBuf.TurtleData.newBuilder();
        responseBuilder.setResHead(pair.getKey());
        responseBuilder.setBeginAble(true);
        responseBuilder.setEndAble(false);
        responseBuilder.setRequestId(requestId);
        channel.write(responseBuilder.build());


        //-----------body---------------------------
        responseBuilder.clear();
        responseBuilder.setRequestId(requestId);
        responseBuilder.setEndAble(false);
        responseBuilder.setBeginAble(false);

        pair.getValue().forEach(resBody -> channel.write(responseBuilder.setResBody(resBody).build()));


        //---------------end------------------
        responseBuilder.clear();
        responseBuilder.setRequestId(requestId);
        responseBuilder.setEndAble(true);
        responseBuilder.setBeginAble(false);
        channel.write(responseBuilder.build());
        channel.flush();
    }



    @SuppressWarnings("unchecked")
    public static void  execute(ICommand command, Object target) {

        Object res;
        try {
            if (command.getValues()==null||command.getValues().size() == 0) {
                res = command.getMethod().invoke(target);
            } else {
                res = command.getMethod().invoke(target, command.getValues().toArray(new Object[0]));
            }
        } catch (IllegalAccessException e) {
            if (command.getChannel()==null){
                logger.error("server request error {}",e.getMessage());
                return;
            }
            exceptionWrite(command.getChannel(), command.getRequestId(), ProtoBuf.ExceptionType.IllegalAccessException, "禁止访问" + e.getMessage());
            return;
        } catch (InvocationTargetException e) {
            if (command.getChannel()==null){
                logger.error("server request error {}",e.getMessage());
                return;
            }
            //调用函数的内部有未捕获的异常
            exceptionWrite(command.getChannel(), command.getRequestId(), ProtoBuf.ExceptionType.InvocationTargetException, e.getMessage());
            return;
        }

        //it represent the command is requested by the server itself
        if (command.getChannel()==null){
            return;
        }
        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> pair = ((Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>>) res);

        writeChannel(pair, command.getChannel(), command.getRequestId());
    }


    public static void exceptionWrite(Channel channel, Long requestId, ProtoBuf.ExceptionType exceptionType, String msg) {
        ProtoBuf.ResHead.Builder headBuilder = ProtoBuf.ResHead.newBuilder();
        headBuilder.setSuccess(false);
        headBuilder.setResSize(0);
        headBuilder.setInnerException(msg);
        headBuilder.setInnerExceptionType(exceptionType);

        ProtoBuf.TurtleData.Builder resDataBuilder = ProtoBuf.TurtleData.newBuilder().setRequestId(requestId).setResHead(headBuilder.build());

        channel.write(resDataBuilder.build());
        resDataBuilder.clear().setEndAble(true).setRequestId(requestId);
        channel.write(resDataBuilder.build());
        channel.flush();
    }

}

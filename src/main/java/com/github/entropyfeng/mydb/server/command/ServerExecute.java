package com.github.entropyfeng.mydb.server.command;

import com.github.entropyfeng.mydb.common.Pair;
import com.github.entropyfeng.mydb.common.protobuf.ProtoBuf;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author entropyfeng
 */
public class ServerExecute {


    public static Logger logger= LoggerFactory.getLogger(ServerExecute.class);
    public static void constructCommand(ClientRequest clientRequest, Channel channel, Class<?> target, ConcurrentLinkedQueue<ClientCommand> queue) {
        Method method;
        final String operationName = clientRequest.getOperationName();
        final Long requestId = clientRequest.getRequestId();
        final Class<?>[] types = clientRequest.getTypes();

        try {
            if (types.length == 0) {
                method = target.getDeclaredMethod(operationName);
            } else {
                method = target.getDeclaredMethod(operationName, types);
            }
        } catch (NoSuchMethodException e) {
            logger.info(e.getMessage());
            exceptionWrite(channel, requestId, ProtoBuf.ExceptionType.NoSuchMethodException, e.getMessage());
            return;
        }

        queue.offer(new ClientCommand(method, clientRequest.getObjects(), channel, requestId));

    }

    private static void writeChannel(Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> pair, Channel channel, Long requestId) {


        logger.info("writeChannel requestId ->{} ; ",requestId);


        //-----------header-------------------------
        ProtoBuf.ResponseData.Builder responseBuilder = ProtoBuf.ResponseData.newBuilder();
        responseBuilder.setHeader(pair.getKey());
        responseBuilder.setBeginAble(true);
        responseBuilder.setEndAble(false);
        responseBuilder.setRequestId(requestId);
        logger.info(pair.getKey().getSuccess()+"");
        channel.write(responseBuilder.build());


        //-----------body---------------------------
        responseBuilder.clear();
        responseBuilder.setRequestId(requestId);
        responseBuilder.setEndAble(false);
        responseBuilder.setBeginAble(false);
        logger.info("body size {}",pair.getValue().size());
        pair.getValue().forEach(resBody -> channel.write(responseBuilder.setBody(resBody).build()));


        //---------------end------------------
        responseBuilder.clear();
        responseBuilder.setRequestId(requestId);
        responseBuilder.setEndAble(true);
        responseBuilder.setBeginAble(false);
        logger.info(responseBuilder.build().toString());
        channel.write(responseBuilder.build());
        channel.flush();
    }



    public static void  execute(ICommand command, Object target) {
        Object res;
        try {
            if (command.getValues().size() == 0) {
                res = command.getMethod().invoke(target);
            } else {

                res = command.getMethod().invoke(target, command.getValues().toArray(new Object[0]));
            }
        } catch (IllegalAccessException e) {
            exceptionWrite(command.getChannel(), command.getRequestId(), ProtoBuf.ExceptionType.IllegalAccessException, "禁止访问" + e.getMessage());
            return;
        } catch (InvocationTargetException e) {
            //调用函数的内部有未捕获的异常
            exceptionWrite(command.getChannel(), command.getRequestId(), ProtoBuf.ExceptionType.InvocationTargetException, e.getMessage());
            return;
        }

        Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>> pair = ((Pair<ProtoBuf.ResHead, Collection<ProtoBuf.ResBody>>) res);

        writeChannel(pair, command.getChannel(), command.getRequestId());
    }


    private static void exceptionWrite(Channel channel, Long requestId, ProtoBuf.ExceptionType exceptionType, String msg) {
        ProtoBuf.ResHead.Builder headBuilder = ProtoBuf.ResHead.newBuilder();
        headBuilder.setSuccess(false);
        headBuilder.setResSize(0);
        headBuilder.setInnerException(msg);
        headBuilder.setInnerExceptionType(exceptionType);

        ProtoBuf.ResponseData.Builder resDataBuilder = ProtoBuf.ResponseData.newBuilder().setRequestId(requestId).setHeader(headBuilder.build());

        channel.write(resDataBuilder.build());
        resDataBuilder.clear().setEndAble(true).setRequestId(requestId);
        channel.write(resDataBuilder.build());
        channel.flush();
    }

}

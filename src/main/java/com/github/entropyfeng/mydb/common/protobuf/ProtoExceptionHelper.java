package com.github.entropyfeng.mydb.common.protobuf;

import com.github.entropyfeng.mydb.common.exception.ElementOutOfBoundException;
import com.github.entropyfeng.mydb.common.exception.TurtleFatalError;
import com.github.entropyfeng.mydb.common.exception.TurtleTimeOutException;
import com.github.entropyfeng.mydb.common.exception.TurtleValueElementOutBoundsException;

import java.util.NoSuchElementException;

/**
 * @author entropyfeng
 */
public class ProtoExceptionHelper {

    public static void handler(ProtoBuf.ExceptionType type, String msg)throws RuntimeException  {
        switch (type){
            case NoSuchMethodException:
            case IllegalAccessException:
            case TurtleDesignError:
                throw new Error(msg);
            case NullPointerException:throw new NullPointerException(msg);
            case OutOfMemoryError:throw new OutOfMemoryError(msg);
            case UnsupportedOperationException:throw new UnsupportedOperationException(msg);
            case NoSuchElementException:throw new NoSuchElementException(msg);
            case TurtleFatalError:throw new TurtleFatalError(msg);
            case InvocationTargetException:throw new RuntimeException("内部运行错误:"+msg);
            case RuntimeException:throw new RuntimeException(msg);
            case StackOverflowError:throw new StackOverflowError(msg);
            case ElementOutOfBoundException:throw new ElementOutOfBoundException(msg);
            case TurtleTimeOutException:throw new TurtleTimeOutException(msg);
            case TurtleValueElementOutBoundsException:throw new TurtleValueElementOutBoundsException(msg);
            default:throw new Error("unknown error");
        }
    }
}

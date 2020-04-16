package com.github.entropyfeng.mydb.common.exception;

/**
 * 该数据库内部数据结构超限异常
 * @author entropyfeng
 */
public class OutOfBoundException extends RuntimeException {

    public OutOfBoundException() {
    }

    public OutOfBoundException(String message) {
        super(message);
    }

    public OutOfBoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OutOfBoundException(Throwable cause) {
        super(cause);
    }

    public OutOfBoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

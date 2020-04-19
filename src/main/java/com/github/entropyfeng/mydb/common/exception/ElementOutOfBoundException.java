package com.github.entropyfeng.mydb.common.exception;

/**
 * 该数据库内部数据结构超限异常
 * @author entropyfeng
 */
public class ElementOutOfBoundException extends RuntimeException {

    public ElementOutOfBoundException() {
    }

    public ElementOutOfBoundException(String message) {
        super(message);
    }

    public ElementOutOfBoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ElementOutOfBoundException(Throwable cause) {
        super(cause);
    }

    public ElementOutOfBoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

package com.github.entropyfeng.mydb.common.exception;


/**
 * @author entropyfeng
 */
public class TurtleTimeOutException extends RuntimeException {

    public TurtleTimeOutException() {
    }

    public TurtleTimeOutException(String message) {
        super(message);
    }

    public TurtleTimeOutException(String message, Throwable cause) {
        super(message, cause);
    }

    public TurtleTimeOutException(Throwable cause) {
        super(cause);
    }

    public TurtleTimeOutException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

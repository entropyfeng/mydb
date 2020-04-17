package com.github.entropyfeng.mydb.common.exception;

/**
 * @author entropyfeng
 */
public class TurtleFatalError extends Error {

    public TurtleFatalError() {
    }

    public TurtleFatalError(String message) {
        super(message);
    }

    public TurtleFatalError(String message, Throwable cause) {
        super(message, cause);
    }

    public TurtleFatalError(Throwable cause) {
        super(cause);
    }

    public TurtleFatalError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

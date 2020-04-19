package com.github.entropyfeng.mydb.common.exception;

/**
 * 数据库设计出错
 * @author entropyfeng
 */
public class TurtleDesignError extends RuntimeException {
    public TurtleDesignError() {
    }

    public TurtleDesignError(String message) {
        super(message);
    }

    public TurtleDesignError(String message, Throwable cause) {
        super(message, cause);
    }

    public TurtleDesignError(Throwable cause) {
        super(cause);
    }

    public TurtleDesignError(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

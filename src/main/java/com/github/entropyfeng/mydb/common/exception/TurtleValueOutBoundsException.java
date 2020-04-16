package com.github.entropyfeng.mydb.common.exception;

/**
 * {@link com.github.entropyfeng.mydb.core.obj.TurtleValue}长度超限异常
 * @author entropyfeng
 */
public class TurtleValueOutBoundsException extends OutOfBoundException {
    public TurtleValueOutBoundsException() {

    }

    public TurtleValueOutBoundsException(String s) {
        super(s);

    }
}

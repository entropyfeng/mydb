package com.github.entropyfeng.mydb.common.exception;

import com.github.entropyfeng.mydb.core.TurtleValue;

/**
 * {@link TurtleValue}长度超限异常
 * @author entropyfeng
 */
public class TurtleValueOutBoundsException extends OutOfBoundException {
    public TurtleValueOutBoundsException() {

    }

    public TurtleValueOutBoundsException(String s) {
        super(s);

    }
}

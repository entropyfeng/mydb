package com.github.entropyfeng.mydb.common.exception;

import com.github.entropyfeng.mydb.common.TurtleValue;

/**
 * {@link TurtleValue}长度超限异常
 * @author entropyfeng
 */
public class TurtleValueElementOutBoundsException extends ElementOutOfBoundException {
    public TurtleValueElementOutBoundsException() {

    }

    public TurtleValueElementOutBoundsException(String s) {
        super(s);

    }
}

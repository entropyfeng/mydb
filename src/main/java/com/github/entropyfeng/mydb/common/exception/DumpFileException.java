package com.github.entropyfeng.mydb.common.exception;

import java.io.IOException;

/**
 * dump file 文件格式错误
 * @author entropyfeng
 */
public class DumpFileException extends IOException {


    public DumpFileException() {
    }

    public DumpFileException(String message) {
        super(message);
    }

    public DumpFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public DumpFileException(Throwable cause) {
        super(cause);
    }
}

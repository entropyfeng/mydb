package com.github.entropyfeng.mydb.client;

import org.jetbrains.annotations.NotNull;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author entropyfeng
 */
public class TurtleOutputStream  extends OutputStream {


    public long count=0;
    @Override
    public void write(int b) throws IOException {
       count++;
    }

    @Override
    public void write(@NotNull byte[] b) throws IOException {
        super.write(b);
        System.out.println("call byte");
    }

    @Override
    public void write(@NotNull byte[] b, int off, int len) throws IOException {
        super.write(b, off, len);
        System.out.println("call byte[]");
    }
}

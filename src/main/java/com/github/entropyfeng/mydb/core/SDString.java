package com.github.entropyfeng.mydb.core;

import com.github.entropyfeng.mydb.constant.Storage;

import java.io.Serializable;
import java.util.Arrays;

/**
 * @author entropyfeng
 * @date 2019/12/27 17:27
 * simple dynamic string
 */
public class SDString implements Serializable {


    public SDString(SDString source) {
        this.free = source.free;
        this.len = source.len;
        this.buf = Arrays.copyOf(source.buf, len + free);
    }

    public SDString(String source) {
        build(source.toCharArray());
    }

    private void build(char[] source) {
        if (source.length <= Storage._1M) {
            this.buf = Arrays.copyOf(source, source.length * 2);
            this.len = source.length;
            this.free = source.length;
        } else {
            this.buf = Arrays.copyOf(source, source.length + Storage._1M);
            this.len = source.length;
            this.free = Storage._1M;
        }
    }


    private int len = 0;

    private int free = 0;

    private char[] buf;

    /**
     * @return 已使用空间字节数
     */
    public int sdsLen() {
        return len;
    }

    /**
     * @return 可用的字节数
     */
    public int sdsAvail() {
        return free;
    }

    public void sdsClear() {

    }

    /**
     * 深拷贝该对象
     *
     * @return {@link SDString}
     */
    public SDString sdsDup() {

        return new SDString(this);
    }

    /**
     * 在该SDS后拼接一个String
     *
     * @param source {@link String}
     */
    public void sdsCat(String source) {
        concat(source.toCharArray(), source.length());
    }

    /**
     * 在该SDS后拼接一个SDS
     *
     * @param source {@link SDString}
     */
    public void sdsCatSds(SDString source) {
        concat(source.buf, source.len);
    }


    private void concat(char[] source, int len) {
        if (this.free >= len) {
            System.arraycopy(source, 0, this.buf, this.len, len);
            this.free -= len;
            this.len += len;
        } else {
            realloc(source, len);
        }
    }

    private void realloc(char[] source, int len) {

        if (len > Storage._1M) {
            //扩展空间
            this.buf = Arrays.copyOf(this.buf, len + this.len + Storage._1M);
            System.arraycopy(source, 0, this.buf, this.len, len);
            this.len += len;
            this.free = Storage._1M;
        } else {
            this.buf = Arrays.copyOf(this.buf, len * 2 + this.len);
            System.arraycopy(source, 0, this.buf, this.len, len);
            this.len += len;
            this.free += len;
        }
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SDString) {
            SDString source = (SDString) obj;
            if (this.len == source.len) {
                for (int i = 0; i < len; i++) {
                    if (this.buf[i] != source.buf[i]) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {

        return new String(buf, 0, buf.length);
    }

}

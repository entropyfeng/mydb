package com.github.entropyfeng.mydb.common.ops;

/**
 * @author entropyfeng
 */
public interface AdminOperations {

    void clear();

    void lazyClear();

    void dump();

    void lazyDump();
}

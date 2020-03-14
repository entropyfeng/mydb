package com.github.entropyfeng.mydb.core;

import org.jetbrains.annotations.NotNull;

public class StringLongPair implements Comparable<StringLongPair> {
   private String key;
   private long value;

    public StringLongPair(String key, long value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }

    @Override
    public int compareTo(@NotNull StringLongPair o) {
        return  Long.compare(this.value,o.value);

    }
}

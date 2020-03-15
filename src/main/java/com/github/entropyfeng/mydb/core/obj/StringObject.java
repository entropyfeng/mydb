package com.github.entropyfeng.mydb.core.obj;

import com.google.common.annotations.Beta;
import com.google.common.base.Charsets;
import com.google.common.base.Objects;
import com.google.common.hash.Hashing;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.stream.IntStream;

public final class StringObject implements Serializable, Comparable<StringObject>,CharSequence {
    public final String  string;
    public long recentAccess;

    StringObject(String string){
        this.string=string;
    }

    @Override
    public int length() {
        return string.length();
    }

    @Override
    public char charAt(int index) {
        return string.charAt(index);
    }

    @Override
    public CharSequence subSequence(int start, int end) {
        return string.subSequence(start,end);
    }

    @NotNull
    @Override
    public String toString() {
        return string;
    }

    @Override
    public IntStream chars() {
        return string.chars();
    }

    @Override
    public IntStream codePoints() {
        return string.codePoints();
    }


    @Override
    public int compareTo(@NotNull StringObject o) {
        return string.compareTo(o.string);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringObject that = (StringObject) o;
        return Objects.equal(string, that.string);
    }


    @Override
    public int hashCode() {
        return Hashing.murmur3_32().hashString(string, Charsets.UTF_8).asInt();
    }
}

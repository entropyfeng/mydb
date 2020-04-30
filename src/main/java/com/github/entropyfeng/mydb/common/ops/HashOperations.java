package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.common.TurtleValue;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

/**
 * @author entropyfeng
 */
public interface HashOperations {

    TurtleValue get(@NotNull String key, @NotNull TurtleValue tKey);

    Collection<TurtleValue> get(@NotNull String key);

    Boolean exists(@NotNull String key, @NotNull TurtleValue tKey, TurtleValue tValue);

    Boolean exists(@NotNull String key);

    void put(@NotNull String key, @NotNull TurtleValue tKey, @NotNull TurtleValue tValue);

    Boolean delete(@NotNull String key);

    Boolean delete(@NotNull String key, @NotNull TurtleValue tKey);

    Integer sizeOf(@NotNull String key);

}

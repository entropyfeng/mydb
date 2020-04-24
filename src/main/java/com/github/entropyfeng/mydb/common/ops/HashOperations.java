package com.github.entropyfeng.mydb.common.ops;

import com.github.entropyfeng.mydb.common.protobuf.TurtleProtoBuf;
import com.github.entropyfeng.mydb.core.TurtleValue;
import org.jetbrains.annotations.NotNull;

import java.math.BigDecimal;
import java.util.Collection;

/**
 * @author entropyfeng
 */
public interface HashOperations {

    public TurtleValue get(@NotNull String key, @NotNull TurtleValue tKey);

    public @NotNull Collection<TurtleValue> get(@NotNull String key);

    public @NotNull Boolean exists(@NotNull String key, @NotNull TurtleValue tKey, TurtleValue tValue);

    public @NotNull Boolean exists(@NotNull String key);

    public @NotNull Void put(@NotNull String key, @NotNull TurtleValue tKey, @NotNull TurtleValue tValue);

    public @NotNull Boolean delete(@NotNull String key);

    public @NotNull Boolean delete(@NotNull String key, @NotNull TurtleValue tKey);

    public @NotNull Integer sizeOf(@NotNull String key);
}

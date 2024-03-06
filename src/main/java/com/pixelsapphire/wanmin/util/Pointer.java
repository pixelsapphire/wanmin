package com.pixelsapphire.wanmin.util;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Pointer<T> {

    T value;

    @Contract(value = " -> new", pure = true)
    public static <T> @NotNull Pointer<T> nullptr() {
        return new Pointer<>(null);
    }

    public Pointer(@Nullable T value) {
        this.value = value;
    }

    public boolean isNull() {
        return value == null;
    }

    public boolean isNotNull() {
        return value != null;
    }

    public T get() {
        return value;
    }

    public void set(@Nullable T value) {
        this.value = value;
    }
}

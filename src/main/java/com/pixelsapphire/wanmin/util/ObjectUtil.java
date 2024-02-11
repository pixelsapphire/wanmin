package com.pixelsapphire.wanmin.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ObjectUtil {

    private ObjectUtil() {
    }

    public static @NotNull Class<?> getClass(@Nullable Object object) {
        if (object == null) return Void.class;
        return object.getClass();
    }
}

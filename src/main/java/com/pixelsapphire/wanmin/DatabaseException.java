package com.pixelsapphire.wanmin;

import org.jetbrains.annotations.NotNull;

public class DatabaseException extends RuntimeException {

    public DatabaseException(@NotNull String message) {
        super(message);
    }

    public DatabaseException(@NotNull String message, @NotNull Throwable cause) {
        super(message, cause);
    }
}

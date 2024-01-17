package com.pixelsapphire.wanmin.data;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class EmploymentContract {

    private final @NotNull String type;
    private final @NotNull Date concluded;
    private final @Nullable Date terminated;

    private EmploymentContract(@NotNull String type, @NotNull Date concluded, @Nullable Date terminated) {
        this.type = type;
        this.concluded = concluded;
        this.terminated = terminated;
    }

    @Contract("_ -> new")
    public static @NotNull EmploymentContract fromResult(@NotNull ResultSet record) throws SQLException {
        return new EmploymentContract(record.getString("typ"), record.getDate("zawiazana"), record.getDate("zerwana"));
    }

    public @NotNull String getType() {
        return type;
    }

    public @NotNull Date getConcluded() {
        return concluded;
    }

    public @Nullable Date getTerminated() {
        return terminated;
    }
}

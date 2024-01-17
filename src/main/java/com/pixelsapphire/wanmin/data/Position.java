package com.pixelsapphire.wanmin.data;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Position {

    private final @NotNull String name;
    private final float salary;

    private Position(@NotNull String name, float salary) {
        this.name = name;
        this.salary = salary;
    }

    @Contract("_ -> new")
    public static @NotNull Position fromRecord(@NotNull ResultSet record) throws SQLException {
        return new Position(record.getString("nazwa"), record.getFloat("pensja"));
    }

    public @NotNull String getName() {
        return name;
    }

    public float getSalary() {
        return salary;
    }
}

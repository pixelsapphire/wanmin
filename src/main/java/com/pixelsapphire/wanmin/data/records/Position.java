package com.pixelsapphire.wanmin.data.records;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Position implements DatabaseRecord {

    private final int id;
    private final @NotNull String name;
    private final float salary;

    private Position(int id, @NotNull String name, float salary) {
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    @Contract("_ -> new")
    public static @NotNull Position fromRecord(@NotNull ResultSet record) throws SQLException {
        return new Position(record.getInt("id"), record.getString("nazwa"), record.getFloat("pensja"));
    }

    public @NotNull String getName() {
        return name;
    }

    public float getSalary() {
        return salary;
    }

    @Override
    public int getId() {
        return id;
    }
}

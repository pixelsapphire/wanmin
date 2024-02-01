package com.pixelsapphire.wanmin.data.records;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Product implements DatabaseRecord {

    private final int id;
    private final @NotNull String name;
    private final String unit;

    private Product(int id, @NotNull String name, String unit) {
        this.id = id;
        this.name = name;
        this.unit = unit;
    }

    @Contract("_ -> new")
    public static @NotNull Product fromRecord(@NotNull ResultSet record) throws SQLException {
        return new Product(record.getInt("id"), record.getString("nazwa"), record.getString("jednostka"));
    }

    public @NotNull String getName() {
        return name;
    }

    public String getUnit() {
        return unit;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public @NotNull String getTableName() {
        return "wm_produkty";
    }
}

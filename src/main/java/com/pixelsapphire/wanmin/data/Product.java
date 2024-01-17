package com.pixelsapphire.wanmin.data;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Product {
    private final @NotNull String name;
    private String unit;

    private Product(@NotNull String name, String unit) {
        this.name = name;
        this.unit = unit;
    }

    @Contract("_ -> new")
    public static @NotNull Product fromRecord(@NotNull ResultSet record) throws SQLException {
        return new Product(record.getString("nazwa"), record.getString("jednostka"));
    }

    public @NotNull String getName() {
        return name;
    }

    public  String getUnit() {return unit;}
}

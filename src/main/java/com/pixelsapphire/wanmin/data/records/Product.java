package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.DatabaseException;
import com.pixelsapphire.wanmin.data.DictTuple;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
    public static @NotNull Product fromRecord(@NotNull DictTuple record) {
        try {
            return new Product(record.getInt("id"), record.getString("nazwa"), record.getString("jednostka"));
        } catch (IllegalArgumentException e) {
            throw new DatabaseException("Failed to create Product from record", e);
        }
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
}

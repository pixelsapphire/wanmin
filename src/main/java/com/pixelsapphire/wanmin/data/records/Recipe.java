package com.pixelsapphire.wanmin.data.records;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Recipe {

    private final @NotNull String description;
    private List<Ingredient> ingredients;

    private Recipe(@NotNull String description) {
        this.description = description;
    }

    @Contract("_ -> new")
    public static @NotNull Recipe formRecord(@NotNull ResultSet record) throws SQLException {
        return new Recipe(record.getString("opis"));
    }

    public @NotNull String getDescription() {
        return description;
    }
}

package com.pixelsapphire.wanmin.data.records;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Recipe implements DatabaseRecord{

    private final int id;
    private final @NotNull String description;
    private List<RecipeIngredient> recipeIngredients;

    private Recipe(int id, @NotNull String description) {
        this.id = id;
        this.description = description;
    }

    @Contract("_ -> new")
    public static @NotNull Recipe formRecord(@NotNull ResultSet record) throws SQLException {
        return new Recipe(record.getInt("id"), record.getString("opis"));
    }

    public @NotNull String getDescription() {
        return description;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public @NotNull String getTableName() {
        return "wm_przepisy";
    }
}

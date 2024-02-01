package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.controller.Provider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Recipe implements DatabaseRecord {

    private final int id;
    private final @NotNull String description;
    private final List<RecipeIngredient> recipeIngredients;

    private Recipe(int id, @NotNull String description, @NotNull List<RecipeIngredient> recipeIngredients) {
        this.id = id;
        this.description = description;
        this.recipeIngredients = new ArrayList<>(recipeIngredients);
    }

    @Contract("_, _ -> new")
    public static @NotNull Recipe formRecord(@NotNull ResultSet record,
                                             @NotNull Provider<List<RecipeIngredient>> ingredientsProvider) throws SQLException {
        return new Recipe(record.getInt("id"), record.getString("opis"),
                          ingredientsProvider.getByKey(record.getInt("id")));
    }

    public @NotNull String getDescription() {
        return description;
    }

    public @NotNull List<RecipeIngredient> getRecipeIngredients() {
        return Collections.unmodifiableList(recipeIngredients);
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

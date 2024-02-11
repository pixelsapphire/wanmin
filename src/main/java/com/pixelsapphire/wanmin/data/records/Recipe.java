package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.DatabaseException;
import com.pixelsapphire.wanmin.controller.Provider;
import com.pixelsapphire.wanmin.data.DictTuple;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
    public static @NotNull Recipe fromRecord(@NotNull DictTuple record,
                                             @NotNull Provider<List<RecipeIngredient>> ingredientsProvider) {
        try {
            return new Recipe(record.getInt("id"), record.getString("opis"),
                              ingredientsProvider.getById(record.getInt("id")));
        } catch (IllegalArgumentException e) {
            throw new DatabaseException("Failed to create Recipe from record" + record, e);
        }
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
}

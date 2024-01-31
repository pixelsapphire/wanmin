package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.controller.Provider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuPosition {
    private final int id;
    private final float price;
    private final @NotNull Recipe recipe;
    private final @NotNull String category, name;

    private MenuPosition(int id, @NotNull String name, float price, @NotNull Recipe recipe, @NotNull String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.recipe = recipe;
        this.category = category;
    }

    @Contract("_, _ -> new")
    public static @NotNull MenuPosition formRecord (@NotNull ResultSet record, @NotNull Provider<Recipe> recipeProvider) throws SQLException {
        return new MenuPosition(record.getInt("id"), record.getString("nazwa"), record.getFloat("cena"),
                recipeProvider.getByValue(record.getInt("przepis")), record.getString("kategoria"));
    }

    public int getId() {
        return id;
    }

    public float getPrice() {
        return price;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public String getCategory() {
        return category;
    }

    public String getName() {
        return name;
    }
}

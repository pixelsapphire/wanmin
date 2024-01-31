package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.controller.Provider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MenuItem implements DatabaseRecord {

    private final int id;
    private final float price;
    private final @NotNull Recipe recipe;
    private final @NotNull String category, name;

    private MenuItem(int id, @NotNull String name, float price, @NotNull Recipe recipe, @NotNull String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.recipe = recipe;
        this.category = category;
    }

    @Contract("_, _ -> new")
    public static @NotNull MenuItem formRecord(@NotNull ResultSet record, @NotNull Provider<Recipe> recipeProvider) throws SQLException {
        return new MenuItem(record.getInt("id"), record.getString("nazwa"), record.getFloat("cena"),
                            recipeProvider.getByValue(record.getInt("przepis")), record.getString("kategoria"));
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public @NotNull String getTableName() {
        return "wm_menu_pozycje";
    }

    public float getPrice() {
        return price;
    }

    public @NotNull Recipe getRecipe() {
        return recipe;
    }

    public @NotNull String getCategory() {
        return category;
    }

    public @NotNull String getName() {
        return name;
    }
}

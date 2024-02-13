package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.DatabaseException;
import com.pixelsapphire.wanmin.controller.Provider;
import com.pixelsapphire.wanmin.data.DictTuple;
import com.pixelsapphire.wanmin.data.DictTuple.DictTupleBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class MenuItem implements DatabaseRecord {

    private final int id;
    private final float price;
    private final @NotNull String category, name;
    private final @NotNull Recipe recipe;

    private MenuItem(int id, @NotNull String name, float price, @NotNull Recipe recipe, @NotNull String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.recipe = recipe;
        this.category = category;
    }

    @Contract("_, _ -> new")
    public static @NotNull MenuItem fromRecord(@NotNull DictTuple record, @NotNull Provider<Recipe> recipeProvider) {
        try {
            return new MenuItem(record.getInt("id"), record.getString("nazwa"), record.getFloat("cena"),
                                recipeProvider.getById(record.getInt("przepis")), record.getString("kategoria"));
        } catch (IllegalArgumentException e) {
            throw new DatabaseException("Failed to create MenuPosition from record" + record, e);
        }
    }

    public float getPrice() {
        return price;
    }

    public @NotNull String getCategory() {
        return category;
    }

    public @NotNull String getName() {
        return name;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public @NotNull DictTuple toRecord() {
        return new DictTupleBuilder().with("id", id)
                                     .with("nazwa", name)
                                     .with("cena", price)
                                     .with("przepis", recipe.getId())
                                     .with("kategoria", category)
                                     .build();
    }
}

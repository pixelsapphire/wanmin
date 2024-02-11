package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.DatabaseException;
import com.pixelsapphire.wanmin.controller.Provider;
import com.pixelsapphire.wanmin.data.DictTuple;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class RecipeIngredient implements DatabaseRecord {

    private final int id;
    private final @NotNull Product product;
    private final float amount;

    private RecipeIngredient(int id, @NotNull Product product, float amount) {
        this.id = id;
        this.product = product;
        this.amount = amount;
    }

    @Contract("_, _ -> new")
    public static @NotNull RecipeIngredient fromRecord(@NotNull DictTuple record, @NotNull Provider<Product> productProvider) {
        try {
            return new RecipeIngredient(record.getInt("id"), productProvider.getById(record.getInt("produkt")),
                                        record.getFloat("ilosc"));
        } catch (IllegalArgumentException e) {
            throw new DatabaseException("Failed to create RecipeIngredient from record" + record, e);
        }
    }

    public @NotNull Product getProduct() {
        return product;
    }

    public float getAmount() {
        return amount;
    }

    @Override
    public int getId() {
        return id;
    }
}

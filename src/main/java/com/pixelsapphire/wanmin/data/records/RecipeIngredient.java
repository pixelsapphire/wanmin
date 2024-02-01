package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.controller.Provider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

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
    public static @NotNull RecipeIngredient fromRecord(@NotNull ResultSet record, @NotNull Provider<Product> productProvider) throws SQLException {
        return new RecipeIngredient(record.getInt("id"), productProvider.getByKey(record.getString("produkt")),
                                    record.getFloat("ilosc"));
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

    @Override
    public @NotNull String getTableName() {
        return "wm_przepisy_skladniki";
    }
}

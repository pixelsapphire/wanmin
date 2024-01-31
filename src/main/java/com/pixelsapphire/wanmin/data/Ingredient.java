package com.pixelsapphire.wanmin.data;

import com.pixelsapphire.wanmin.controller.Provider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Ingredient {

    private final @NotNull Product product;
    private final float amount;

    private Ingredient(@NotNull Product product, float amount) {
        this.product = product;
        this.amount = amount;
    }

    @Contract("_, _ -> new")
    public static @NotNull Ingredient fromRecord(@NotNull ResultSet record, @NotNull Provider<Product> productProvider) throws SQLException {
        return new Ingredient(productProvider.getByValue(record.getString("produkt")), record.getFloat("ilosc"));
    }

    public @NotNull Product getProduct() {
        return product;
    }

    public float getAmount() {
        return amount;
    }
}

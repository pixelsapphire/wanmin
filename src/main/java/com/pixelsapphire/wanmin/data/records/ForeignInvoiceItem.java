package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.controller.Provider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ForeignInvoiceItem implements DatabaseRecord {

    private final int id;
    private final @NotNull Product product;
    private final float price, amount;
    private final Date date;

    private ForeignInvoiceItem(int id, @NotNull Product product, float price, float amount, Date date) {
        this.id = id;
        this.product = product;
        this.price = price;
        this.amount = amount;
        this.date = date;
    }

    @Contract("_, _ -> new")
    public static @NotNull ForeignInvoiceItem fromRecord(@NotNull ResultSet record, @NotNull Provider<Product> productProvider) throws SQLException {
        return new ForeignInvoiceItem(record.getInt("id"), productProvider.getByKey(record.getString("produkt")),
                                      record.getFloat("cena"), record.getFloat("ilosc"), record.getDate("data_waznosci"));
    }

    public @NotNull Product getProduct() {
        return product;
    }

    public float getAmount() {
        return amount;
    }

    public Date getDate() {
        return date;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public int getId() {
        return id;
    }
}

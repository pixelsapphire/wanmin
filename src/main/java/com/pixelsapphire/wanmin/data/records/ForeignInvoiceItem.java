package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.DatabaseException;
import com.pixelsapphire.wanmin.controller.Provider;
import com.pixelsapphire.wanmin.data.DictTuple;
import com.pixelsapphire.wanmin.data.DictTuple.DictTupleBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

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
    public static @NotNull ForeignInvoiceItem fromRecord(@NotNull DictTuple record, @NotNull Provider<Product> productProvider) {
        try {
            return new ForeignInvoiceItem(record.getInt("id"), productProvider.getById(record.getInt("produkt")),
                                          record.getFloat("cena"), record.getFloat("ilosc"), record.getDate("data_waznosci"));
        } catch (IllegalArgumentException e) {
            throw new DatabaseException("Failed to create ForeignInvoiceItem from record" + record, e);
        }
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

    @Override
    public @NotNull DictTuple toRecord() {
        return new DictTupleBuilder().with("id", id)
                                     .with("produkt", product.getId())
                                     .with("cena", price)
                                     .with("ilosc", amount)
                                     .with("data_waznosci", date)
                                     .build();
    }
}

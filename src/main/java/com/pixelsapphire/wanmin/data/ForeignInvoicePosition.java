package com.pixelsapphire.wanmin.data;

import com.pixelsapphire.wanmin.controller.Provider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ForeignInvoicePosition {

    private final @NotNull ForeignInvoice invoice;
    private final @NotNull Product product;
    private final float price, amount;
    private final Date date;

    private ForeignInvoicePosition(@NotNull ForeignInvoice invoice, @NotNull Product product, float price, float amount, Date date) {
        this.invoice = invoice;
        this.product = product;
        this.price = price;
        this.amount = amount;
        this.date = date;
    }

    @Contract("_, _, _ -> new")
    public static @NotNull ForeignInvoicePosition fromRecord(@NotNull ResultSet record, @NotNull Provider<ForeignInvoice> invoiceProvider,
                                                             @NotNull Provider<Product> productProvider) throws SQLException {
        return new ForeignInvoicePosition(invoiceProvider.getByValue(record.getString("faktura")), productProvider.getByValue(record.getString("produkt")),
                                          record.getFloat("cena"), record.getFloat("ilosc"), record.getDate("data_waznosci"));
    }

    public @NotNull Product getProduct() {
        return product;
    }

    public float getAmount() {
        return amount;
    }

    public @NotNull ForeignInvoice getInvoice() {
        return invoice;
    }

    public Date getDate() {
        return date;
    }

    public float getPrice() {
        return price;
    }
}

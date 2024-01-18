package com.pixelsapphire.wanmin.data;

import com.pixelsapphire.wanmin.controller.Provider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Storage {
    private final @NotNull Product product;
    private final float amount;
    private Date terminDue;
    private final @NotNull ForeignInvoice invoice;

    private Storage(@NotNull Product product, float amount, Date terminDue, @NotNull ForeignInvoice invoice) {
        this.product = product;
        this.amount = amount;
        this.terminDue = terminDue;
        this.invoice = invoice;
    }

    @Contract("_, _, _ -> new")
    public static @NotNull Storage fromRecord(@NotNull ResultSet record, @NotNull Provider<Product> productProvider,
                                              @NotNull Provider<ForeignInvoice> invoiceProvider) throws SQLException {
        return new Storage(productProvider.getByValue(record.getString("produkt")), record.getFloat("ilosc"),
                record.getDate("data_waznosci"), invoiceProvider.getByValue(record.getString("faktura")));
    }

    public Date getTerminDue() {
        return terminDue;
    }

    public float getAmount() {
        return amount;
    }

    public @NotNull ForeignInvoice getInvoice() {
        return invoice;
    }

    public @NotNull Product getProduct() {
        return product;
    }
}

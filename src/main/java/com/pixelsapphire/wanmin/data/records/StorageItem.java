package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.controller.Provider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class StorageItem implements DatabaseRecord {

    private final int id;
    private final @NotNull Product product;
    private final float amount;
    private final @NotNull ForeignInvoice invoice;
    private final @Nullable Date dueDate;

    private StorageItem(int id, @NotNull Product product, float amount, Date dueDate, @NotNull ForeignInvoice invoice) {
        this.id = id;
        this.product = product;
        this.amount = amount;
        this.dueDate = dueDate;
        this.invoice = invoice;
    }

    @Contract("_, _, _ -> new")
    public static @NotNull StorageItem fromRecord(@NotNull ResultSet record, @NotNull Provider<Product> productProvider,
                                                  @NotNull Provider<ForeignInvoice> invoiceProvider) throws SQLException {
        return new StorageItem(record.getInt("is"), productProvider.getByValue(record.getString("produkt")), record.getFloat("ilosc"),
                               record.getDate("data_waznosci"), invoiceProvider.getByValue(record.getString("faktura")));
    }

    public @Nullable Date getDueDate() {
        return dueDate;
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

    @Override
    public int getId() {
        return id;
    }

    @Override
    public @NotNull String getTableName() {
        return "wm_magazyn";
    }
}

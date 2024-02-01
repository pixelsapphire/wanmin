package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.controller.Provider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ForeignInvoice implements DatabaseRecord {

    private final int id;
    private final @NotNull Contractor contractor;
    private final @NotNull Date date;
    private final @NotNull String number;
    private final @NotNull List<ForeignInvoiceItem> items;

    private ForeignInvoice(int id, @NotNull Contractor contractor, @NotNull Date date, @NotNull String number,
                           @NotNull List<ForeignInvoiceItem> items) {
        this.id = id;
        this.contractor = contractor;
        this.date = date;
        this.number = number;
        this.items = new ArrayList<>(items);
    }

    @Contract("_, _, _ -> new")
    public static @NotNull ForeignInvoice fromRecord(@NotNull ResultSet record, @NotNull Provider<Contractor> contractorProvider,
                                                     @NotNull Provider<List<ForeignInvoiceItem>> itemsProvider) throws SQLException {
        return new ForeignInvoice(record.getInt("id"), contractorProvider.getByKey(record.getInt("kontrahent")),
                                  record.getDate("data"), record.getString("nr_obcy"),
                                  itemsProvider.getByKey(record.getInt("id")));
    }

    public @NotNull Contractor getContractor() {
        return contractor;
    }

    public @NotNull Date getDate() {
        return date;
    }

    public @NotNull String getNumber() {
        return number;
    }

    public @NotNull List<ForeignInvoiceItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public @NotNull String getTableName() {
        return "wm_faktury_obce";
    }
}

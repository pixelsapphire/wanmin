package com.pixelsapphire.wanmin.data;

import com.pixelsapphire.wanmin.controller.Provider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class ForeignInvoice {

    private final @NotNull Contractor contractor;
    private final @NotNull Date date;
    private final @NotNull String number;

    private ForeignInvoice(@NotNull Contractor contractor, @NotNull Date date, @NotNull String number) {
        this.contractor = contractor;
        this.date = date;
        this.number = number;
    }

    @Contract("_, _ -> new")
    public static @NotNull ForeignInvoice fromRecord (@NotNull ResultSet record, @NotNull Provider<Contractor> contractorProvider) throws SQLException {
        return new ForeignInvoice(contractorProvider.getByValue(record.getInt("kontrahent")), record.getDate("data"),
                record.getString("nr_obcy"));
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
}

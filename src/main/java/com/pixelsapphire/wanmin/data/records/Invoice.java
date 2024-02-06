package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.controller.Provider;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class Invoice implements DatabaseRecord {

    private final int id;
    private final @NotNull Customer customer;
    private final @NotNull Date date;
    private final @NotNull String number;
    private final float discount;

    private Invoice (int id, @NotNull Customer customer, @NotNull Date date, @NotNull String number, float discount) {
        this.id = id;
        this.customer = customer;
        this.date = date;
        this.number = number;
        this.discount = discount;
    }

    @Contract("_, _ -> new")
    public static @NotNull Invoice fromRecord(@NotNull ResultSet record, @NotNull Provider<Customer> customerProvider) throws SQLException {
        return new Invoice(record.getInt("id"), customerProvider.getByKey(record.getInt("klient")), record.getDate("data"),
                record.getString("nr_faktury"),record.getFloat("znizka"));
    }

    @Override
    public int getId() {
        return id;
    }

    public @NotNull Customer getCustomer() {
        return customer;
    }

    public @NotNull Date getDate() {
        return date;
    }

    public @NotNull String getNumber() {
        return number;
    }

    public float getDiscount() {
        return discount;
    }
}

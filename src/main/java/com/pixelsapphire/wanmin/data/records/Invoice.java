package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.DatabaseException;
import com.pixelsapphire.wanmin.controller.Provider;
import com.pixelsapphire.wanmin.data.DictTuple;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.sql.SQLException;
import java.util.Date;

public class Invoice implements DatabaseRecord {

    private final int id;
    private final @NotNull Customer customer;
    private final @NotNull Order order;
    private final @NotNull Date date;
    private final @NotNull String number;
    private final float discount;

    private Invoice(int id, @NotNull Customer customer, @NotNull Order order, @NotNull Date date, @NotNull String number, float discount) {
        this.id = id;
        this.customer = customer;
        this.order = order;
        this.date = date;
        this.number = number;
        this.discount = discount;
    }

    @Contract("_, _ -> new")
    public static @NotNull Invoice fromRecord(@NotNull DictTuple record, @NotNull Provider<Customer> customerProvider,
                                              @NotNull Provider<Order> orderProvider) {
        try {
            return new Invoice(record.getInt("id"), customerProvider.getById(record.getInt("klient")), orderProvider.getById(record.getInt("zamowienie")),
                                record.getDate("data"), record.getString("nr_faktury"), record.getFloat("znizka"));
        } catch (IllegalArgumentException e) {
            throw new DatabaseException("Failed to create Invoice from record" + record, e);
        }
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

    public Order getOrder() {
        return order;
    }
}

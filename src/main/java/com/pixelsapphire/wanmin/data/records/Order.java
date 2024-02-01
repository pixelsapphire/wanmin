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

public class Order implements DatabaseRecord {

    private final int id, table;
    private final @NotNull Employee waiter;
    private final @NotNull Date time;
    private final @NotNull Customer customer;
    private final boolean paid;
    private final @NotNull List<OrderItem> items;

    public Order(int id, int table, @NotNull Employee waiter, @NotNull Date time, @NotNull Customer customer, boolean paid, @NotNull List<OrderItem> items) {
        this.id = id;
        this.table = table;
        this.waiter = waiter;
        this.time = time;
        this.customer = customer;
        this.paid = paid;
        this.items = new ArrayList<>(items);
    }

    @Contract("_, _, _, _ -> new")
    public static @NotNull Order fromRecord(@NotNull ResultSet record, @NotNull Provider<Employee> employeeProvider,
                                            @NotNull Provider<Customer> customerProvider,
                                            @NotNull Provider<List<OrderItem>> itemsProvider) throws SQLException {
        return new Order(record.getInt("id"), record.getInt("stolik"),
                         employeeProvider.getByKey(record.getInt("kelner")), record.getDate("czas"),
                         customerProvider.getByKey(record.getInt("klient")), record.getBoolean("zaplacone"),
                         itemsProvider.getByKey(record.getInt("id")));
    }

    public int getTable() {
        return table;
    }

    public @NotNull Employee getWaiter() {
        return waiter;
    }

    public @NotNull Date getTime() {
        return time;
    }

    public @NotNull Customer getCustomer() {
        return customer;
    }

    public boolean isPaid() {
        return paid;
    }

    public @NotNull List<OrderItem> getItems() {
        return Collections.unmodifiableList(items);
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public @NotNull String getTableName() {
        return "wm_zamowienia";
    }
}

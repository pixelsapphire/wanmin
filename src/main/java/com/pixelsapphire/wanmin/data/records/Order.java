package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.DatabaseException;
import com.pixelsapphire.wanmin.controller.Provider;
import com.pixelsapphire.wanmin.data.DictTuple;
import com.pixelsapphire.wanmin.data.DictTuple.DictTupleBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@SuppressWarnings("ClassCanBeRecord")
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
    public static @NotNull Order fromRecord(@NotNull DictTuple record, @NotNull Provider<Employee> employeeProvider,
                                            @NotNull Provider<Customer> customerProvider,
                                            @NotNull Provider<List<OrderItem>> itemsProvider) {
        try {
            return new Order(record.getInt("id"), record.getInt("stolik"),
                             employeeProvider.getById(record.getInt("kelner")), record.getDate("czas"),
                             customerProvider.getById(record.getInt("klient")), record.getBoolean("czy_zaplacone"),
                             itemsProvider.getById(record.getInt("id")));
        } catch (IllegalArgumentException e) {
            throw new DatabaseException("Failed to create Order from record" + record, e);
        }
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
    public @NotNull DictTuple toRecord() {
        return new DictTupleBuilder().with("id", id)
                                     .with("stolik", table)
                                     .with("kelner", waiter.getId())
                                     .with("czas", time)
                                     .with("klient", customer.getId())
                                     .with("czy_zaplacone", paid)
                                     .build();
    }

    @Override
    public String toString() {
        final var sb = new StringBuilder();
        final float[] sum = {0};

        sb.append(" Stolik ");
        sb.append(table).append(' ');
        sb.append(customer).append(' ');
        sb.append('\n');

        items.forEach(it -> {
            sb.append('\t');
            final var menuItem = it.getMenuItem();

            float amount = it.getAmount();
            float charge = menuItem.getPrice() * amount;
            sum[0] += charge;
            sb.append((int) amount);
            sb.append(" x ");
            sb.append(menuItem.getName());
            sb.append(' ');
            sb.append(charge);
            sb.append("\n");
        });
        sb.append("Suma: ").append(sum[0]).append("\n\n");

        return sb.toString();
    }
}

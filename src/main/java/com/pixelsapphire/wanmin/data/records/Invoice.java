package com.pixelsapphire.wanmin.data.records;

import com.pixelsapphire.wanmin.DatabaseException;
import com.pixelsapphire.wanmin.controller.Provider;
import com.pixelsapphire.wanmin.data.DictTuple;
import com.pixelsapphire.wanmin.data.DictTuple.DictTupleBuilder;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class Invoice implements DatabaseRecord {

    private final int id;
    private final @NotNull Customer customer;
    private final @NotNull Order order;
    private final @NotNull Date date;
    private final float discount;

    public Invoice(int id, @NotNull Customer customer, @NotNull Order order, @NotNull Date date, float discount) {
        this.id = id;
        this.customer = customer;
        this.order = order;
        this.date = date;
        this.discount = discount;
    }

    @Contract("_, _ -> new")
    public static @NotNull Invoice fromRecord(@NotNull DictTuple record, @NotNull Provider<Customer> customerProvider,
                                              @NotNull Provider<Order> orderProvider) {
        try {
            return new Invoice(record.getInt("id"), customerProvider.getById(record.getInt("klient")), orderProvider.getById(record.getInt("zamowienie")),
                               record.getDate("data"), record.getFloat("znizka"));
        } catch (IllegalArgumentException e) {
            throw new DatabaseException("Failed to create Invoice from record" + record, e);
        }
    }

    public @NotNull Customer getCustomer() {
        return customer;
    }

    public @NotNull Date getDate() {
        return date;
    }

    public @NotNull String getNumber() {
        return "FV/" + id;
    }

    public float getDiscount() {
        return discount;
    }

    public Order getOrder() {
        return order;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public @NotNull DictTuple toRecord() {
        return new DictTupleBuilder().with("id", id)
                                     .with("klient", customer.getId())
                                     .with("zamowienie", order.getId())
                                     .with("data", date)
                                     .with("znizka", discount)
                                     .build();
    }
}

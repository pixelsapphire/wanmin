package com.pixelsapphire.wanmin.controller.collections;

import com.pixelsapphire.wanmin.controller.DatabaseExecutor;
import com.pixelsapphire.wanmin.controller.Provider;
import com.pixelsapphire.wanmin.data.DictTuple;
import com.pixelsapphire.wanmin.data.WanminCollection;
import com.pixelsapphire.wanmin.data.records.Customer;
import com.pixelsapphire.wanmin.data.records.Employee;
import com.pixelsapphire.wanmin.data.records.Order;
import com.pixelsapphire.wanmin.data.records.OrderItem;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class OrdersCollection extends WanminCollection<Order> {

    private final @NotNull Provider<Employee> employees;
    private final @NotNull Provider<Customer> customers;
    private final @NotNull Provider<List<OrderItem>> orderItemsProvider;

    public OrdersCollection(@NotNull DatabaseExecutor controller,
                            @NotNull Provider<Employee> employees, @NotNull Provider<Customer> customers,
                            @NotNull Provider<List<OrderItem>> orderItemsProvider) {
        super(controller);
        this.employees = employees;
        this.customers = customers;
        this.orderItemsProvider = orderItemsProvider;
    }

    @Override
    public @NotNull Order elementFromRecord(@NotNull DictTuple record) {
        return Order.fromRecord(record, employees, customers, orderItemsProvider);
    }

    @Override
    public @NotNull DictTuple elementToRecord(@NotNull Order order) {
        return order.toRecord();
    }

    @Override
    public @NotNull List<DictTuple> getRecords() {
        return controller.executeQuery("SELECT * FROM sbd147412.wm_zamowienia");
    }

    @Override
    public void saveRecord(@NotNull DictTuple r) {
        controller.executeDML("INSERT INTO sbd147412.wm_zamowienia (stolik, kelner, czas, klient, czy_zaplacone) VALUES (?,?,?,?,?)",
                              r.getInt("stolik"), r.getInt("kelner"), r.getDate("czas"),
                              r.getInt("klient"), r.getBoolean("czy_zaplacone"));
    }

    public void addNewOrder(int table, int waiterId, int customerId) {
        controller.executeDML("INSERT INTO sbd147412.wm_zamowienia (stolik, kelner, klient) VALUES (?,?,?)",
                table, waiterId, customerId);
    }

    public void addNewOrder(int table, @NotNull Employee waiter, @NotNull Customer customer) {
        addNewOrder(table, waiter.getId(), customer.getId());
    }

    public void addOrderItem(int orderId, @NotNull OrderItem orderItem) {
        controller.executeDML("INSERT INTO sbd147412.wm_zamowienia_pozycje (zamowienie, pozycja, ilosc) VALUES (?,?,?)",
                              orderId, orderItem.getMenuItem().getId(), orderItem.getAmount());
    }

    public void deleteOrder (int orderId) {
        controller.executeDML("DELETE FROM sbd147412.wm_zamowienia_pozycje WHERE zamowienie = ?", orderId);
        controller.executeDML("DELETE FROM sbd147412.wm_zamowienia WHERE id = ?", orderId);
    }

    public void updateOrder (@NotNull Order order) {
        controller.executeDML("UPDATE sbd147412.wm_zamowienia set stolik = ?, kelner = ?, czas = ?, klient = ?, czy_zaplacone = ? where id = ?",
                order.getTable(), order.getWaiter(), order.getTime(), order.getCustomer(), order.isPaid(), order.getId());
    }

    public void updateOrderItem (int orderId, @NotNull OrderItem orderItem) {
        controller.executeDML("UPDATE sbd147412.wm_zamowienia_pozycje set zamowienie = ?, pozycja = ?, ilosc = ? where id = ?",
                orderId, orderItem.getMenuItem().getId(), orderItem.getAmount(), orderItem.getId());
    }
}

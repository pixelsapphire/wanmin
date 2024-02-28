package com.pixelsapphire.wanmin.controller.collections;

import com.pixelsapphire.wanmin.controller.DatabaseExecutor;
import com.pixelsapphire.wanmin.controller.Provider;
import com.pixelsapphire.wanmin.data.DictTuple;
import com.pixelsapphire.wanmin.data.WanminCollection;
import com.pixelsapphire.wanmin.data.records.Customer;
import com.pixelsapphire.wanmin.data.records.Invoice;
import com.pixelsapphire.wanmin.data.records.Order;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class InvoicesCollection extends WanminCollection<Invoice> {

    private final @NotNull Provider<Customer> customers;
    private final @NotNull Provider<Order> orders;

    public InvoicesCollection(@NotNull DatabaseExecutor controller,
                              @NotNull Provider<Customer> customers, @NotNull Provider<Order> orders) {
        super(controller);
        this.customers = customers;
        this.orders = orders;
    }

    @Override
    public @NotNull Invoice elementFromRecord(@NotNull DictTuple record) {
        return Invoice.fromRecord(record, customers, orders);
    }

    @Override
    public @NotNull DictTuple elementToRecord(@NotNull Invoice invoice) {
        return invoice.toRecord();
    }

    @Override
    public @NotNull List<DictTuple> getRecords() {
        return controller.executeQuery("SELECT * FROM sbd147412.wm_faktury");
    }

    @Override
    public void saveRecord(@NotNull DictTuple r) {
        controller.executeDML("INSERT INTO sbd147412.wm_faktury (klient, zamowienie, data, nr_faktury, znizka) VALUES (?,?,?,?,?)",
                              r.getInt("klient"), r.getInt("zamowienie"), r.getString("data"), r.getString("nr_faktury"), r.getFloat("znizka"));
    }

    public void deleteInvoice (int invoiceId) {
        controller.executeDML("DELETE FROM sbd147412.wm_faktury where id = ?", invoiceId);
    }

    public void updateInvoice (@NotNull Invoice invoice) {
        controller.executeDML("UPDATE sbd147412.wm_faktury SET klient = ?, zamowienie = ?, data = ?, nr_faktury = ?, znizka = ? where id = ?",
                invoice.getCustomer().getId(), invoice.getOrder().getId(), invoice.getDate(), invoice.getNumber(), invoice.getDiscount(), invoice.getId());
    }
}

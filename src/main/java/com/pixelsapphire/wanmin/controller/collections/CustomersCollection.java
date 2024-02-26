package com.pixelsapphire.wanmin.controller.collections;

import com.pixelsapphire.wanmin.controller.DatabaseExecutor;
import com.pixelsapphire.wanmin.data.DictTuple;
import com.pixelsapphire.wanmin.data.WanminCollection;
import com.pixelsapphire.wanmin.data.records.Customer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CustomersCollection extends WanminCollection<Customer> {

    public CustomersCollection(@NotNull DatabaseExecutor controller) {
        super(controller);
    }

    @Override
    public @NotNull Customer elementFromRecord(@NotNull DictTuple record) {
        return Customer.fromRecord(record);
    }

    @Override
    public @NotNull DictTuple elementToRecord(@NotNull Customer customer) {
        return customer.toRecord();
    }

    @Override
    public @NotNull List<DictTuple> getRecords() {
        return controller.executeQuery("SELECT * FROM sbd147412.wm_klienci");
    }

    @Override
    public void saveRecord(@NotNull DictTuple r) {
        controller.executeDML("INSERT INTO sbd147412.wm_klienci (imie, nazwisko) VALUES (?,?)",
                              r.getString("imie"), r.getString("nazwisko"));
    }
}
